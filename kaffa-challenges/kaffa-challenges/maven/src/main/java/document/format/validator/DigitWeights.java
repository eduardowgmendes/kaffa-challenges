package document.format.validator;

public class DigitWeights {

    public static final int[] FIRST_DIGIT_WEIGHTS = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    public static final int[] SECOND_DIGIT_WEIGHTS = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static class Evaluator {

        public static int evaluateCheckDigits(String document, int[] digitWeights) {
            int sum = 0;

            for (int i = 0; i < document.length(); i++)
                sum += Character.getNumericValue(document.charAt(i)) * digitWeights[i];

            int remainder = sum % 11;

            return (remainder < 2) ? 0 : 11 - remainder; // TODO - To check this conditional to test code coverage
        }

    }
}
