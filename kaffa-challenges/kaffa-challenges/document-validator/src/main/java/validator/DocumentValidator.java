package validator;

public interface DocumentValidator {

    boolean isValid();

    boolean matchesPattern(String pattern);

}
