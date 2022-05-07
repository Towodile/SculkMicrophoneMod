package me.towo.sculkmic.utils;

import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;

public class SculkVibrationGenerator {
    private final int distance;
    private final BlockPos position;
    private final Level level;
    public SculkVibrationGenerator(int distance, BlockPos position, Level level) {
        this.distance = distance;
        this.position = position;
        this.level = level;
    }
    public void generate() {
        BlockEntityFinder<SculkSensorBlockEntity> finder =
                new BlockEntityFinder<>(SculkSensorBlockEntity.class, range, e.player.blockPosition(), e.player.level);

        for (SculkSensorBlockEntity sculk : finder.find()) {
            VibrationListener listener = sculk.getListener();
            GameEvent event = getGameEventByLoudness(noiseLevel);
            listener.handleGameEvent(e.player.level, event, e.player, e.player.blockPosition());
        }
    }
}
