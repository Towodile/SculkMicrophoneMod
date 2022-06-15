package me.towo.sculkmic.common.compatibility;

import me.towo.sculkmic.SculkMicMod;
import net.minecraftforge.fml.ModList;

public class CompatibilityRegister {
    private final ModDependency[] dependencies = new ModDependency[]{
    };

    public void findAll() {
        for (ModDependency mod : dependencies) {
            if (ModList.get().isLoaded(mod.modid)){
                SculkMicMod.LOGGER.info("Mod matching id {} succesfully located.", mod.modid);
                mod.setPresent(true);
                mod.onFind();
            }
        }
    }

}
