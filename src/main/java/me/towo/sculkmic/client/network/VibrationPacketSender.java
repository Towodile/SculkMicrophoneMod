package me.towo.sculkmic.client.network;

import me.towo.sculkmic.server.network.ServerboundSculkVibrationPacket;
import me.towo.sculkmic.server.network.packet.PacketHandler;

public abstract class VibrationPacketSender extends ServerBoundPacketSender {
    protected VibrationPacketSender(int timeBetweenPacketsInTicks) {
        super(timeBetweenPacketsInTicks);
    }

    protected abstract int getFrequency();


    protected void sendPacket() {
        PacketHandler.INSTANCE.sendToServer(new ServerboundSculkVibrationPacket(getFrequency()));
    }
}
