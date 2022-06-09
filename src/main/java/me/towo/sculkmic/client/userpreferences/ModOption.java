package me.towo.sculkmic.client.userpreferences;

import net.minecraft.client.*;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class ModOption {

    public static final OptionInstance<Boolean> ENABLE_MIC_LISTENING = OptionInstance.createBoolean("options.mic.enable", SculkMicConfig.ENABLED.get(), (p_231970_) -> {
        SculkMicConfig.editIfEnabled(p_231970_);
    });

    public static final OptionInstance<Integer> SENSITIVITY = new OptionInstance<>("options.mic.sensitivity", OptionInstance.noTooltip(), (p_231962_, p_231963_) -> {
        return Options.genericValueLabel(p_231962_, Component.translatable("options.mic.sensitivityValue", p_231963_));
    }, new OptionInstance.IntRange(1, 100), SculkMicConfig.SENSITIVITY.get().intValue(), (value) -> {
        SculkMicConfig.editSensitivity(value);
    });

    public static final OptionInstance<Integer> SCULK_THRESHOLD = new OptionInstance<>("options.mic.threshold", OptionInstance.noTooltip(), (p_231962_, p_231963_) -> {
        return Options.genericValueLabel(p_231962_, Component.translatable("options.mic.decibel", p_231963_));
    }, new OptionInstance.IntRange(1, 100), SculkMicConfig.THRESHOLD.get().intValue(), (value) -> {
        SculkMicConfig.editThreshold(value);
    });

    public static final OptionInstance<Boolean> INFO_ONSCREEN = OptionInstance.createBoolean("options.mic.show_info", SculkMicConfig.SHOW_INFO_ON_SCREEN.get(), (value) -> {
        SculkMicConfig.editIfInfoOnScreen(value);
    });
}
