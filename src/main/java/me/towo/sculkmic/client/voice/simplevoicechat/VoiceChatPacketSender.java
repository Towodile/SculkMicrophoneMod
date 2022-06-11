package me.towo.sculkmic.client.voice.simplevoicechat;

import me.towo.sculkmic.client.voice.VibrationPacketSender;

public class VoiceChatPacketSender extends VibrationPacketSender {

    public VoiceChatPacketSender(int timeBetweenPacketsInTicks) {
        super(timeBetweenPacketsInTicks);
    }

    @Override
    protected int getVolume() {
        return 0;
    }
}
