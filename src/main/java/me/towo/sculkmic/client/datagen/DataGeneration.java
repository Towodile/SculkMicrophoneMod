package me.towo.sculkmic.client.datagen;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.datagen.client.lang.ModEnUSProvider;
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
            gen.addProvider(new ModEnUSProvider(gen));
        }

        if (e.includeServer()) {
            // Server data generation

        }


    }
}
