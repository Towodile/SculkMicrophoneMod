package me.towo.sculkmic.client.userpreferences;

import me.towo.sculkmic.SculkMicMod;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public final class SculkMicConfig {
    public static final String FILE_NAME = "sculkmicmod-client.toml";
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLED;
    public static final ForgeConfigSpec.ConfigValue<Double> THRESHOLD;
    public static final ForgeConfigSpec.ConfigValue<Integer> SENSITIVITY;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_ICON;

    private static final HashMap<ForgeConfigSpec.ConfigValue, Object> STORED_DATA = new HashMap<>();



    static {
        BUILDER.push("These options can also be configured in-game: Options -> Microphone Settings");

        ENABLED = BUILDER.comment("Set to 'true' to enable microphone listening.")
                .define("Enabled", true);

        THRESHOLD = BUILDER.comment("This value determines at what level (RMS) sculk sensors get activated.")
                .defineInRange("Sculk Threshold", 50, 0, 120d);

        SENSITIVITY = BUILDER.comment("The higher you set this factor, the easier your volume level will raise.")
                .defineInRange("Sensitivity", 1, 1, 100);

        SHOW_ICON = BUILDER.comment("If 'true', an icon will appear on your screen once the volume threshold has been met.")
                .define("Show Info", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }


    /**
     * Stores the given value with the given config entity.
     * To save all stored data, use {@link #saveStoredToConfig()}.
     */
    public static <T> void store(ForgeConfigSpec.ConfigValue<T> configEntity, T newValue) {
        STORED_DATA.put(configEntity, newValue);
    }

    /**
     * Saves all currently stored data to the right config file(s). Stored data will get cleared afterwards.
     */
    public static void saveStoredToConfig() {
        if (!hasStoredChanges()) {
            SculkMicMod.LOGGER.info("Attempted to save stored data to config, but there were no changes!");
            return;
        }
        for (Map.Entry<ForgeConfigSpec.ConfigValue, Object> entry : STORED_DATA.entrySet()) {
            entry.getKey().set(entry.getValue());
        }

        SculkMicMod.LOGGER.info("New client-side settings saved to config file " + FILE_NAME);
        clearStored();
    }

    public static void clearStored() {
        STORED_DATA.clear();
    }

    public static boolean hasStoredChanges() {
        return STORED_DATA.size() > 0;
    }
}
