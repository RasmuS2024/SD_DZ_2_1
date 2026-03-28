package tiger.bankapp.facade;

import tiger.bankapp.model.Operation;
import tiger.bankapp.service.OperationService;

import java.time.LocalDateTime;
import java.util.List;

public class OperationFacade {
    private final OperationService operationService;

    public OperationFacade(OperationService operationService) {
        this.operationService = operationService;
    }

    public Operation addIncome(Long accountId, double amount, Integer categoryId, String description) {
        return operationService.createIncome(accountId, amount, categoryId, description);
    }

    public Operation addExpense(Long accountId, double amount, Integer categoryId, String description) {
        return operationService.createExpense(accountId, amount, categoryId, description);
    }

    public Operation getOperation(Integer id) {
        return operationService.getOperation(id);
    }

    public List<Operation> getAccountOperations(Long accountId) {
        return operationService.getAccountOperations(accountId);
    }

    public List<Operation> getAllOperations() {
        return operationService.getAllOperations();
    }

    public boolean updateOperation(Integer id, String description, Integer categoryId) {
        return operationService.updateOperation(id, description, categoryId);
    }

    public boolean deleteOperation(Integer id) {
        return operationService.deleteOperation(id);
    }

    public Operation importIncome(Long accountId, double amount, Integer categoryId,
                                  String description, LocalDateTime date) {
        return operationService.importIncome(accountId, amount, categoryId, description, date);
    }

    public Operation importExpense(Long accountId, double amount, Integer categoryId,
                                   String description, LocalDateTime date) {
        return operationService.importExpense(accountId, amount, categoryId, description, date);
    }
}