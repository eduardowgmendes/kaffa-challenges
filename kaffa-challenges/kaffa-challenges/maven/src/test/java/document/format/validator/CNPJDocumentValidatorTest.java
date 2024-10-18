package document.format.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CNPJDocumentValidatorTest {

    @Test
    public void lookalikeAValidCNPJ() {
        Assertions.assertTrue(new CNPJDocumentValidator("08.730.563/0001-47").matchesPattern(DocumentPatterns.CNPJ_ANY));
    }

    @Test
    public void notLookalikeAValidCNPJ() {
        Assertions.assertFalse(new CNPJDocumentValidator("123.456.789-10").matchesPattern(DocumentPatterns.CNPJ_ANY));
    }

    @Test
    public void notLookalikeAValidCNPJDueToHisNullability() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CNPJDocumentValidator(null).matchesPattern(DocumentPatterns.CNPJ_ANY));
    }

    @Test
    public void isAWellFormedCNPJ() {
        Assertions.assertTrue(new CNPJDocumentValidator("08.730.563/0001-47").isValid());
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisNullability() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CNPJDocumentValidator(null).isValid());
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisEmptiness() {
        Assertions.assertFalse(new CNPJDocumentValidator("").isValid());
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisDigitsAreAllTheSame() {
        Assertions.assertFalse(new CNPJDocumentValidator("00.000.000/0000-00").isValid());
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisLength() {
        Assertions.assertFalse(new CNPJDocumentValidator("0000000000000").isValid());
    }

}
