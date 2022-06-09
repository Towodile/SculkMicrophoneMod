package me.towo.sculkmic.server.blockentity;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.common.utils.BlockEntityFinder;
import me.towo.sculkmic.common.utils.Chat;
import me.towo.sculkmic.common.utils.ModMath;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SculkVibration {

    private final Player source;
    private final int distance;

    /**
     * A vibration made by a player that will travel to any nearby {@link net.minecraft.world.level.gameevent.vibrations.VibrationListener}.
     * @param source The player from where the vibration originates.
     * @param maxListenerDistance The distance the vibration will travel; any sculk listener within this range will activate.
     */
    public SculkVibration(Player source, int maxListenerDistance) {
        this.source = source;
        this.distance = maxListenerDistance;
    }

    /**
     * This method tries to generate a sculk vibration to any listener that's within the given distance.
     * @param comparatorSignal The signal (between 1 and 15) a redstone comparator linked to a sculk sensor block will output on receiving this vibration.
     */
    public void generate(int comparatorSignal) {
        // to-do add some sort of cooldown for warden.
        GameEvent event = getEventForSignal(comparatorSignal); // retrieves a game event matching the given comparator output.
        AABB aabb = new AABB(source.blockPosition(),  source.blockPosition().offset(distance, distance, distance));
        List<Warden> nearbyWardens = source.level.getNearbyEntities(Warden.class, TargetingConditions.DEFAULT, source, aabb);
        if (nearbyWardens.size() > 0) {
            for (Warden warden : nearbyWardens) {
                warden.onSignalReceive(source.getServer().overworld(), null, source.blockPosition(), event, source, warden.getTarget(), 0f);
            }
            return;
        }

        if (comparatorSignal > 15) {

            SculkMicMod.LOGGER.warn("A comparator signal of more than 15 is not valid! Given is {}, but outputting 15.", comparatorSignal);
            comparatorSignal = 15;
        } else if (comparatorSignal < 1) {
            SculkMicMod.LOGGER.warn("A comparator signal of less than 1 is not valid! Given is {}, but outputting 1.", comparatorSignal);
            comparatorSignal = 1;
        }

        BlockEntityFinder<SculkSensorBlockEntity> finder =
                new BlockEntityFinder<>(SculkSensorBlockEntity.class, distance, source.blockPosition(), source.level);

        for (SculkSensorBlockEntity sculk : finder.find()) { // finds all sculk within distance
            VibrationListener listener = sculk.getListener();
            GameEvent.Message message = new GameEvent.Message(event, source.position(),
                    new GameEvent.Context(source, null), listener,
                    listener.getListenerSource().getPosition(source.level).get());

            listener.handleGameEvent(source.level.getServer().overworld(), message); // handles the game event a.k.a. spawns a vibration for the listener
        }
    }

    private GameEvent getEventForSignal(int comparatorSignal) {
        Object2IntMap<GameEvent> vibrationMap = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT;

        for (Object2IntMap.Entry<GameEvent> entry : vibrationMap.object2IntEntrySet()) {
            if (entry.getIntValue() == comparatorSignal) {
                return entry.getKey();
            }
        }
        return null;
    }
}
