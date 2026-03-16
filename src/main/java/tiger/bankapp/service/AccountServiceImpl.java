package tiger.bankapp.service;

import org.springframework.stereotype.Service;
import tiger.bankapp.factory.AccountFactory;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.repository.AccountRepository;

import java.util.List;

@Service
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
        return accountRepository.findById(id)
                .filter(acc -> acc.getBalance() == 0)
                .map(acc -> accountRepository.deleteById(id))
                .orElse(false);
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