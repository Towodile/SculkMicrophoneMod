package me.towo.sculkmic.common.init;

import me.towo.sculkmic.client.voice.mic.event.MicrophoneListener;
import me.towo.sculkmic.client.voice.simplevoicechat.VoiceChatListener;
import me.towo.sculkmic.client.voice.simplevoicechat.VoiceChatPacketSender;

public final class GlobalEventHandler {
    public final static MicrophoneListener MICROPHONE_LISTENER = new MicrophoneListener(40);
    public final static VoiceChatPacketSender VOICE_CHAT_LISTENER = new VoiceChatListener().sender;
}
