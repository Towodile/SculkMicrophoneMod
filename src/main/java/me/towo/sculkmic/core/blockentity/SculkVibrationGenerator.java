package me.towo.sculkmic.core.blockentity;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;

public class SculkVibrationGenerator {

    public static void generate(ServerPlayer player, int distance, int noiseLevel) {
        BlockEntityFinder<SculkSensorBlockEntity> finder =
                new BlockEntityFinder<>(SculkSensorBlockEntity.class, distance, player.blockPosition(), player.level);

        for (SculkSensorBlockEntity sculk : finder.find()) {
            VibrationListener listener = sculk.getListener();
            GameEvent event = getGameEventByLoudness(noiseLevel);
            listener.handleGameEvent(player.level, event, player, player.blockPosition());
        }
    }

    private static GameEvent getGameEventByLoudness(int loudness) {
        int eventInt = (int)((loudness - (loudness / 3)) / SculkMicConfig.SENSITIVITY.get());

        Object2IntMap vibrationMap = SculkSensorBlock.VIBRATION_STRENGTH_FOR_EVENT;
        int[] values = vibrationMap.values().toIntArray();
        Object[] keys = vibrationMap.keySet().toArray();

        int distance = Math.abs(values[0] - eventInt);
        int index = 0;
        for (int i = 1; i < vibrationMap.size(); i++) {
            int idistance = Math.abs(values[i] - eventInt);

            if(idistance < distance){
                index = i;
                distance = idistance;
            }
        }
        return (GameEvent)keys[index];
    }
}
