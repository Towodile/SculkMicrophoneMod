package me.towo.sculkmic.server.network;

import me.towo.sculkmic.server.userpreferences.ServerSculkMicConfig;
import me.towo.sculkmic.server.blockentity.SculkVibrationGenerator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerboundSculkVibrationPacket {
    private final int noiseLevel;
    private final boolean doDynamically;

    public ServerboundSculkVibrationPacket(int noiseLevel) {
        this.noiseLevel = noiseLevel;
        this.doDynamically = ServerSculkMicConfig.DO_DYNAMIC_REDSTONE.get();
    }

    public ServerboundSculkVibrationPacket() {
        this.noiseLevel = 0;
        this.doDynamically = ServerSculkMicConfig.DO_DYNAMIC_REDSTONE.get();
    }


    public ServerboundSculkVibrationPacket (FriendlyByteBuf buffer) {
        this.noiseLevel = buffer.readInt();
        this.doDynamically = ServerSculkMicConfig.DO_DYNAMIC_REDSTONE.get() && noiseLevel > 0;
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(noiseLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var succes = new AtomicBoolean(false);
        if (doDynamically) {
            ctx.get().enqueueWork(() -> {
                SculkVibrationGenerator.generateDynamically(ctx.get().getSender(),
                        ServerSculkMicConfig.SCULK_VIBRATION_DISTANCE.get(), noiseLevel);
            });
        } else {
            ctx.get().enqueueWork(() -> {
                SculkVibrationGenerator.generate(ctx.get().getSender(),
                        ServerSculkMicConfig.SCULK_VIBRATION_DISTANCE.get(),
                        ServerSculkMicConfig.DEFAULT_COMPARATOR_STRENGTH.get());
            });
        }

        ctx.get().setPacketHandled(true);
        return succes.get();
    }

}
