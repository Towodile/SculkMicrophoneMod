package me.towo.sculkmic.common.compatibility;

import net.minecraftforge.fml.ModList;

public class CompatibilityRegister {
    private final ModDependency[] dependencies = new ModDependency[]{
            Dependencies.SIMPLE_VOICE_CHAT
    };

    public void registerAll() {
        for (ModDependency mod : dependencies) {
            if (ModList.get().isLoaded(mod.modid)){
                mod.setPresent(true);
                mod.onFind();
            }
        }
    }

}
