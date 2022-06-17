package me.towo.sculkmic.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.towo.sculkmic.server.userpreferences.ServerSculkMicConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;


public class SetCommands {
    public SetCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sculkmicrophone")
                .then(Commands.literal("set").requires((source) -> {
                    return source.hasPermission(2);
                })
                .then(Commands.literal("distance").then(Commands.argument("distance", IntegerArgumentType.integer(0, 10))
                .executes((command) -> {
                    return setDistance(command.getSource(), IntegerArgumentType.getInteger(command, "distance"));
                }))).then(Commands.literal("defaultRedstoneSignal").then(Commands.argument("redstone", IntegerArgumentType.integer(1, 15))
                                .executes((command) -> {
                                    return setDefaultRedstoneStrength(command.getSource(), IntegerArgumentType.getInteger(command, "redstone"));
                                }))).then(Commands.literal("doDynamicRedstone").then(Commands.argument("dynRedstone", BoolArgumentType.bool())
                                .executes((command) -> {
                                    return setDynamicRedstone(command.getSource(), BoolArgumentType.getBool(command,"dynRedstone"));
                                })))));
    }

    private int setDistance(CommandSourceStack source, int distance) throws CommandSyntaxException {
        ServerSculkMicConfig.SCULK_VIBRATION_DISTANCE.set(distance);
        Component msg = Component.literal("Max vibration distance succesfully set to " + distance);
        source.sendSuccess(msg, true);
        return 1;
    }

    private int setDefaultRedstoneStrength(CommandSourceStack source, int strength) throws CommandSyntaxException {
        ServerSculkMicConfig.DEFAULT_COMPARATOR_STRENGTH.set(strength);
        Component msg = Component.literal("Default comparator output successfully set to" + strength);
        source.sendSuccess(msg, true);
        return 1;
    }

    private int setDynamicRedstone(CommandSourceStack source, boolean value) throws CommandSyntaxException {
        ServerSculkMicConfig.DO_DYNAMIC_REDSTONE.set(value);
        Component msg = Component.literal("Dynamic redstone comparator output successfully set to " + value);
        source.sendSuccess(msg, true);
        return 1;
    }
}
