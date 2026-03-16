package tiger.bankapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.factory.OperationFactory;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Operation;
import tiger.bankapp.repository.OperationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final OperationFactory operationFactory;

    @Override
    public Operation createIncome(Long accountId, double amount, Integer categoryId, String description) {
        validateAmount(amount);

        Operation op = operationFactory.createOperation("INCOME", accountId, amount, categoryId, description);

        boolean success = accountService.deposit(accountId, amount);

        if (success) {
            return operationRepository.save(op);
        } else {
            throw new BankingException("Не удалось обновить баланс. Операция отменена.");
        }
    }

    @Override
    public Operation createExpense(Long accountId, double amount, Integer categoryId, String description) {
        validateAmount(amount);

        if (!accountService.withdraw(accountId, amount)) {
            throw new BankingException("Недостаточно средств на счете " + accountId);
        }

        Operation op = operationFactory.createOperation("EXPENSE", accountId, amount, categoryId, description);
        return operationRepository.save(op);
    }

    @Override
    public Operation getOperation(Integer id) {
        return operationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Операция с ID " + id + " не найдена"));
    }

    @Override
    public List<Operation> getAccountOperations(Long accountId) {
        validateId(accountId);
        return operationRepository.findByAccountId(accountId);
    }

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public boolean updateOperation(Integer id, String description, Integer categoryId) {
        return operationRepository.findById(id)
                .map(op -> {
                    if (description != null) op.setDescription(description);
                    if (categoryId != null) op.setCategoryId(categoryId);
                    operationRepository.save(op);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean deleteOperation(Integer id) {
        return operationRepository.findById(id)
                .map(op -> {
                    if ("INCOME".equals(op.getType())) {
                        accountService.withdraw(op.getBankAccountId(), op.getAmount());
                    } else {
                        accountService.deposit(op.getBankAccountId(), op.getAmount());
                    }
                    return operationRepository.deleteById(id);
                }).orElse(false);
    }

    @Override
    public List<Operation> getOperationsInPeriod(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new IllegalArgumentException("Некорректный период дат");
        }
        return operationRepository.findByDateRange(from, to);
    }

    @Override
    public List<Operation> getOperationsByAccountAndPeriod(Long accountId, LocalDateTime from, LocalDateTime to) {
        return List.of();
    }

    @Override
    public List<Operation> getOperationsByType(String type) {
        return List.of();
    }

    @Override
    public long getOperationsCount() {
        return 0;
    }

    public Operation importIncome(Long accountId, double amount, Integer categoryId, String description) {
        BankAccount account = accountService.getAccount(accountId);
        if (account == null) return null;

        Operation operation = operationFactory.createIncome(accountId, amount, categoryId, description);
        return operationRepository.save(operation);
    }

    public Operation importExpense(Long accountId, double amount, Integer categoryId, String description) {
        BankAccount account = accountService.getAccount(accountId);
        if (account == null) return null;

        Operation operation = operationFactory.createExpense(accountId, amount, categoryId, description);
        return operationRepository.save(operation);
    }

    /**
     * Валидация суммы
     * @param amount положительное число
     */
    private void validateAmount(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть > 0");
    }

    /**
     * Валидация ID
     * @param id положительное целое число
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Неверный ID");
    }
}
