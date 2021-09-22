package util;

import java.util.Random;

public class Utilities {
    public static int rint(int min, int max) {

        if(min==max){
            return min;
        }

        if (min >= max) {
            return max;
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public static int rint(int min, int max, Random rand) {

        if(min==max){
            return min;
        }

        if (min >= max) {
            return max;
        }


        return rand.nextInt((max - min) + 1) + min;
    }
    public static double rdoub(double min, double max) {

        if(min==max){
            return min;
        }

        if (min >= max) {
            return max;
        }

        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
    public static double rdoub(double min, double max, Random rand) {

        if(min==max){
            return min;
        }

        if (min >= max) {
            return max;
        }


        return min + (max - min) * rand.nextDouble();
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static float scaleBetween(float unscaledNum, float minAllowed, float maxAllowed, float min, float max) {
        return (maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
    }

    public static double scaleBetween(double unscaledNum, double minAllowed, double maxAllowed, double min, double max) {
        return (maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
    }

    public static int scaleBetween(int unscaledNum, int minAllowed, int maxAllowed, int min, int max) {
        return (maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
    }

    public static void waitNanos(int delay){
        long start = System.nanoTime();
        while(System.nanoTime() - start < delay);
    }
}
