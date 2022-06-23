package me.towo.sculkmic.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.towo.sculkmic.server.userpreferences.ServerSculkMicConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;


public class SetCommands {
    public SetCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /sculkmicrophone
        dispatcher.register(Commands.literal("sculkmicrophone")
                // /sculkmicrophone set
                .then(Commands.literal("set")
                        // '/sculkmicrophone set' requires permission 2.
                        .requires((source) -> source.hasPermission(2))

                            // /sculkmicrophone set distance
                            .then(Commands.literal("distance")
                                // /sculkmicrophone set distance <value>
                                .then(Commands.argument("distance", IntegerArgumentType.integer(0, 10))
                                     // on execution: calls setDistance
                                    .executes((command) -> setDistance(command.getSource(), IntegerArgumentType.getInteger(command, "distance")))))

                            // /sculkmicrophone set defaultRedstoneSignal
                            .then(Commands.literal("defaultRedstoneSignal")
                                 // /sculkmicrophone set defaultRedstoneSignal <value>
                                .then(Commands.argument("redstone", IntegerArgumentType.integer(1, 15))
                                     // on execution: calls setDefaultRedstoneStrength
                                    .executes((command) -> setDefaultRedstoneStrength(command.getSource(), IntegerArgumentType.getInteger(command, "redstone")))))

                            // /sculkmicrophone set doDynamicRedstone
                            .then(Commands.literal("doDynamicRedstone")
                                 // /sculkmicrophone set doDynamicRedstone <value>
                                .then(Commands.argument("dynRedstone", BoolArgumentType.bool())
                                     // on execution: calls setDynamicRedstone
                                    .executes((command) -> setDynamicRedstone(command.getSource(), BoolArgumentType.getBool(command,"dynRedstone")))))));
    }

    private int setDistance(CommandSourceStack source, int distance) throws CommandSyntaxException {
        ServerSculkMicConfig.SCULK_VIBRATION_DISTANCE.set(distance);
        Component msg = Component.literal("Max vibration distance succesfully set to " + distance);
        source.sendSuccess(msg, true);
        return 1;
    }

    private int setDefaultRedstoneStrength(CommandSourceStack source, int strength) throws CommandSyntaxException {
        ServerSculkMicConfig.DEFAULT_COMPARATOR_STRENGTH.set(strength);
        Component msg = Component.literal("Default comparator output successfully set to " + strength);
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
