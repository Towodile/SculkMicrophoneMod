package me.towo.sculkmic.client.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ServerBoundPacketSender {

    private final int timeout;

    /**
     * A timer. Gets incremented every tick, until it hits the same value as {@link #timeout}. In that case, it will be reset to 0 and start over.
     */
    private int passedTicks;

    /**
     * If true, a packet can be sent.
     */
    private final AtomicBoolean canSendPacket;

    protected ServerBoundPacketSender(int timeBetweenPacketsInTicks) {
        this.timeout = timeBetweenPacketsInTicks;
        canSendPacket = new AtomicBoolean(false);
    }

    /**
     * An event fired every (client-side) tick; in this event, {@link #sendPacket()} gets called when {@link #timeout} is exceeded and a packet is scheduled.
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

    /**
     * This method schedules sending a packet by setting {@link #canSendPacket} to true, meaning once enough time has passed, a packet will be sent.
     * <br><br>
     * <i>Overriding this method without using <code>super.sendPacket();</code> will cancel it.</i>
     */
    public void schedulePacket() {
        canSendPacket.compareAndSet(false, true);
    }
    
    /**
     * This method is called everytime {@link #passedTicks} reaches the {@link #timeout} limit while {@link #canSendPacket} is true.
     */
    protected abstract void sendPacket();
}
