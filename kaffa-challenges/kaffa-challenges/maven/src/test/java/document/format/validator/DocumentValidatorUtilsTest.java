package document.format.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DocumentValidatorUtilsTest {

    private final String documentPattern = DocumentPatterns.CNPJ_ANY;

    @Test
    public void lookalikeAValidCNPJ() {
        Assertions.assertTrue(DocumentValidatorUtils.isCNPJ("08.730.563/0001-47", documentPattern));
    }

    @Test
    public void notLookalikeAValidCNPJ() {
        Assertions.assertFalse(DocumentValidatorUtils.isCNPJ("123.456.789-10", documentPattern));
    }

    @Test
    public void notLookalikeAValidCNPJDueToHisNullability() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DocumentValidatorUtils.isCNPJ(null, documentPattern));
    }

    @Test
    public void isAWellFormedCNPJ() {
        Assertions.assertTrue(DocumentValidatorUtils.isValid("08.730.563/0001-47"));
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisNullability() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DocumentValidatorUtils.isValid(null));
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisEmptiness() {
        Assertions.assertFalse(DocumentValidatorUtils.isValid(""));
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisDigitsAreAllTheSame() {
        Assertions.assertFalse(DocumentValidatorUtils.isValid("00.000.000/0000-00"));
    }

    @Test
    public void isNotAWellFormedCNPJDueToHisLength() {
        Assertions.assertFalse(DocumentValidatorUtils.isValid("0000000000000"));
    }

}
