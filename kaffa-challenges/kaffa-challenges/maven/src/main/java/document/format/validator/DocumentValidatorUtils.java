package document.format.validator;

public final class DocumentValidatorUtils {

    /**
     * Checks the validity of a document through three special conditions:
     * <p>
     * - The document is blank
     * - The document has 14 digits
     * - The document contains all identical digits
     *
     * @param document
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if the document is null
     */
    public static boolean isWellFormed(String document) {

        if (document == null) throw new IllegalArgumentException("document cannot be null!");

        document = document.replaceAll("\\D", "");

        if (document.isEmpty()) return false;

        if (document.length() != 14) return false;

        if (document.matches("(\\d)\\1{13}")) return false;

        String baseDocument = document.substring(0, 12);

        int firstCheckDigit = DigitWeights.Evaluator.evaluateCheckDigits(baseDocument, DigitWeights.FIRST_DIGIT_WEIGHTS);
        int secondCheckDigit = DigitWeights.Evaluator.evaluateCheckDigits(baseDocument + firstCheckDigit, DigitWeights.SECOND_DIGIT_WEIGHTS);

        return document.equals(baseDocument + firstCheckDigit + secondCheckDigit);
    }

    /**
     * Checks if the document is a CNPJ
     * by matching the @code DocumentPatterns.CNPJ_ANY
     * pattern that's can be any of these format:
     * <p>
     * - 00.000.000/0000-00
     * - 00000000000000
     *
     * @param document
     * @return true if it is, false otherwise
     * @throws IllegalArgumentException if the document is null
     */
    public static boolean isCNPJ(String document) {
        if (document == null) throw new IllegalArgumentException("document cannot be null!");
        return document.matches(DocumentPatterns.CNPJ_ANY);
    }

}
