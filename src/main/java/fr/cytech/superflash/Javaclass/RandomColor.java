package fr.cytech.superflash.Javaclass;

import java.util.Random;

public class RandomColor {
    public static String generateRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return "rgba(" + r + "," + g + "," + b + ",1)";
    }
}
