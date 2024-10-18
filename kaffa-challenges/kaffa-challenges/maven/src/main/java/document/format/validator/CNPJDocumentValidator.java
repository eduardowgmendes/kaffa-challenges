package document.format.validator;

public final class CNPJDocumentValidator implements DocumentValidator {

    private String document;

    public CNPJDocumentValidator(String document) {
        this.document = document;
    }

    /**
     * Checks the validity of a document through three special conditions:
     * <p>
     * - The document is blank
     * - The document has fourteen digits
     * - The document contains all identical digits
     *
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if the document is null
     */
    @Override
    public boolean isValid() {

        if (document == null) throw new IllegalArgumentException("document cannot be null!");

        document = document.replaceAll("\\D", "");

        if (document.isEmpty()) return false;

        if (hasAllDigits()) return false;

        if (allDigitsAreSame()) return false;

        String baseDocument = document.substring(0, 12);

        int firstCheckDigit = DigitWeightsUtils.evaluateCheckDigits(baseDocument, DigitWeightsUtils.FIRST_DIGIT_WEIGHTS);
        int secondCheckDigit = DigitWeightsUtils.evaluateCheckDigits(baseDocument + firstCheckDigit, DigitWeightsUtils.SECOND_DIGIT_WEIGHTS);

        return document.equals(baseDocument + firstCheckDigit + secondCheckDigit);
    }

    /**
     * Checks if the document has the correct length of 14 digits.
     *
     * @return true if the document does not have exactly 14 characters, false otherwise
     */
    private boolean hasAllDigits() {
        return document.length() != 14;
    }

    /**
     * Checks if all digits in the document are the same.
     *
     * @return true if all digits in the document are identical, false otherwise
     */
    private boolean allDigitsAreSame() {
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
     * @return true if it is, false otherwise
     * @throws IllegalArgumentException if the document is null
     */
    @Override
    public boolean matchesPattern(String pattern) {
        if (document == null) throw new IllegalArgumentException("document cannot be null!");
        return document.matches(pattern);
    }

}
