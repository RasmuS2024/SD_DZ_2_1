package tiger.bankapp.factory;

import org.springframework.stereotype.Component;
import tiger.bankapp.model.BankAccount;

@Component
public class AccountFactory {

    public BankAccount createAccount(String name) {
        return new BankAccount(0L, name, 0.0);
    }

    public BankAccount createAccountWithId(long id, String name) {
        return new BankAccount(id, name, 0.0);
    }

    public BankAccount createAccountWithBalance(String name, double initialBalance) {
        return new BankAccount(0L, name, initialBalance);
    }
}