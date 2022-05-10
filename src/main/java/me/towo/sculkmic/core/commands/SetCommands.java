package me.towo.sculkmic.core.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
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
                }))).then(Commands.literal("defaultVolume").then(Commands.argument("volume", IntegerArgumentType.integer(1))
                                .executes((command) -> {
                                    return setDefaultVolume(command.getSource(), IntegerArgumentType.getInteger(command, "volume"));
                                })))));
    }

    private int setDistance(CommandSourceStack source, int distance) throws CommandSyntaxException {
        ServerSculkMicConfig.editVibrationDistance(distance);
        Component msg = new TextComponent(new TranslatableComponent("commands.sculkmic.set.distance").getString() + distance);
        source.sendSuccess(msg, true);
        return 1;
    }

    private int setDefaultVolume(CommandSourceStack source, int volume) throws CommandSyntaxException {
        ServerSculkMicConfig.editDefaultVolume(volume);
        Component msg = new TextComponent(new TranslatableComponent("commands.sculkmic.set.volume").getString() + volume);
        source.sendSuccess(msg, true);
        return 1;
    }
}
