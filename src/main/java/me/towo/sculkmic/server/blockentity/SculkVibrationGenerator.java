package me.towo.sculkmic.server.blockentity;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import net.minecraft.client.Game;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;

public class SculkVibrationGenerator {

    public static void generate(ServerPlayer player, int distance, int comparatorSignalStrength) {
        if (comparatorSignalStrength > 15 || comparatorSignalStrength < 0)
            throw new IllegalArgumentException("A redstone signal must be a value between 1 and 15.");

        BlockEntityFinder<SculkSensorBlockEntity> finder =
                new BlockEntityFinder<>(SculkSensorBlockEntity.class, distance, player.blockPosition(), player.level);

        Object2IntMap vibrationMap = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT;
        int[] values = vibrationMap.values().toIntArray();
        Object[] keys = vibrationMap.keySet().toArray();
        GameEvent event = (GameEvent)keys[getNearestInteger(comparatorSignalStrength, values)];

        for (SculkSensorBlockEntity sculk : finder.find()) {
            VibrationListener listener = sculk.getListener();
            GameEvent.Message message = new GameEvent.Message(event, player.position(), new GameEvent.Context(player, null), listener, listener.getListenerSource().getPosition(player.level).get());
            listener.handleGameEvent(player.getLevel().getServer().overworld(), message);
        }
    }

    public static void generateDynamically(ServerPlayer player, int distance, int noiseLevel) {
        BlockEntityFinder<SculkSensorBlockEntity> finder =
                new BlockEntityFinder<>(SculkSensorBlockEntity.class, distance, player.blockPosition(), player.level);

        for (SculkSensorBlockEntity sculk : finder.find()) {
            VibrationListener listener = sculk.getListener();
            GameEvent event = getGameEventByLoudness(noiseLevel);
            GameEvent.Message message = new GameEvent.Message(event, player.position(), new GameEvent.Context(player, null), listener, listener.getListenerSource().getPosition(player.level).get());
            listener.handleGameEvent(player.level.getServer().overworld(), message);

        }
    }

    private static GameEvent getGameEventByLoudness(int loudness) {
        int eventInt = (int)((loudness - (loudness / 3)) / SculkMicConfig.SENSITIVITY.get());

        Object2IntMap vibrationMap = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT;
        int[] values = vibrationMap.values().toIntArray();
        Object[] keys = vibrationMap.keySet().toArray();

        return (GameEvent)keys[getNearestInteger(eventInt, values)];
    }

    private static int getNearestInteger(int number, int[] array) {
        int distance = Math.abs(array[0] - number);
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            int idistance = Math.abs(array[i] - number);

            if(idistance < distance){
                index = i;
                distance = idistance;
            }
        }
        return index;
    }
}
