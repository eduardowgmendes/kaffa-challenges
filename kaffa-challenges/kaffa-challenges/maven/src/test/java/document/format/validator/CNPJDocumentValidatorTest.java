package document.format.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CNPJDocumentValidatorTest {

    @Test
    public void shouldReturnTrue_whenCNPJMatchesPattern() {
        Assertions.assertTrue(new CNPJDocumentValidator("08.730.563/0001-47").matchesPattern(DocumentPatterns.CNPJ_ANY));
    }

    @Test
    public void shouldReturnFalse_whenCNPJDoesNotMatchPattern() {
        Assertions.assertFalse(new CNPJDocumentValidator("123.456.789-10").matchesPattern(DocumentPatterns.CNPJ_ANY));
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenCNPJIsNullInPatternValidation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CNPJDocumentValidator(null).matchesPattern(DocumentPatterns.CNPJ_ANY));
    }

    @Test
    public void shouldReturnTrue_whenCNPJIsWellFormed() {
        Assertions.assertTrue(new CNPJDocumentValidator("08.730.563/0001-47").isValid());
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenCNPJIsNullInValidation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CNPJDocumentValidator(null).isValid());
    }

    @Test
    public void shouldReturnFalse_whenCNPJIsEmpty() {
        Assertions.assertFalse(new CNPJDocumentValidator("").isValid());
    }

    @Test
    public void shouldReturnFalse_whenCNPJHasAllSameDigits() {
        Assertions.assertFalse(new CNPJDocumentValidator("00.000.000/0000-00").isValid());
    }

    @Test
    public void shouldReturnFalse_whenCNPJLengthIsIncorrect() {
        Assertions.assertFalse(new CNPJDocumentValidator("0000000000000").isValid());
    }

}
