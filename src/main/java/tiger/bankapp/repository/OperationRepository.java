package tiger.bankapp.repository;

import tiger.bankapp.model.Operation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OperationRepository {
    Operation save(Operation operation);
    Optional<Operation> findById(Integer id);
    List<Operation> findAll();
    List<Operation> findByAccountId(Long accountId);
    boolean deleteById(Integer id);
    void saveAll(List<Operation> operations);
    List<Operation> findByDateRange(LocalDateTime from, LocalDateTime to);
    void clear();
}
