package me.towo.sculkmic.client.datagen.client.lang;

import me.towo.sculkmic.SculkMicMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUSProvider extends LanguageProvider {
    public ModEnUSProvider(DataGenerator gen) {
        super(gen, SculkMicMod.ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        // GUI
        add("gui.microphone_settings", "Microphone Settings");
    }
}