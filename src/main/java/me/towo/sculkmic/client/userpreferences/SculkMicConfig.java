package me.towo.sculkmic.client.userpreferences;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.voice.AudioManager;
import me.towo.sculkmic.common.init.GlobalEventHandler;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public final class SculkMicConfig {
    public static final String FILE_NAME = "sculkmicmod-client.toml";
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLED;
    public static final ForgeConfigSpec.ConfigValue<Double> THRESHOLD;
    public static final ForgeConfigSpec.ConfigValue<String> INPUT_DEVICE;
    public static final ForgeConfigSpec.ConfigValue<Integer> ICON;

    private static final HashMap<ForgeConfigSpec.ConfigValue, Object> STORED_DATA = new HashMap<>();



    static {
        BUILDER.push("These options can also be configured in-game: Options -> Microphone Settings");

        ENABLED = BUILDER.comment("Set to 'true' to enable microphone listening.")
                .define("Enabled", true);

        THRESHOLD = BUILDER.comment("This value determines at what level (RMS) sculk sensors get activated.")
                .defineInRange("Sculk Threshold", 50, 0, 120d);

        INPUT_DEVICE = BUILDER.comment("The name of the device that's used for recording your voice.")
                .define("Input Device", AudioManager.Input.defaultDeviceName());

        ICON = BUILDER.comment("A value that determines where the icon that shows up when the sculk threshold has been reached will appear. 0 will disable the icon.")
                .defineInRange("Icon Position", 1, 0, 7);

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
            return;
        }

        for (Map.Entry<ForgeConfigSpec.ConfigValue, Object> entry : STORED_DATA.entrySet()) {
            entry.getKey().set(entry.getValue());
        }

        SculkMicMod.LOGGER.debug("Stored settings saved to config file " + FILE_NAME);
        clearStored();
    }

    public static void clearStored() {
        STORED_DATA.clear();
    }

    public static boolean hasStoredChanges() {
        return STORED_DATA.size() > 0;
    }

    public static void setInputDevice(String deviceName) {
        if (!AudioManager.Input.exists(deviceName)) {
            SculkMicMod.LOGGER.warn("The input device " + deviceName + " doesn't exist! Settings it to default.");
            setInputDevice(AudioManager.Input.defaultDeviceName());
        } else {
            INPUT_DEVICE.set(deviceName);
        }
        GlobalEventHandler.MICROPHONE_LISTENER.reloadMicrophone();
        return;
    }
}
