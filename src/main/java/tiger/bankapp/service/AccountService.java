package tiger.bankapp.service;

import tiger.bankapp.model.BankAccount;

import java.util.List;

public interface AccountService {
    BankAccount createAccount(String name);
    BankAccount createAccountWithBalance(String name, double balance);
    BankAccount getAccount(Long id);
    List<BankAccount> getAllAccounts();
    boolean updateAccount(Long id, String newName);
    boolean deleteAccount(Long id);
    boolean deposit(Long accountId, double amount);
    boolean withdraw(Long accountId, double amount);
}