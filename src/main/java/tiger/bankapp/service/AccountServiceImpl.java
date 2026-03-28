package tiger.bankapp.service;

import tiger.bankapp.exceptions.AccountNotEmptyException;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.factory.AccountFactory;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.repository.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;

    public AccountServiceImpl(AccountRepository accountRepository, AccountFactory accountFactory) {
        this.accountRepository = accountRepository;
        this.accountFactory = accountFactory;
    }

    @Override
    public BankAccount createAccount(String name) {
        BankAccount account = accountFactory.createAccount(name);
        return accountRepository.save(account);
    }

    @Override
    public BankAccount createAccountWithBalance(String name, double balance) {
        BankAccount account = accountFactory.createAccountWithBalance(name, balance);
        return accountRepository.save(account);
    }

    @Override
    public BankAccount getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public boolean updateAccount(Long id, String newName) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setName(newName);
                    accountRepository.save(account);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteAccount(Long id) {
        BankAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Счет с ID " + id + " не найден"));

        if (account.getBalance() != 0) {
            throw new AccountNotEmptyException("Нельзя удалить счет с ненулевым балансом: " + account.getBalance());
        }

        return accountRepository.deleteById(id);
    }

    @Override
    public boolean deposit(Long accountId, double amount) {
        return accountRepository.findById(accountId)
                .map(account -> {
                    account.deposit(amount);
                    accountRepository.save(account);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean withdraw(Long accountId, double amount) {
        return accountRepository.findById(accountId)
                .filter(account -> account.withdraw(amount))
                .map(account -> {
                    accountRepository.save(account);
                    return true;
                })
                .orElse(false);
    }
}