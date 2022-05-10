package me.towo.sculkmic.client.mic;

import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.compatibility.VoiceChatCompatibility;

public class MicrophoneHandler {
    private static Microphone microphone;

    public int getCurrentVolumeLevel() {
        if (VoiceChatCompatibility.present)
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
        if (VoiceChatCompatibility.present)
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
        if (VoiceChatCompatibility.present)
            return;

        if (isRunning()) {
            microphone.closeAndStop();
            microphone = null;
        }
    }

    public boolean isRunning() {
        if (VoiceChatCompatibility.present)
            return false;

        return microphone != null && microphone.isAlive();
    }

}
