package me.towo.sculkmic.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public final class SculkMicConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> THRESHOLD;
    public static final ForgeConfigSpec.ConfigValue<Double> SENSITIVITY;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_INFO_ON_SCREEN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> MESSAGE_ON_JOIN;

    static {
        BUILDER.push("Configure the 'Sculk Microphone' mod settings here!");

        THRESHOLD = BUILDER.comment("This value determines at what 'volume level' sculk sensors get activated.")
                .define("Volume Threshold", 5);

        SENSITIVITY = BUILDER.comment("The higher you set this factor, the easier your volume level will raise.")
                .defineInRange("Sensitivity", 0.80, 0.01, 1.00);

        SHOW_INFO_ON_SCREEN = BUILDER.comment("If 'true', your current volume and microphone status get displayed on-screen.")
                .define("Show Info", false);

        MESSAGE_ON_JOIN = BUILDER.comment("If 'true', a message containing useful information will be sent in chat once you join a game. ")
                .define("Send Message on Join", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void editThreshold(int threshold) {
        THRESHOLD.set(threshold);
        SPEC.save();
    }

    public static void editIfInfoOnScreen(boolean showInfo) {
        SHOW_INFO_ON_SCREEN.set(showInfo);
        SPEC.save();
    }

    public static void editIfMessageOnJoin(boolean messageOnJoin) {
        SHOW_INFO_ON_SCREEN.set(messageOnJoin);
        SPEC.save();
    }

    public static void editSensitivity(double sensitivity) {
        SENSITIVITY.set(sensitivity);
        SPEC.save();
    }
}
