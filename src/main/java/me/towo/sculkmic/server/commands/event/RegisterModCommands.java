package me.towo.sculkmic.server.commands.event;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.server.commands.SetCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID)
public class RegisterModCommands {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent e) {
         new SetCommands(e.getDispatcher());
        ConfigCommand.register(e.getDispatcher());
    }
}
