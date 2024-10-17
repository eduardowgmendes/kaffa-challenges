package document.format.validator;

public class DocumentPatterns {
    public static final String CNPJ_ONLY_NUMBERS = "^(\\d{14})$";
    public static final String CNPJ_WITH_SYMBOLS = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$";
    public static final String CNPJ_ANY = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14})$";
}
