package me.towo.sculkmic.server.userpreferences;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerSculkMicConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public final static ForgeConfigSpec.ConfigValue<Integer> SCULK_VIBRATION_DISTANCE;
    public final static ForgeConfigSpec.ConfigValue<Integer>  DEFAULT_COMPARATOR_STRENGTH;
    public final static ForgeConfigSpec.ConfigValue<Boolean> DO_DYNAMIC_REDSTONE;


    static {
        BUILDER.push("These options can also be configured in-game using '/sculkmicrophone set'");

        SCULK_VIBRATION_DISTANCE = BUILDER.comment("The max distance a player can be from a Sculk Block/Entity to send a signal.")
                .define("Vibration Distance", 6);

        DEFAULT_COMPARATOR_STRENGTH = BUILDER.comment("In case of inaccessibility to this mod's microphone, this default value will be used to determine a redstone comparators output.")
                .defineInRange("Default Comparator Output", 6, 1, 15);

        DO_DYNAMIC_REDSTONE = BUILDER.comment("Setting this to true will enable redstone with a comparator linked to them to dynamically change in signal strength depending on your volume. NOTE: This will only work for clients which have not installed the Simple Voice Chat mod.")
                        .define("Dynamic Redstone", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void editVibrationDistance(int distance) {
        SCULK_VIBRATION_DISTANCE.set(distance);
    }

    public static void editDefaultRedstoneStrength(int volume) {
        DEFAULT_COMPARATOR_STRENGTH.set(volume);
    }

    public static void editIfDynamicRedstone(boolean value) {
        DO_DYNAMIC_REDSTONE.set(value);
    }
}
