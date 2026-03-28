package tiger.bankapp.repository;

import tiger.bankapp.model.Operation;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OperationRepositoryImpl implements OperationRepository {
    private final Map<Integer, Operation> storage = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    /**
     * Универсальный метод сохранения.
     * Если ID нет - создает новый. Если есть - обновляет.
     */
    @Override
    public Operation save(Operation operation) {
        if (operation.getId() == null) {
            operation.setId(nextId.getAndIncrement());
        } else {
            updateNextId(operation.getId());
        }
        storage.put(operation.getId(), operation);
        return operation;
    }

    private void updateNextId(int currentId) {
        nextId.getAndUpdate(prev -> Math.max(prev, currentId + 1));
    }

    @Override
    public Optional<Operation> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Operation> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Operation> findByAccountId(Long accountId) {
        return storage.values().stream()
                .filter(op -> op.getBankAccountId().equals(accountId))
                .toList();
    }

    @Override
    public boolean deleteById(Integer id) {
        return storage.remove(id) != null;
    }

    @Override
    public void saveAll(List<Operation> operations) {
        operations.forEach(this::save);
    }

    @Override
    public List<Operation> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return storage.values().stream()
                .filter(op -> !op.getDate().isBefore(from) && !op.getDate().isAfter(to))
                .toList();
    }

    @Override
    public void clear() {
        storage.clear();
        nextId.set(1);
    }
}
