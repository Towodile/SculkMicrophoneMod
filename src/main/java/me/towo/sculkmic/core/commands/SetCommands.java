package me.towo.sculkmic.core.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sun.jdi.connect.Connector;
import me.towo.sculkmic.core.userpreferences.ServerSculkMicConfig;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class SetCommands {
    public SetCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> lab = Commands.literal("distance");

        dispatcher.register(Commands.literal("sculkmicrophone")
                .then(Commands.literal("set").requires((source) -> {
                    return source.hasPermission(2);
                })
                .then(Commands.literal("distance").then(Commands.argument("distance", IntegerArgumentType.integer(0))
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
        ServerSculkMicConfig.editVibrationDistance(distance);
        Component msg = new TextComponent(new TranslatableComponent("commands.sculkmic.set.distance").getString() + distance);
        source.sendSuccess(msg, true);
        return 1;
    }

    private int setDefaultRedstoneStrength(CommandSourceStack source, int strength) throws CommandSyntaxException {
        ServerSculkMicConfig.editDefaultRedstoneStrength(strength);
        Component msg = new TextComponent(new TranslatableComponent("commands.sculkmic.set.comparator").getString() + strength);
        source.sendSuccess(msg, true);
        return 1;
    }

    private int setDynamicRedstone(CommandSourceStack source, boolean value) throws CommandSyntaxException {
        ServerSculkMicConfig.editIfDynamicRedstone(value);
        Component msg = new TextComponent(new TranslatableComponent("commands.sculkmic.set.comparator").getString() + value);
        source.sendSuccess(msg, true);
        return 1;
    }
}
