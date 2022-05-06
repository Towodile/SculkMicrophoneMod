package me.towo.sculkmic.datagen.server;

import me.towo.backpacks.datagen.BaseLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModLootTableProvider extends BaseLootTableProvider {

    public ModLootTableProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void addTables() {
        //dropSelf(BlockINIT.?.get());
        //silkTouch();
    }

    protected void silkTouch(Block block, Item silk, int min, int max) {
        add(block, createSilkTouchTable(block.getRegistryName().getPath(), block, silk, min, max));
    }

    protected void dropSelf(Block block) {
        add(block, createSimpleTable(block.getRegistryName().getPath(), block));
    }
}
