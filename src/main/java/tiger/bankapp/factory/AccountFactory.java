package tiger.bankapp.factory;

import tiger.bankapp.model.BankAccount;

public interface AccountFactory {

    BankAccount createAccount(String name);

    BankAccount createAccountWithId(long id, String name);

    BankAccount createAccountWithBalance(String name, double initialBalance);
}