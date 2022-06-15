package me.towo.sculkmic.client.voice.microphone;

import javax.sound.sampled.*;

public class Microphone extends Thread{

    /**
     * Microphone RMS level
     */
    public float level;
    public float minLevel;
    public float maxLevel;
    private float lastLevel;

    private TargetDataLine line;
    private AudioFormat format;
    private DataLine.Info info;
    private boolean run = false;

    @Override
    public void run(){
        tryOpenAndStart();
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


    private void tryOpenAndStart() {
        run = true;

        format = new AudioFormat(42000.0f, 16, 1, true, false);
        info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) { // If no default line
            System.out.println("SCULK MIC: The TargetDataLine is unavailable");
            System.exit(1);
        }

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
        } catch (LineUnavailableException ex) {
            System.out.println("SCULK MIC: The TargetDataLine is Unavailable.");
            System.exit(1);
        }


        byte[] tempBuffer = new byte[6000];
        try {
            while (run) {
                // If read in enough, calculate RMS
                if (line.read(tempBuffer, 0, tempBuffer.length) > 0) {
                    level = calculateRMSLevel(tempBuffer);

                    if (level < minLevel) {
                        minLevel = level;
                    }

                    if (level > maxLevel) {
                        maxLevel = level;
                    }

                    lastLevel = level;
                }
            }
        } catch (Exception e) {
            System.out.println("SCULK MIC: Something went wrong while recording!");
            System.err.println(e);
            System.exit(2);
        }
    }

    public void setNewDevice() {
        throw new UnsupportedOperationException();
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
