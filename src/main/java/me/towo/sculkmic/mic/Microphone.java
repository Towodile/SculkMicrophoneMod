package me.towo.sculkmic.mic;

import me.towo.sculkmic.userpreferences.SculkMicConfig;

import javax.sound.sampled.*;

public class Microphone extends Thread{

    public enum Status {
        OK,
        CLOSED
    }

    private TargetDataLine line;
    public int level;

    @Override
    public void run(){
        tryOpenAndStart();
    }

    public void tryStop() {
        if (line == null)
            return;

        if (line.isActive()) {
            line.stop();
            if (line.isOpen()) {
                line.close();
            }
        }
    }



    private Status getStatus() {
        if (line != null && line.isActive()) {
            return Status.OK;
        }
        return Status.CLOSED;
    }

    // below code by Dan Foad
    // https://danfoad.co.uk/blog/volume-meter-for-microphone-input-volume/
    private void tryOpenAndStart() {

        // Open a TargetDataLine for getting mic input level
        AudioFormat format = new AudioFormat(42000.0f, 16, 1, true, true); // Get default line
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) { // If no default line
            System.out.println("The TargetDataLine is unavailable");
        }

        // Obtain and open the line.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
        } catch (LineUnavailableException ex) {
            System.out.println("The TargetDataLine is Unavailable.");
        }


        byte tempBuffer[] = new byte[6000]; // Data buffer for raw audio
        try {
            // Continually read in mic data into buffer and calculate RMS
            while (true) {
                // If read in enough, calculate RMS
                if (line.read(tempBuffer, 0, tempBuffer.length) > 0) {
                    level = calculateRMSLevel(tempBuffer);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    private static int calculateRMSLevel(byte[] audioData) {
        long lSum = 0;
        for(int i = 0; i < audioData.length; i++)
            lSum = lSum + audioData[i];

        double dAvg = lSum / audioData.length;

        double sumMeanSquare = 0d;
        for(int j = 0; j < audioData.length; j++)
            sumMeanSquare = sumMeanSquare + Math.pow(audioData[j] - dAvg, 2d);

        double averageMeanSquare = sumMeanSquare / audioData.length;
        return (int)(Math.pow(averageMeanSquare, 0.5d) + 0.5) - 50;
    }

}
