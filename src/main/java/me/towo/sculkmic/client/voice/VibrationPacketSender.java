package me.towo.sculkmic.client.voice;

import me.towo.sculkmic.server.network.ServerboundSculkVibrationPacket;
import me.towo.sculkmic.server.network.packet.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class VibrationPacketSender {
    private final int timeout;

    /**
     * A timer. Gets incremented every tick, until it hits the same value as {@link #timeout}. In that case, it will be 0 again.
     */
    private int passedTicks;

    /**
     * If true, a packet can be send.
     */
    private final AtomicBoolean canSendPacket;

    public VibrationPacketSender(int timeBetweenPacketsInTicks){
        this.timeout = timeBetweenPacketsInTicks;
        canSendPacket = new AtomicBoolean(false);
    }

    /**
     * An event fired every (client-side) tick; in this event, {@link #sendPacket()} gets called when enough time passes.
     */
    @SubscribeEvent
    protected void tick(TickEvent.ClientTickEvent e) {
        if (Minecraft.getInstance().level == null) {
            return;
        }

        if (canSendPacket.get()) {
            if (canSendPacket.compareAndSet(true, false) && passedTicks > timeout) {
                sendPacket();
                passedTicks = 0;
            }
        }
        passedTicks++;
    }

    protected abstract int getFrequency();

    /**
     * This method is called everytime {@link #passedTicks} reaches the {@link #timeout} limit while {@link #canSendPacket} is true.
     */
    private void sendPacket() {
        // to-do : calculate comparator strength based on getVolume() instead of it being a linear comparison
        PacketHandler.INSTANCE.sendToServer(new ServerboundSculkVibrationPacket((getFrequency())));
    }

    /**
     * This method schedules sending a packet by setting {@link #canSendPacket} to true, meaning once enough time has passed, a packet will be sent.
     * <br><br>
     * <i>Overriding this method without using <code>super.sendPacket();</code> will cancel it.</i>
     */
    public void schedulePacket() {
        canSendPacket.compareAndSet(false, true);
    }
}
