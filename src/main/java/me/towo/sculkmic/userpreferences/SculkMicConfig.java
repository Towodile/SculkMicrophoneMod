package me.towo.sculkmic.userpreferences;

import net.minecraftforge.common.ForgeConfigSpec;

public final class SculkMicConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLED;
    public static final ForgeConfigSpec.ConfigValue<Integer> THRESHOLD;
    public static final ForgeConfigSpec.ConfigValue<Double> SENSITIVITY;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_INFO_ON_SCREEN;

    static {
        BUILDER.push("These options can also be configured in-game: Options -> Microphone Settings");

        ENABLED = BUILDER.comment("Set to 'true' to enable microphone listening.")
                .define("Enabled", true);

        THRESHOLD = BUILDER.comment("This value determines at what 'volume level' sculk sensors get activated.")
                .define("Volume Threshold", 5);

        SENSITIVITY = BUILDER.comment("The higher you set this factor, the easier your volume level will raise.")
                .defineInRange("Sensitivity", 0.80, 0.01, 1.00);

        SHOW_INFO_ON_SCREEN = BUILDER.comment("If 'true', your current volume and microphone status get displayed on-screen.")
                .define("Show Info", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void editIfEnabled(boolean isEnabled) {
        ENABLED.set(isEnabled);
    }

    public static void editThreshold(int threshold) {
        THRESHOLD.set(threshold);
        //SPEC.save();
    }

    public static void editIfInfoOnScreen(boolean showInfo) {
        SHOW_INFO_ON_SCREEN.set(showInfo);
        //SPEC.save();
    }

    public static void editSensitivity(double sensitivity) {
        SENSITIVITY.set(sensitivity);
        //SPEC.save();
    }
}
