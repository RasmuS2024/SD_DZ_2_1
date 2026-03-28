package tiger.bankapp.factory;

import tiger.bankapp.model.BankAccount;

public class AccountFactoryImpl implements AccountFactory {

    @Override
    public BankAccount createAccount(String name) {
        return new BankAccount(0L, name, 0.0);
    }

    @Override
    public BankAccount createAccountWithId(long id, String name) {
        return new BankAccount(id, name, 0.0);
    }

    @Override
    public BankAccount createAccountWithBalance(String name, double initialBalance) {
        return new BankAccount(0L, name, initialBalance);
    }

}