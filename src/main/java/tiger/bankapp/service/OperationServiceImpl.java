package tiger.bankapp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.Operation;
import tiger.bankapp.repository.OperationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository,
                                AccountService accountService) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
    }

    public Operation createIncome(Long accountId, int amount, Integer categoryId, String description) {
        if (!accountService.deposit(accountId, amount)) {
            return null;
        }

        Operation operation = new Operation(null, "INCOME", accountId, amount, categoryId, description);
        return operationRepository.save(operation);
    }

    public Operation createExpense(Long accountId, int amount, Integer categoryId, String description) {
        if (!accountService.withdraw(accountId, amount)) {
            return null;
        }

        Operation operation = new Operation(null, "EXPENSE", accountId, amount, categoryId, description);
        return operationRepository.save(operation);
    }

    public Operation getOperation(Integer id) {
        return operationRepository.findById(id).orElse(null);
    }

    public List<Operation> getAccountOperations(Long accountId) {
        return operationRepository.findByAccountId(accountId);
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public boolean updateOperation(Integer id, String description, Integer categoryId) {
        return operationRepository.findById(id)
                .map(operation -> {
                    operation.setDescription(description);
                    operation.setCategoryId(categoryId);
                    operationRepository.update(operation);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteOperation(Integer id) {
        return operationRepository.findById(id)
                .map(operation -> {
                    if ("INCOME".equals(operation.getType())) {
                        accountService.withdraw(operation.getBankAccountId(), operation.getAmount());
                    } else if ("EXPENSE".equals(operation.getType())) {
                        accountService.deposit(operation.getBankAccountId(), operation.getAmount());
                    }
                    return operationRepository.deleteById(id);
                })
                .orElse(false);
    }

    public List<Operation> getOperationsInPeriod(LocalDateTime from, LocalDateTime to) {
        return operationRepository.findByDateRange(from, to);
    }
}