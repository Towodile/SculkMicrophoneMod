package me.towo.sculkmic.datagen;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.datagen.client.ModBlockStateProvider;
import me.towo.sculkmic.datagen.client.ModItemModelProvider;
import me.towo.sculkmic.datagen.client.lang.ModEnUSProvider;
import me.towo.sculkmic.datagen.server.ModBlockTagsProvider;
import me.towo.sculkmic.datagen.server.ModItemTagsProvider;
import me.towo.sculkmic.datagen.server.ModLootTableProvider;
import me.towo.sculkmic.datagen.server.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Bus.MOD)

public class DataGeneration {
    @SubscribeEvent
    public static void GatherData(GatherDataEvent e){
        DataGenerator gen = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();
        if (e.includeClient()) {
            // Client data generation
            gen.addProvider(new ModBlockStateProvider(gen, helper));
            gen.addProvider(new ModItemModelProvider(gen, helper));
            gen.addProvider(new ModEnUSProvider(gen));
        }

        if (e.includeServer()) {
            // Server data generation
            gen.addProvider(new ModRecipeProvider((gen)));
            gen.addProvider(new ModBlockTagsProvider(gen, helper));
            gen.addProvider(new ModLootTableProvider(gen));

            ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, helper);
            gen.addProvider(blockTags);
            gen.addProvider(new ModItemTagsProvider(gen, blockTags, helper));
        }


    }
}
