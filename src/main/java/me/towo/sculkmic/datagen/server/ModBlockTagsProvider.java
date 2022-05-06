package me.towo.sculkmic.datagen.server;

import me.towo.sculkmic.SculkMicMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SculkMicMod.ID, helper);
    }

    @Override
    protected void addTags() {
        //tag(Tags.Blocks.STORAGE_BLOCKS).add(BlockINIT.BACKPACK.get());
    }
}
