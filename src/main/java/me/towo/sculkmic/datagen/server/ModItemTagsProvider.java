package me.towo.sculkmic.datagen.server;

import me.towo.sculkmic.SculkMicMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator gen, BlockTagsProvider provider, ExistingFileHelper helper) {
        super(gen, provider, SculkMicMod.ID, helper);
    }

    @Override
    protected void addTags() {
        //tag(Tags.Items.STORAGE_BLOCKS).add(BlockINIT.BACKPACK.get().asItem());
    }
}
