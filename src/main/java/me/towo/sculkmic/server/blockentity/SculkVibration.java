package me.towo.sculkmic.server.blockentity;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.common.utils.BlockEntityFinder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class SculkVibration {

    private final Player source;
    private final int distance;
    private final int comparatorSignal;
    private final DynamicGameEventListener<VibrationListener>[] dynamicListeners;

    /**
     * A vibration made by a player that will travel to any nearby {@link net.minecraft.world.level.gameevent.vibrations.VibrationListener}.
     * @param source The player from where the vibration originates.
     * @param maxListenerDistance The distance the vibration will travel; any sculk listener within this range will activate.
     * @param comparatorSignal The signal (between 1 and 15) a redstone comparator linked to a sculk sensor block will output on receiving this vibration.
     */
    public SculkVibration(Player source, int maxListenerDistance, int comparatorSignal) {

        this.source = source;
        this.distance = maxListenerDistance;
        this.comparatorSignal = comparatorSignal;

        if (comparatorSignal > 15) {
            SculkMicMod.LOGGER.warn("A comparator signal of more than 15 is not valid! Given is {}, but outputting 15.", comparatorSignal);
            comparatorSignal = 15;
        } else if (comparatorSignal < 1) {
            SculkMicMod.LOGGER.warn("A comparator signal of less than 1 is not valid! Given is {}, but outputting 1.", comparatorSignal);
            comparatorSignal = 1;
        }

        AABB aabb = new AABB(source.blockPosition().offset(-distance, -distance, -distance),  source.blockPosition().offset(distance, distance, distance));
        List<Warden> nearbyWardens = source.level.getNearbyEntities(Warden.class, TargetingConditions.DEFAULT, source, aabb);
        dynamicListeners = new DynamicGameEventListener[nearbyWardens.size()];
        for (int i = 0; i < nearbyWardens.size(); i++) {
            Warden warden = nearbyWardens.get(i);
            dynamicListeners[i] = new DynamicGameEventListener<>(new VibrationListener(new EntityPositionSource(warden, warden.getEyeHeight()), 16, warden, (VibrationListener.ReceivingEvent)null, 0.0F, 0));
        }
    }

    /**
     * This method tries to generate a sculk vibration to any warden that's within the given distance.
     * */
    public void generateForWarden() {
            for (DynamicGameEventListener<VibrationListener> dynamicListener : dynamicListeners) {
                GameEvent event = GameEvent.BLOCK_PLACE; // default game event that will get heard by the warden
                ServerLevel level = source.level.getServer().overworld(); // world
                VibrationListener listener = dynamicListener.getListener(); // gets the vibrationlistener of all wardens
                GameEvent.Message message = new GameEvent.Message(event, source.position(),
                        new GameEvent.Context(source, Blocks.STONE.defaultBlockState()), listener,
                        listener.getListenerSource().getPosition(source.level).get()); // creates a message for the event that supposedly took place
                listener.handleGameEvent(level, message); // sends signal
            }
            return;
    }

    /**
     * This method tries to generate a sculk vibration to any sculk sensor blocks that's within the given distance.
     * */
    public void generateForSculkBlock() {
        GameEvent event = getEventForSignal(comparatorSignal); // retrieves a game event matching the given comparator output.
        BlockEntityFinder<SculkSensorBlockEntity> finder =
                new BlockEntityFinder<>(SculkSensorBlockEntity.class, distance, source.blockPosition().offset(0, 1, 0), source.level);

        for (SculkSensorBlockEntity sculk : finder.find()) { // finds all sculk within distance
            VibrationListener listener = sculk.getListener();
            GameEvent.Message message = new GameEvent.Message(event, source.position(),
                    new GameEvent.Context(source, null), listener,
                    listener.getListenerSource().getPosition(source.level).get());

            listener.handleGameEvent(source.level.getServer().overworld(), message); // handles the game event a.k.a. spawns a vibration for the listener
        }
    }

    /**
     * Gets called every tick. This method calls {@link net.minecraft.world.level.gameevent.vibrations.VibrationListener#tick(Level)}.
     * This is needed for Warden-listening to work properly <b>and must therefore be registered in the event bus</b>.
     * */
    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent e) {
        for (DynamicGameEventListener<VibrationListener> listener : dynamicListeners) {
            listener.getListener().tick(source.level);
            System.out.println("Still registered.");
        }
    }

    /**
     * @return A {@link net.minecraft.world.level.gameevent.GameEvent} matching the given comparator signal based on {@link net.minecraft.world.level.block.SculkSensorBlock#VIBRATION_FREQUENCY_FOR_EVENT}.
     * */
    private GameEvent getEventForSignal(int comparatorSignal) {
        Object2IntMap<GameEvent> vibrationMap = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT;

        for (Object2IntMap.Entry<GameEvent> entry : vibrationMap.object2IntEntrySet()) {
            if (entry.getIntValue() == comparatorSignal) {
                return entry.getKey();
            }
        }
        return null;
    }


    /**
     * @todo call this on vibration's arrival.
     */
    private void unregister() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
