package me.towo.sculkmic.client.sound.microphone;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class Recording extends Thread {
    private final AudioFormat format;
    private final SourceDataLine line;
    private final ByteArrayOutputStream[] data;
    private boolean canPlay = true;
    public Recording(AudioFormat format, ByteArrayOutputStream[] recordedBytes){
        this.format = format;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        data = recordedBytes;
    }

    public void run() {
        try {
            line.open(format);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        play();
    }

    private void play() {
        if (!line.isActive()) {
            line.start();
        }

        for (ByteArrayOutputStream section : data) {
            if (!canPlay)
                break;

            line.write(section.toByteArray(), 0, section.toByteArray().length);
        }
    }

    public void stopPlaying() {
        canPlay = false;
        this.interrupt();
    }
}
