package me.towo.sculkmic.client.userpreferences;

import com.mojang.serialization.Codec;
import net.minecraft.client.*;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class ModOption {

    public static final OptionInstance<Boolean> ENABLE_MIC_LISTENING = OptionInstance.createBoolean("options.mic.enable", SculkMicConfig.ENABLED.get(), (p_231970_) -> {
        SculkMicConfig.store(SculkMicConfig.ENABLED , p_231970_);
    });

    public static final OptionInstance<Integer> SENSITIVITY = new OptionInstance<>("options.mic.sensitivity", OptionInstance.noTooltip(), (p_231962_, p_231963_) -> {
        return Options.genericValueLabel(p_231962_, Component.translatable("options.mic.sensitivityValue", p_231963_));
    }, new OptionInstance.IntRange(0, 100), SculkMicConfig.SENSITIVITY.get(), (value) -> {
        SculkMicConfig.store(SculkMicConfig.SENSITIVITY, value);
    });

    public static final OptionInstance<Integer> SCULK_THRESHOLD = new OptionInstance<>("options.mic.threshold", OptionInstance.noTooltip(), (p_231962_, p_231963_) -> {
        return Options.genericValueLabel(p_231962_, Component.translatable("options.mic.thresholdValue", p_231963_));
    }, new OptionInstance.IntRange(0, 120), (int)Math.round(SculkMicConfig.THRESHOLD.get()), (value) -> {
        SculkMicConfig.store(SculkMicConfig.THRESHOLD, (double)value);
    });

    public static final OptionInstance<Boolean> DO_ICON = OptionInstance.createBoolean("options.mic.show_info", SculkMicConfig.SHOW_ICON.get(), (value) -> {
        SculkMicConfig.store(SculkMicConfig.SHOW_ICON, value);
    });

    private static class Utils {
        private static double decimalVolume(int p_231966_) {
             return p_231966_ / 100d;
        }
        private static int noDecimalVolume(double p_231840_) {
            return (int)Math.round(p_231840_);
        }
    }


}
