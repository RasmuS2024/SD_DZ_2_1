package tiger.bankapp.repository;

import tiger.bankapp.model.BankAccount;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    BankAccount save(BankAccount account);
    Optional<BankAccount> findById(Long id);
    List<BankAccount> findAll();
    boolean deleteById(Long id);
}
