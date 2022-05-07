package me.towo.sculkmic.common.compatibility;


import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.core.network.ServerboundSculkVibrationPacket;
import me.towo.sculkmic.core.network.packet.PacketHandler;

@ForgeVoicechatPlugin
public class VoiceChatListener implements VoicechatPlugin{

    @Override
    public String getPluginId() {
        return SculkMicMod.ID;
    }

    @Override
    public void initialize(VoicechatApi api) {
        SculkMicMod.LOGGER.info("Simple Voice Chat API initialized.");
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(ClientSoundEvent.class, this::onClientSound);
        SculkMicMod.LOGGER.info("ClientSoundEvent registered.");
    }

    public void onClientSound(ClientSoundEvent e) {
        PacketHandler.INSTANCE.sendToServer(new ServerboundSculkVibrationPacket());
    }

}