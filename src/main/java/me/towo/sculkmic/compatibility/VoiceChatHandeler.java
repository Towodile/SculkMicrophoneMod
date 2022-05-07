package me.towo.sculkmic.compatibility;


import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.audio.AudioConverter;
import de.maxhenkel.voicechat.api.audiochannel.AudioChannel;
import de.maxhenkel.voicechat.api.audiochannel.EntityAudioChannel;
import de.maxhenkel.voicechat.api.events.*;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoderMode;
import me.towo.sculkmic.SculkMicMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

public class VoiceChatHandeler implements VoicechatPlugin{

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
        System.out.println("Client sound packet! " + Minecraft.getInstance().player.blockPosition());
    }

}
