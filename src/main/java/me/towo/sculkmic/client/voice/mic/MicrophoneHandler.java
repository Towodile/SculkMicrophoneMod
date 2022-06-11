package me.towo.sculkmic.client.voice.mic;

import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.compatibility.Dependencies;

public class MicrophoneHandler {

    private static Microphone microphone;

    public int getCurrentVolumeLevel() {
        if (Dependencies.SIMPLE_VOICE_CHAT.isPresent())
            return 0;


        if (isRunning()) {
            return (int)(microphone.level * SculkMicConfig.SENSITIVITY.get());
        } else return 0;
    }


    /**
     * Only one thread of class 'Microphone' may be running at a time.
     * Starting a new thread of class "Microphone" will kill the current thread.
     */
    public boolean startNewThread() {
        if (Dependencies.SIMPLE_VOICE_CHAT.isPresent())
            return false;

        stopCurrentThread();
        microphone = new Microphone();
        if (!microphone.available()) {
            return false;
        }

        microphone.start();
        return true;
    }

    public void stopCurrentThread() {
        if (Dependencies.SIMPLE_VOICE_CHAT.isPresent())
            return;

        if (isRunning()) {
            microphone.closeAndStop();
            microphone = null;
        }
    }

    public boolean isRunning() {
        if (Dependencies.SIMPLE_VOICE_CHAT.isPresent())
            return false;

        return microphone != null && microphone.isAlive();
    }

}
