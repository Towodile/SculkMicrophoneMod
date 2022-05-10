package me.towo.sculkmic.client.event;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.common.compatibility.VoiceChatCompatibility;
import me.towo.sculkmic.client.mic.MicrophoneHandler;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.compatibility.utils.Chat;
import me.towo.sculkmic.core.network.ServerboundSculkVibrationPacket;
import me.towo.sculkmic.core.network.packet.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MicListener {

    private final static MicrophoneHandler handler = new MicrophoneHandler();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        checkMicrophone();

        if (Minecraft.getInstance().level == null)
            return;

        if (!SculkMicConfig.ENABLED.get() || !handler.isRunning())
            return;

        int noiseLevel = handler.getCurrentVolumeLevel();
        int threshold = SculkMicConfig.THRESHOLD.get();
        boolean playerIsLoud = noiseLevel > threshold;

        if (playerIsLoud) {
            PacketHandler.INSTANCE.sendToServer(new ServerboundSculkVibrationPacket(noiseLevel));
        }
    }

    // to-do make some chat messages translatable
    private static void checkMicrophone() {
        Player p = null;
        try {
            p = Minecraft.getInstance().player;
        }
        catch (NoSuchMethodError nsme){}

        if (!SculkMicConfig.ENABLED.get()) {
            if (handler.isRunning()) {
                handler.stopCurrentThread();

                SculkMicMod.LOGGER.info("Microphone has been closed.");
                if (p != null)
                    Chat.sendMessage("Microphone has been closed.", p);
            }
            return;
        }

        if (SculkMicConfig.ENABLED.get() && !handler.isRunning()) {
            if (handler.startNewThread()) {
                SculkMicMod.LOGGER.info("Microphone has been activated.");
                if (p != null)
                    Chat.sendMessage("Microphone has been activated.", p);
            } else {
                SculkMicMod.LOGGER.error("Microphone is unavailable! Microphone has been disabled.");
                if (p != null) {
                    if (!VoiceChatCompatibility.present)
                        Chat.sendMessage("§4ERROR: §cMicrophone is unavailable.", p);
                    else
                        Chat.sendMessage("Hold on! You currently have the §6Simple Voice Chat§r mod installed, " +
                                "meaning there's no need for §3Sculk Sensor Microphone's§r microphone to be turned on. " +
                                "Open your Voice Chat settings and edit your preferences there.", p);
                }
                SculkMicConfig.editIfEnabled(false);
            }
        }
    }

}
