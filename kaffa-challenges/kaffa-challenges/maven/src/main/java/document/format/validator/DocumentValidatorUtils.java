package document.format.validator;

public final class DocumentValidatorUtils {

    /**
     * Checks the validity of a document through three special conditions:
     * <p>
     * - The document is blank
     * - The document has fourteen digits
     * - The document contains all identical digits
     *
     * @param document
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if the document is null
     */
    public static boolean isValid(String document) {

        if (document == null) throw new IllegalArgumentException("document cannot be null!");

        document = document.replaceAll("\\D", "");

        if (document.isEmpty()) return false;

        if (hasAllDigits(document)) return false;

        if (allDigitsAreSame(document)) return false;

        String baseDocument = document.substring(0, 12);

        int firstCheckDigit = DigitWeights.evaluateCheckDigits(baseDocument, DigitWeights.FIRST_DIGIT_WEIGHTS);
        int secondCheckDigit = DigitWeights.evaluateCheckDigits(baseDocument + firstCheckDigit, DigitWeights.SECOND_DIGIT_WEIGHTS);

        return document.equals(baseDocument + firstCheckDigit + secondCheckDigit);
    }

    /**
     * Checks if the document has the correct length of 14 digits.
     *
     * @param document the document to be validated
     * @return true if the document does not have exactly 14 characters, false otherwise
     */
    private static boolean hasAllDigits(String document) {
        return document.length() != 14;
    }

    /**
     * Checks if all digits in the document are the same.
     *
     * @param document the document to be validated
     * @return true if all digits in the document are identical, false otherwise
     */
    private static boolean allDigitsAreSame(String document) {
        return document.matches("(\\d)\\1{13}");
    }

    /**
     * Checks if the document is a CNPJ
     * by matching the DocumentPatterns.CNPJ_ANY
     * pattern that's can be any of these format:
     * <p>
     * - 00.000.000/0000-00
     * - 00000000000000
     * <p>
     * Other two formats are more exclusive:
     * <p>
     * DocumentPatterns.CNPJ_ONLY_NUMBERS
     * Accepts only - 00000000000000
     * <p>
     * DocumentPatterns.CNPJ_NUMBERS_AND_SYMBOLS
     * Accepts only - 00.000.000/0000-00
     *
     * @param document
     * @return true if it is, false otherwise
     * @throws IllegalArgumentException if the document is null
     */
    public static boolean isCNPJ(String document, String documentPattern) {
        if (document == null) throw new IllegalArgumentException("document cannot be null!");
        return document.matches(documentPattern);
    }

}
