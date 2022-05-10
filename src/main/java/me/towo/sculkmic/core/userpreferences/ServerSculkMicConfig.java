package me.towo.sculkmic.core.userpreferences;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerSculkMicConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public final static ForgeConfigSpec.ConfigValue<Integer> SCULK_VIBRATION_DISTANCE;
    public final static ForgeConfigSpec.ConfigValue<Integer>  DEFAULT_NOISE_LEVEL;


    static {
        BUILDER.push("These options can also be configured in-game using '/sculkmicrophone set'");

        SCULK_VIBRATION_DISTANCE = BUILDER.comment("The max distance a player can be from a Sculk Block/Entity to send a signal.")
                .define("Vibration Distance", 6);

        DEFAULT_NOISE_LEVEL = BUILDER.comment("In case of inaccessibility to this mod's microphone, this default value will be used to determine a redstone comparators output.")
                .define("Default Volume Level", 6);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void editVibrationDistance(int distance) {
        SCULK_VIBRATION_DISTANCE.set(distance);
    }

    public static void editDefaultVolume(int volume) {
        DEFAULT_NOISE_LEVEL.set(volume);
    }
}
