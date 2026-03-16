package tiger.bankapp.facade;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.service.AccountService;

import java.util.List;

@Component
public class AccountFacade {
    private final AccountService accountService;

    @Autowired
    public AccountFacade(AccountService accountService) {
        this.accountService = accountService;
    }

    public BankAccount createAccount(String name) {
        return accountService.createAccount(name);
    }

    public BankAccount createAccountWithBalance(String name, double balance) {
        return accountService.createAccountWithBalance(name, balance);
    }

    public BankAccount getAccount(Long id) {
        return accountService.getAccount(id);
    }

    public List<BankAccount> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public boolean updateAccount(Long id, String newName) {
        return accountService.updateAccount(id, newName);
    }

    public boolean deleteAccount(Long id) {
        return accountService.deleteAccount(id);
    }

    public boolean deposit(Long accountId, double amount) {
        return accountService.deposit(accountId, amount);
    }

    public boolean withdraw(Long accountId, double amount) {
        return accountService.withdraw(accountId, amount);
    }
}
