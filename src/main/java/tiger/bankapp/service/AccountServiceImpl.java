package tiger.bankapp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.repository.AccountRepository;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public BankAccount createAccount(String name) {
        BankAccount account = new BankAccount(null, name);
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
                    accountRepository.update(account);
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
    public boolean deposit(Long accountId, int amount) {
        return accountRepository.findById(accountId)
                .map(account -> {
                    account.deposit(amount);
                    accountRepository.update(account);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean withdraw(Long accountId, int amount) {
        return accountRepository.findById(accountId)
                .filter(account -> account.withdraw(amount))
                .map(account -> {
                    accountRepository.update(account);
                    return true;
                })
                .orElse(false);
    }
}