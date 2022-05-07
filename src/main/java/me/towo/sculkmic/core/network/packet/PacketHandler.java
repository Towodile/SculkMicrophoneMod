package me.towo.sculkmic.core.network.packet;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.core.network.ServerboundSculkVibrationPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
    private PacketHandler() {

    }
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(SculkMicMod.ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        int index = 0;
        INSTANCE.messageBuilder(ServerboundSculkVibrationPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerboundSculkVibrationPacket::encode).decoder(ServerboundSculkVibrationPacket::new)
                .consumer(ServerboundSculkVibrationPacket::handle).add();
    }
}
