package me.towo.sculkmic.core.network;

import me.towo.sculkmic.core.userpreferences.ServerSculkMicConfig;
import me.towo.sculkmic.core.blockentity.SculkVibrationGenerator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerboundSculkVibrationPacket {
    public final int noiseLevel;
    public ServerboundSculkVibrationPacket(int noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public ServerboundSculkVibrationPacket() {
        this.noiseLevel = ServerSculkMicConfig.DEFAULT_NOISE_LEVEL;
    }


    public ServerboundSculkVibrationPacket (FriendlyByteBuf buffer) {
        this.noiseLevel = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(noiseLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var succes = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            SculkVibrationGenerator.generate(ctx.get().getSender(),
                    ServerSculkMicConfig.DEFAULT_VIBRATION_DISTANCE, noiseLevel);
        });

        ctx.get().setPacketHandled(true);
        return succes.get();
    }

}
