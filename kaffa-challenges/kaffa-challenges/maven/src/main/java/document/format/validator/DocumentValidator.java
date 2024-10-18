package document.format.validator;

public interface DocumentValidator {

    boolean isValid();

    boolean matchesPattern(String pattern);

}
