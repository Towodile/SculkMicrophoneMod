package me.towo.sculkmic.client.voice.microphone;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.client.voice.VibrationPacketSender;
import me.towo.sculkmic.common.utils.Chat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
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
        validateMicrophone();
        super.tick(e);

        double threshold = SculkMicConfig.THRESHOLD.get();
        boolean playerIsLoud = handler.getCurrentLevel() > threshold;

        if (playerIsLoud) {
            schedulePacket();
        }
    }

    public void kill() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (handler.isRunning()) {
            handler.stop();
            SculkMicMod.LOGGER.info("Microphone has been closed and listener has been unregistered.");
            if (player != null)
                Chat.sendMessage(Component.translatable("microphone.info.closed").getString(), player);
        }

        SculkMicConfig.ENABLED.set(false);
        SculkMicConfig.SHOW_ICON.set(false);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public MicrophoneHandler getHandler() {
        return handler;
    }

    @Override
    protected int getFrequency() {
        double floor = 20;
        double roof = 100; // handler.maxLevel
        float current = handler.getCurrentLevel();

        double factor = (current - floor) / (roof - floor);
        return (int)Math.round(factor * 15);
    }

    private void validateMicrophone() {

        if (shouldBeRunning() && !handler.isRunning()) {
            tryStart();
        }

        if (!shouldBeRunning() && handler.isRunning()) {
            tryStop();
        }
    }

    private void tryStart(){
        LocalPlayer player = Minecraft.getInstance().player;
        if (handler.start()) {
            SculkMicMod.LOGGER.info("Microphone has been activated.");
            if (player != null)
                Chat.sendMessage(Component.translatable("microphone.info.opened").getString(), player);
        } else {
            SculkMicMod.LOGGER.error("Microphone is unavailable! Microphone has been disabled.");
            if (player != null) {
                Chat.sendMessage(Component.translatable("microphone.error.unavailable").getString(), player);
            }
            SculkMicConfig.ENABLED.set(false);
        }
    }

    private void tryStop(){
        LocalPlayer player = Minecraft.getInstance().player;
        handler.stop();
        if (player != null)
            Chat.sendMessage(Component.translatable("microphone.info.closed").getString(), player);
    }

    private boolean shouldBeRunning(){
        return SculkMicConfig.ENABLED.get();
    }

}
