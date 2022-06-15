package me.towo.sculkmic.client.voice.microphone;

import javax.annotation.Nullable;

public class MicrophoneHandler {

    @Nullable
    private Microphone microphone;
    private float lastMinLevel;

    public float getCurrentLevel() {
        if (isRunning()) {
            assert microphone != null;
            if (microphone.level > 0) {
                lastMinLevel = microphone.minLevel;
                return microphone.level;
            }
        }

        return 0;
    }


    /**
     * Will try to start the microphone. If one is already running, it will restart it.
     */
    public boolean start() {

        stop();
        microphone = new Microphone();
        if (!microphone.available()) {
            return false;
        }

        microphone.start();
        return true;
    }

    /**
     * Will try to stop the microphone.
     */
    public void stop() {
        if (isRunning()) {
            microphone.closeAndStop();
            microphone = null;
        }
    }

    public boolean isRunning() {
        return microphone != null && microphone.isAlive();
    }

}
