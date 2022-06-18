package me.towo.sculkmic.server.vibration;

import me.towo.sculkmic.common.init.ModGameEvent;
import me.towo.sculkmic.common.utils.BlockEntityFinder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.http.annotation.Obsolete;

import java.util.List;

public class SculkVibration {
    private final ServerPlayer source;
    private final int distance;
    private int comparatorSignal;
    private final DynamicGameEventListener<VibrationListener>[] dynamicListeners;

    /**
     * A vibration made by a player that will travel to any nearby {@link net.minecraft.world.level.gameevent.vibrations.VibrationListener}. This class might generate more than one vibration; it spawns vibrations for every nearby listener.
     * @param source The player from where the vibration originates.
     * @param maxListenerDistance The distance the vibration will travel; any sculk listener within this range will activate.
     * @param comparatorSignal The signal (between 1 and 15) a redstone comparator linked to a sculk sensor block will output on receiving this vibration.
     */
    public SculkVibration(ServerPlayer source, int maxListenerDistance, int comparatorSignal) {

        this.source = source;
        this.distance = maxListenerDistance;
        this.comparatorSignal = comparatorSignal;

        if (comparatorSignal > 15) {
            this.comparatorSignal = 15;
        } else if (comparatorSignal < 1) {
            this.comparatorSignal = 1;
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
                GameEvent event = ModGameEvent.TALK; // default game event that will get heard by the warden
                ServerLevel level = source.level.getServer().getLevel(source.level.dimension()); // gets correct world
                VibrationListener listener = dynamicListener.getListener(); // gets the vibrationlistener of all wardens
                GameEvent.Message message = new GameEvent.Message(event, source.position().add(0, 1, 0),
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
        GameEvent event = ModGameEvent.TALK;
        BlockEntityFinder<SculkSensorBlockEntity> finder =
                new BlockEntityFinder<>(SculkSensorBlockEntity.class, distance, source.blockPosition().offset(0, 1, 0), source.level);

        for (SculkSensorBlockEntity sculk : finder.find()) { // finds all sculk within distance
            VibrationListener listener = sculk.getListener();
            GameEvent.Message message = new GameEvent.Message(event, source.position().add(0, 1, 0),
                    new GameEvent.Context(source, null), listener,
                    listener.getListenerSource().getPosition(source.level).get());

            ModVibrationFrequencies.setTalkFrequency(comparatorSignal);
            listener.handleGameEvent(source.level.getServer().getLevel(source.level.dimension()), message); // handles the game event a.k.a. spawns a vibration for the listener
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
        }
    }
}
