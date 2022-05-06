package me.towo.sculkmic.mic;

public class MicrophoneHandler {
    private static final Microphone microphone = new Microphone();

    public static Microphone getMic() {
        return microphone;
    }
}
