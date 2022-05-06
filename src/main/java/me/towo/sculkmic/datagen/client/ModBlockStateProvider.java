package me.towo.sculkmic.datagen.client;

import me.towo.sculkmic.SculkMicMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
        public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper helper) {
                super(gen, SculkMicMod.ID, helper);
        }

        @Override
        protected void registerStatesAndModels() {
                //simpleBlock(BlockINIT.BACKPACK.get());
        }
}
