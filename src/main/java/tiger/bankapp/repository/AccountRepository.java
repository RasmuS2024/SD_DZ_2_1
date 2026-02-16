package tiger.bankapp.repository;

import org.springframework.stereotype.Repository;
import tiger.bankapp.model.BankAccount;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccountRepository {
    private final Map<Long, BankAccount> storage = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public BankAccount save(BankAccount account) {
        if (account.getId() == null) {
            account.setId(nextId.getAndIncrement());
        }
        storage.put(account.getId(), account);
        return account;
    }

    public Optional<BankAccount> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<BankAccount> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public void update(BankAccount account) {
        storage.put(account.getId(), account);
    }

}