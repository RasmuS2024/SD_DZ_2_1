package tiger.bankapp.exceptions;

public class AccountNotEmptyException extends BankingException {
    public AccountNotEmptyException(String message) {
        super(message);
    }
}
