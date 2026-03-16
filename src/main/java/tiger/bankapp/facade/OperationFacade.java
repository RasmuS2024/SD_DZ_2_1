package tiger.bankapp.facade;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.Operation;
import tiger.bankapp.service.OperationService;

import java.util.List;

@Component
public class OperationFacade {
    private final OperationService operationService;

    @Autowired
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

    public Operation importIncome(Long accountId, double amount, Integer categoryId, String description) {
        return operationService.importIncome(accountId, amount, categoryId, description);
    }

    public Operation importExpense(Long accountId, double amount, Integer categoryId, String description) {
        return operationService.importExpense(accountId, amount, categoryId, description);
    }
}