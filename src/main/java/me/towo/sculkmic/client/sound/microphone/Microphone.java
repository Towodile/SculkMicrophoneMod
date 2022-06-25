package me.towo.sculkmic.client.sound.microphone;

import org.spongepowered.asm.mixin.injection.struct.TargetNotSupportedException;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Microphone extends Thread{

    /**
     * Microphone RMS level
     */
    public float level;
    public float minLevel;
    public float maxLevel;
    public List<ByteArrayOutputStream> recordedData;

    private final TargetDataLine line;
    private DataLine.Info info;
    private final AudioFormat format;
    private final Mixer device;

    private boolean run = false;
    private boolean record = false;

    /**
     * Creates a Microphone based on the given input device and audio format.
     * @param device A {@link Mixer} that holds info about an input device on this computer.
     * @throws LineUnavailableException If the line of the given device is unavailable.
     * @throws IllegalArgumentException If the given device does not support audio recording on TargetDataLine
     */
    public Microphone(Mixer device) throws LineUnavailableException, IllegalArgumentException {
        this.setName("Microphone Thread");
        this.device = device;
        this.format = new AudioFormat(42000.0f, 16, 1, true, false);
        info = new DataLine.Info(TargetDataLine.class, format);
        for (Line.Info dinfo : device.getTargetLineInfo()) {
            if (AudioSystem.isLineSupported(dinfo)) {
                info = (DataLine.Info) dinfo;
            }
        }
        line = (TargetDataLine) device.getLine(info);
        recordedData = new ArrayList<>();
    }

    /**
     * Starts the microphone.
     */
    @Override
    public void run() {
        tryStart();
    }

    public void closeAndStop() {
        if (line == null)
            return;

        if (line.isActive()) {
            line.stop();
        }

        if (line.isOpen()) {
            line.close();
        }

        line.flush();

        run = false;
    }

    public boolean available() {
        if (line != null && format != null && info != null) {
            return AudioSystem.isLineSupported(info);
        } return true;
    }

    public void open() throws LineUnavailableException {
      line.open(format);
    }

    private void tryStart() {
        run = true;
        if (!AudioSystem.isLineSupported(info)) {
            throw new TargetNotSupportedException("This target line is not supported!");
        }

        line.start();


        byte[] tempBuffer = new byte[6000];
        while (run) {
            int bytesRead = line.read(tempBuffer, 0, tempBuffer.length);
            if (bytesRead > 0) {
                level = calculateRMSLevel(tempBuffer);

                if (level < minLevel) {
                    minLevel = level;
                }

                if (minLevel < level) {
                    minLevel += .3f;
                }

                if (level > maxLevel) {
                    maxLevel = level;
                }

                if (record) {
                    ByteArrayOutputStream section = new ByteArrayOutputStream();
                    section.write(tempBuffer, 0, bytesRead);
                    recordedData.add(section);
                }
            }
        }
    }

    public void startRecording() {
        recordedData.clear();
        record = true;
    }

    /**
     * @return The recording.
     */
    public Recording stopRecording() {
        record = false;
        return new Recording(format, recordedData.toArray(new ByteArrayOutputStream[0]));
    }

    public Mixer.Info getDevice() {
        return device.getMixerInfo();
    }



    /**
     * @author <a href="https://danfoad.co.uk/blog/volume-meter-for-microphone-input-volume/">Dan Foad</a>
     */
    private static float calculateRMSLevel(byte[] audioData) {
        long lSum = 0;
        for(int i = 0; i < audioData.length; i++)
            lSum = lSum + audioData[i];

        double dAvg = lSum / audioData.length;

        double sumMeanSquare = 0d;
        for(int j = 0; j < audioData.length; j++)
            sumMeanSquare = sumMeanSquare + Math.pow(audioData[j] - dAvg, 2d);

        double averageMeanSquare = sumMeanSquare / audioData.length;
        return (float)(Math.pow(averageMeanSquare, 0.5F) + 0.5F);
    }

}
