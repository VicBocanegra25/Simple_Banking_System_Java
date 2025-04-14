package banking.generators;

import banking.validator.LuhnAlgorithm;

import java.util.Random;

public class CardNumberGenerator {
    public static String generateCardNumber() {
        String bin = "400000";
        String uniqueAccount = generateNRandomNumbers(9);
        String checkSum = String.valueOf(LuhnAlgorithm.getCheckDigit(bin + uniqueAccount));
        return bin + uniqueAccount + checkSum;
    }

    private static String generateNRandomNumbers(int n) {
        Random rand = new Random();
        int lowerBound = (int) Math.pow(10, n - 1);
        int upperBound = (int) Math.pow(10, n) - 1;
        return String.valueOf(rand.nextInt(upperBound - lowerBound + 1) + lowerBound);
    }
}
