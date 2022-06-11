package me.towo.sculkmic.client.voice.simplevoicechat;


import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import me.towo.sculkmic.SculkMicMod;
import net.minecraftforge.common.MinecraftForge;

@ForgeVoicechatPlugin
public class VoiceChatListener implements VoicechatPlugin {

    public final VoiceChatPacketSender sender;
    public VoiceChatListener (){
        sender = new VoiceChatPacketSender(40);
        MinecraftForge.EVENT_BUS.register(sender);
    }

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
    }

    public void onClientSound(ClientSoundEvent e) {
        sender.schedulePacket();
    }
}
