package me.towo.sculkmic.client.userpreferences;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.client.*;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    public static final OptionInstance<IconStatus> ICON_POSITION = new OptionInstance<>("options.mic.icon", OptionInstance.noTooltip(), OptionInstance.forOptionEnum(),
            new OptionInstance.Enum<>(Arrays.asList(IconStatus.values()),
                    Codec.INT.xmap(IconStatus::byId, IconStatus::getId)), IconStatus.TOP_LEFT, (value) -> {
        SculkMicConfig.store(SculkMicConfig.ICON, value.getId());
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
