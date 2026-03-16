package tiger.bankapp.service;

import tiger.bankapp.model.Operation;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationService {
    Operation createIncome(Long accountId, double amount, Integer categoryId, String description);

    Operation createExpense(Long accountId, double amount, Integer categoryId, String description);

    Operation getOperation(Integer id);

    List<Operation> getAccountOperations(Long accountId);

    List<Operation> getAllOperations();

    boolean updateOperation(Integer id, String description, Integer categoryId);

    boolean deleteOperation(Integer id);

    List<Operation> getOperationsInPeriod(LocalDateTime from, LocalDateTime to);

    List<Operation> getOperationsByAccountAndPeriod(Long accountId, LocalDateTime from, LocalDateTime to);

    List<Operation> getOperationsByType(String type);

    long getOperationsCount();

    public Operation importIncome(Long accountId, double amount, Integer categoryId, String description);

    public Operation importExpense(Long accountId, double amount, Integer categoryId, String description);


}