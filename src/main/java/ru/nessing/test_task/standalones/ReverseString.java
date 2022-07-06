package ru.nessing.test_task.standalones;

public class ReverseString {
    public static void main(String[] args) {
        String str = "Simple";
        System.out.println(str);
        System.out.println(reverse(str));
        timeOfRevers(str, 1_000);
        timeOfRevers(str, 10_000);
        timeOfRevers(str, 100_000);
    }

    protected static void timeOfRevers(String str, int times) {
        int repeat = 0;
        long timerStart = System.currentTimeMillis();
        while (repeat < times) {
            reverse(str);
            repeat++;
        }
        System.out.println(System.currentTimeMillis() - timerStart);
    }

    protected static String reverse(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            result = str.charAt(i) + result;
        }
        return result;
    }
}
