package me.towo.sculkmic.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;

public class BlockEntityFinder<T extends BlockEntity> {
    int radius;
    BlockPos center;
    Level level;
    Class<T> beClass;
    public BlockEntityFinder(Class<T> blockEntityClass, int squareRadius, BlockPos center, Level level) {
        radius = squareRadius;
        this.center = center;
        this.level = level;
        beClass = blockEntityClass;
    }

    public <E extends T> ArrayList<T> find() {
        Iterable<BlockPos> positions = BlockPos.betweenClosed(center.offset(-radius, -radius, -radius),
                center.offset(radius, radius, radius));
        return getBlockEntities(positions, level);
    }


    private <T extends BlockEntity> ArrayList<T> getBlockEntities(Iterable<BlockPos> positions, Level level) {
        ArrayList<T> result = new ArrayList<>();
        for (BlockPos pos : positions) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                if (be.getClass().isAssignableFrom(beClass)) {
                    result.add((T)be);
                }
            }
        }
        return result;
    }
}
