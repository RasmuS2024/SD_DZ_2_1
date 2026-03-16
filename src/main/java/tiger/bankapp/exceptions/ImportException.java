package tiger.bankapp.exceptions;

public class ImportException extends BankingException {

    public ImportException(String message) {
        super(message);
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }

}