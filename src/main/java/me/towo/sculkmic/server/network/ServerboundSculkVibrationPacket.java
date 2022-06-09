package me.towo.sculkmic.server.network;

import me.towo.sculkmic.server.blockentity.SculkVibration;
import me.towo.sculkmic.server.userpreferences.ServerSculkMicConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerboundSculkVibrationPacket {
    private final int comparatorOutput;

    public ServerboundSculkVibrationPacket(int comparatorStrength) {
        this.comparatorOutput = comparatorStrength;
    }

    public ServerboundSculkVibrationPacket (FriendlyByteBuf buffer) {
        this.comparatorOutput = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(comparatorOutput);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        SculkVibration vibration = new SculkVibration(ctx.get().getSender(), ServerSculkMicConfig.SCULK_VIBRATION_DISTANCE.get());

        ctx.get().enqueueWork(() -> {
            vibration.generate(comparatorOutput);
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }

}
