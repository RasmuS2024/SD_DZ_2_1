package tiger.bankapp.service;

import tiger.bankapp.model.BankAccount;

import java.util.List;

public interface AccountService {
    BankAccount createAccount(String name);
    BankAccount getAccount(Long id);
    List<BankAccount> getAllAccounts();
    boolean updateAccount(Long id, String newName);
    boolean deleteAccount(Long id);
    boolean deposit(Long accountId, int amount);
    boolean withdraw(Long accountId, int amount);
}