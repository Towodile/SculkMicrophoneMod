package me.towo.sculkmic.mic;

import me.towo.sculkmic.userpreferences.SculkMicConfig;

public class MicrophoneHandler {
    private static Microphone microphone;

    public static int getCurrentVolumeLevel() {
        if (isRunning()) {
            return (int)(microphone.level * SculkMicConfig.SENSITIVITY.get());
        } else return 0;
    }


    /**
     * Only one thread of class 'Microphone' may be running at a time.
     * Starting a new thread of class "Microphone" will kill the current thread.
     */
    public static boolean startNewThread() {
        microphone = new Microphone();
        if (!microphone.available()) {
            return false;
        }

        stopCurrentThread();
        microphone.start();
        return true;
    }

    public static void stopCurrentThread() {
        if (isRunning()) {
            microphone.closeAndStop();
            microphone = null;
        }
    }

    public static boolean isRunning() {
        return microphone != null && microphone.isAlive();
    }

}
