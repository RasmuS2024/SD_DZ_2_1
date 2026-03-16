package tiger.bankapp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.Operation;
import tiger.bankapp.factory.OperationFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class OperationRepository {
    private final Map<Integer, Operation> storage = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    // Фабрика здесь больше не нужна, так как объекты приходят готовыми из Service или Importer

    /**
     * Универсальный метод сохранения.
     * Если ID нет - создает новый. Если есть - обновляет.
     */
    public Operation save(Operation operation) {
        if (operation.getId() == null) {
            operation.setId(nextId.getAndIncrement());
        } else {
            // Если ID пришел извне (например, при импорте), подтягиваем счетчик
            updateNextId(operation.getId());
        }
        storage.put(operation.getId(), operation);
        return operation;
    }

    private void updateNextId(int currentId) {
        nextId.getAndUpdate(prev -> Math.max(prev, currentId + 1));
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

    public boolean deleteById(Integer id) {
        return storage.remove(id) != null;
    }

    public void saveAll(List<Operation> operations) {
        operations.forEach(this::save);
    }

    public List<Operation> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return storage.values().stream()
                .filter(op -> !op.getDate().isBefore(from) && !op.getDate().isAfter(to))
                .toList();
    }

    public void clear() {
        storage.clear();
        nextId.set(1);
    }
}
