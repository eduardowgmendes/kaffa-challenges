package validator;

public class ValidatorApplication {

    public static void main(String[] args) {
        if (args.length > 0) {
            String document = args[0];

            if (document != null) {
                DocumentValidator documentValidator = new CNPJDocumentValidator(document);

                documentValidator.matchesPattern(DocumentPatterns.CNPJ_ANY);

                String anyPattern = "000.000.000/0000-00 or 000000000000000";

                isValid("Document is Valid: %s", documentValidator.isValid());
                isMatchingPattern("Document matches the pattern '%s' ? %s", anyPattern, documentValidator.matchesPattern(DocumentPatterns.CNPJ_ANY));
            }
        } else {
            System.out.printf("must provide document arg following this: %s", "java -jar document-validator-1.0-SNAPSHOT.jar \"<document>\"");
        }

    }

    private static void isValid(String message, boolean valid) {
        System.out.printf(message.concat("\n"), valid);
    }

    private static void isMatchingPattern(String message, String expectedPattern, boolean matching) {
        System.out.printf(message.concat("\n"), expectedPattern, matching);
    }
}
