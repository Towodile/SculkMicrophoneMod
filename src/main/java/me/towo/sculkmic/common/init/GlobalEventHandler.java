package me.towo.sculkmic.common.init;

import me.towo.sculkmic.client.voice.microphone.MicrophoneListener;

public final class GlobalEventHandler {
    public final static MicrophoneListener MICROPHONE_LISTENER = new MicrophoneListener(40);
}
