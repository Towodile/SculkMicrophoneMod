package me.towo.sculkmic.common.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Chat {
    public static void sendMessage(String message, Player player){
        player.displayClientMessage(Component.nullToEmpty("§3[§bSculk Microphone§3]§r " + message), false);
    }
}
