package me.towo.sculkmic.client.sound.microphone;

import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.client.sound.AudioManager;

import javax.annotation.Nullable;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class MicrophoneHandler {

    @Nullable
    private Microphone microphone;

    public float getCurrentLevel() {
        if (isRunning()) {
            assert microphone != null;
            if (microphone.level > 0) {
                return microphone.level;
            }
        }

        return 0;
    }

    public float getMinimumLevel() {
        if (isRunning()) {
            assert microphone != null;
            return microphone.minLevel;
        }

        return 0;
    }

    /**
     * Will try to start the microphone. If one is already running, it will restart it.
     */
    public boolean start() {

        stop();
        try {
            Mixer mixer = AudioManager.Input.get(SculkMicConfig.INPUT_DEVICE.get());
            microphone = new Microphone(mixer);
        } catch (LineUnavailableException e) {
            return false;
        }

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
            assert microphone != null;
            microphone.closeAndStop();
            microphone = null;
        }
    }

    public boolean isRunning() {
        return microphone != null && microphone.isAlive();
    }

    public String getDevice() {
        if (microphone == null)
            return null;
        return microphone.getDevice().getName();
    }
}
