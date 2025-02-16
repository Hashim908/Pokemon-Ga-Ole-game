package main.java.core;

public class TextUtils {
    private static int delay = 30;

    public static void setDelay(int delay) {
        TextUtils.delay = delay;
    }

    public static void printWithDelay(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}
