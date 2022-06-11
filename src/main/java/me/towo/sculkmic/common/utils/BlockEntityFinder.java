package me.towo.sculkmic.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;

public class BlockEntityFinder<T extends BlockEntity> {
    int distance;
    BlockPos center;
    Level level;
    Class<T> beClass;


    /**
     * Retrieves all BlockEntities of type <code>T</code>.
     * @param blockEntityClass The class of the BlockEntity
     * @param center The position where Block Entities should be looked for.
     * @param distance The max distance from <code>center</code> in which BlockEntities can be found.
     * @param level The level where Block Entities should be looked for.
     * <p><br>
     * Note: big numbers will often result in performance issues.
     * Be cautious when finding BlockEntities using a large distance.</p>
     */
    public BlockEntityFinder(Class<T> blockEntityClass, int distance, BlockPos center, Level level) {
        this.distance = distance;
        this.center = center;
        this.level = level;
        beClass = blockEntityClass;
    }

    public <E extends T> ArrayList<T> find() {
        Iterable<BlockPos> positions = BlockPos.betweenClosed(center.offset(-distance, -distance, -distance),
                center.offset(distance, distance, distance));
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
