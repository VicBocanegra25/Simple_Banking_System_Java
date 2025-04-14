package banking.validator;

public class LuhnAlgorithm {
    public static int getCheckDigit(String partialCardNumber) {
        int sum = 0;
        for (int i = 0; i < partialCardNumber.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(partialCardNumber.charAt(i)));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return (10 - (sum % 10)) % 10;
    }

    public static boolean isValid(String cardNumber) {
        // Check for null or empty string
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }

        // Validate that input contains only digits
        if (!cardNumber.matches("\\d+")) {
            return false;
        }

        // Validate length (example minimum length check)
        if (cardNumber.length() < 2) {
            return false;
        }

        int checkSumDigit = getCheckDigit(cardNumber.substring(0, cardNumber.length() - 1));
        int lastDigit = Character.getNumericValue(cardNumber.charAt(cardNumber.length() - 1));

        return checkSumDigit == lastDigit;
    }
}
