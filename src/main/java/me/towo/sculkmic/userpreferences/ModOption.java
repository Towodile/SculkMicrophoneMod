package me.towo.sculkmic.userpreferences;

import net.minecraft.client.*;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.server.command.TextComponentHelper;

import java.text.DecimalFormat;

@OnlyIn(Dist.CLIENT)
public class ModOption{
    public static final CycleOption ENABLE_MIC_LISTENING = CycleOption.createOnOff("options.mic.enable",
            (o) -> {
                return SculkMicConfig.ENABLED.get();
            },
            (o, o2, value) -> {
                SculkMicConfig.editIfEnabled(value);
            });

    public static final ProgressOption MICROPHONE_SENSITIVITY = new ProgressOption("options.mic.sensitivity", 0.01D, 1.00D, 0.01F,
            (o) -> {
        return SculkMicConfig.SENSITIVITY.get();
        },
            (o, value) -> {
        SculkMicConfig.editSensitivity(value);
        },
            (o, po) -> {
        double value = Math.round(po.get(o)*100.0)/100.0;
        return getComponentWithValue("options.mic.sensitivityValue", value);
            });

    public static final ProgressOption SCULK_THRESHOLD = new ProgressOption("options.mic.threshold", 0D, 50D, 1F,
            (o) -> {
        return SculkMicConfig.THRESHOLD.get().doubleValue();
        },
            (o, value) -> {
        SculkMicConfig.editThreshold(value.intValue());
        },
            (o, po) -> {
        Double value = po.get(o);
        return getComponentWithValue("options.mic.thresholdValue", value.intValue());
            });

    public static final CycleOption SHOW_ON_SCREEN_INFO = CycleOption.createOnOff("options.mic.show_info",
            (o) -> {
        return SculkMicConfig.SHOW_INFO_ON_SCREEN.get();
        },
            (o, o2, value) -> {
        SculkMicConfig.editIfInfoOnScreen(value);
        });

    private static TextComponent getComponentWithValue(String translatableKey, double value) {
        return new TextComponent(new TranslatableComponent(translatableKey).getString() + String.valueOf(value));
    }
}
