package document.format.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DocumentValidatorUtilsTest {

    @Test
    public void lookalikeAValidCNPJ() {
        Assertions.assertTrue(DocumentValidatorUtils.isCNPJ("08.730.563/0001-47"));
    }

    @Test
    public void notLookalikeAValidCNPJ() {
        Assertions.assertFalse(DocumentValidatorUtils.isCNPJ("123.456.789-10"));
    }

    @Test
    public void notLookalikeAValidCNPJDueToHisNullability() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DocumentValidatorUtils.isCNPJ(null));
    }

    @Test
    public void isAWellFormedDocument() {
        Assertions.assertTrue(DocumentValidatorUtils.isWellFormed("08.730.563/0001-47"));
    }

    @Test
    public void isNotAWellFormedDocumentDueToHisNullability() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DocumentValidatorUtils.isWellFormed(null));
    }

    @Test
    public void isNotAWellFormedDocumentDueToDocumentBlankness() {
        Assertions.assertFalse(DocumentValidatorUtils.isWellFormed(""));
    }

    @Test
    public void isNotAWellFormedDocumentDueToDocumentDigitsAreAllTheSame() {
        Assertions.assertFalse(DocumentValidatorUtils.isWellFormed("00.000.000/0000-00"));
    }

    @Test
    public void isNotAWellFormedDocumentDueToDocumentLength() {
        Assertions.assertFalse(DocumentValidatorUtils.isWellFormed("0000000000000"));
    }

}
