package banking.utils;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static int getIntegerInput() {
        return SCANNER.nextInt();
    }

    public static String getStringInput() {
        return SCANNER.next();
    }
}
