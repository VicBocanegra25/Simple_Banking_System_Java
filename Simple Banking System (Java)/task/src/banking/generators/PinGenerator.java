package banking.generators;

import java.util.Random;

public class PinGenerator {
    public static String generatePin() {
        Random rand = new Random();
        return String.format("%d", rand.nextInt(1000,9999));
    }
}
