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
    public static void startNewThread() {
        stopCurrentThread();
        microphone = new Microphone();
        microphone.start();
    }

    public static void stopCurrentThread() {
        if (isRunning()) {
            microphone.interrupt();
            microphone = null;
        }
    }

    public static boolean isRunning() {
        return microphone != null && microphone.isAlive();
    }

}
