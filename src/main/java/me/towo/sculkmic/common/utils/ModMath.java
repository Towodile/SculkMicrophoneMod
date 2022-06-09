package me.towo.sculkmic.common.utils;

public class ModMath {
    public static int getNearestInteger(int number, int[] array) {
        int distance = java.lang.Math.abs(array[0] - number);
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            int idistance = java.lang.Math.abs(array[i] - number);

            if(idistance < distance){
                index = i;
                distance = idistance;
            }
        }
        return index;
    }
}
