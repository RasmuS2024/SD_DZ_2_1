package tiger.bankapp.factory;

import tiger.bankapp.model.BankAccount;

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