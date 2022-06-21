package me.towo.sculkmic.client.sound.microphone;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.gui.init.ModScreenIcon;
import me.towo.sculkmic.client.userpreferences.IconStatus;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.client.sound.VibrationPacketSender;
import me.towo.sculkmic.common.utils.Chat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public class MicrophoneListener extends VibrationPacketSender {

    private final static MicrophoneHandler HANDLER = new MicrophoneHandler();

    public MicrophoneListener(int timeBetweenPacketsInTicks) {
        super(timeBetweenPacketsInTicks);
        ModScreenIcon.THRESHOLD_ALERT.create();
    }

    @Override
    protected void tick(TickEvent.ClientTickEvent e) {
        validateMicrophone();
        super.tick(e);

        double threshold = SculkMicConfig.THRESHOLD.get();
        boolean playerIsLoud = HANDLER.getCurrentLevel() > threshold;

        if (playerIsLoud) {
            if (SculkMicConfig.ICON.get() != 0) {
                ModScreenIcon.THRESHOLD_ALERT.setPosition(IconStatus.byId(SculkMicConfig.ICON.get()).getCorrespondingPosition());
                ModScreenIcon.THRESHOLD_ALERT.show();
            }
            schedulePacket();
        } else ModScreenIcon.THRESHOLD_ALERT.hide();
    }

    public void reloadMicrophone() {
        SculkMicMod.LOGGER.info("Restarting microphone...");
        HANDLER.stop();
        if (HANDLER.start()) {
            SculkMicMod.LOGGER.info("Microphone '" + HANDLER.getDevice() + "' has been activated.");
        } else SculkMicMod.LOGGER.error("Something went wrong trying to restart the microphone.");
    }

    public void kill() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (HANDLER.isRunning()) {
            HANDLER.stop();
            SculkMicMod.LOGGER.info("Microphone has been closed and listener has been unregistered.");
            if (player != null)
                Chat.sendMessage(Component.translatable("microphone.info.closed").getString(), player);
        }

        SculkMicConfig.ENABLED.set(false);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public MicrophoneHandler getHandler() {
        return HANDLER;
    }

    @Override
    protected int getFrequency() {
        double floor = HANDLER.getMinimumLevel();
        double roof = 100;
        float current = HANDLER.getCurrentLevel();

        double factor = (current - floor) / (roof - floor);
        return (int)Math.round(factor * 15);
    }

    private void validateMicrophone() {

        if (shouldBeRunning() && !HANDLER.isRunning()) {
            tryStart();
        }

        if (!shouldBeRunning() && HANDLER.isRunning()) {
            tryStop();
        }
    }

    private void tryStart(){
        LocalPlayer player = Minecraft.getInstance().player;
        if (HANDLER.start()) {
            SculkMicMod.LOGGER.info("Microphone '" + HANDLER.getDevice() + "' has been activated.");
            if (player != null)
                Chat.sendMessage(Component.translatable("microphone.info.opened").getString(), player);
        } else {
            SculkMicMod.LOGGER.error("Microphone '" + HANDLER.getDevice() + "' is unavailable! It has been disabled.");
            if (player != null) {
                Chat.sendMessage(Component.translatable("microphone.error.unavailable").getString(), player);
            }
            SculkMicConfig.ENABLED.set(false);
        }
    }

    private void tryStop(){
        LocalPlayer player = Minecraft.getInstance().player;
        HANDLER.stop();
        if (player != null)
            Chat.sendMessage(Component.translatable("microphone.info.closed").getString(), player);
    }

    private boolean shouldBeRunning(){
        return SculkMicConfig.ENABLED.get();
    }

}
