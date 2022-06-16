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
                .defineInRange("Vibration Distance", 6, 1, 10);

        DO_DYNAMIC_REDSTONE = BUILDER.comment("Setting this to true will enable redstone with a comparator linked to them to dynamically change in signal strength depending on your volume. NOTE: This will only work for clients which have not installed the Simple Voice Chat mod.")
                .define("Dynamic Redstone", true);

        DEFAULT_COMPARATOR_STRENGTH = BUILDER.comment("If 'Dynamic Redstone' is disabled, this default value will be used as the redstone comparator's output.")
                .defineInRange("Default Comparator Output", 6, 1, 15);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
