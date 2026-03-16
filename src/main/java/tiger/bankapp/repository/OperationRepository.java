package tiger.bankapp.repository;

import org.springframework.stereotype.Repository;
import tiger.bankapp.model.Operation;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class OperationRepository {
    private final Map<Integer, Operation> storage = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public Operation save(Operation operation) {
        if (operation.getId() == null) {
            operation.setId(nextId.getAndIncrement());
        }
        storage.put(operation.getId(), operation);
        return operation;
    }

    public Optional<Operation> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Operation> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Operation> findByAccountId(Long accountId) {
        return storage.values().stream()
                .filter(op -> op.getBankAccountId().equals(accountId))
                .toList();
    }

    public List<Operation> findByCategoryId(Integer categoryId) {
        return storage.values().stream()
                .filter(op -> categoryId.equals(op.getCategoryId()))
                .toList();
    }

    public boolean deleteById(Integer id) {
        return storage.remove(id) != null;
    }

    public void update(Operation operation) {
        storage.put(operation.getId(), operation);
    }

    public List<Operation> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return storage.values().stream()
                .filter(op -> !op.getDate().isBefore(from) && !op.getDate().isAfter(to))
                .collect(Collectors.toList());
    }

}