package me.towo.sculkmic.client.voice.mic.event;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.client.voice.VibrationPacketSender;
import me.towo.sculkmic.client.voice.mic.MicrophoneHandler;
import me.towo.sculkmic.common.compatibility.Dependencies;
import me.towo.sculkmic.common.utils.Chat;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public class MicrophoneListener extends VibrationPacketSender {

    // to-do improve microphone and volume calculations -> use DB
    private final static MicrophoneHandler handler = new MicrophoneHandler();

    public MicrophoneListener(int timeBetweenPacketsInTicks) {
        super(timeBetweenPacketsInTicks);
    }

    @Override
    protected void tick(TickEvent.ClientTickEvent e) {
        checkMicrophone();
        super.tick(e);

        int threshold = SculkMicConfig.THRESHOLD.get();
        boolean playerIsLoud = getVolume() > threshold;

        if (playerIsLoud) {
            schedulePacket();
        }
    }

    @Override
    protected int getVolume() {
        return handler.getCurrentVolumeLevel();
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
                    if (Dependencies.SIMPLE_VOICE_CHAT.isPresent())
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

    public void kill() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
