package tiger.bankapp.factory;

import org.springframework.stereotype.Component;
import tiger.bankapp.model.Operation;

import java.time.LocalDateTime;

@Component
public class OperationFactory {
    /**
     * Создание новой операции (ID = null, дата = сейчас)
     */
    public Operation createOperation(String type, Long accountId, double  amount,
                                     Integer categoryId, String description) {
        validateType(type);
        return new Operation(null, type, accountId, amount,
                LocalDateTime.now(), description, categoryId);
    }

    /**
     * Создание операции со всеми полями (для импорта)
     */
    public Operation createOperationWithAllFields(Integer id, String type, Long accountId,
                                                  double amount, LocalDateTime date,
                                                  String description, Integer categoryId) {
        return new Operation(id, type, accountId, amount, date, description, categoryId);
    }

    /**
     * Создание операции дохода (ID = null, дата = сейчас)
     */
    public Operation createIncome(Long accountId, double amount,
                                  Integer categoryId, String description) {
        return new Operation(null, "INCOME", accountId, amount,
                LocalDateTime.now(), description, categoryId);
    }

    /**
     * Создание операции расхода (ID = null, дата = сейчас)
     */
    public Operation createExpense(Long accountId, double amount,
                                   Integer categoryId, String description) {
        return new Operation(null, "EXPENSE", accountId, amount,
                LocalDateTime.now(), description, categoryId);
    }

    /**
     * Валидация типа операции
     * @param type может быть INCOME (доход) или EXPENSE (расход)
     */
    private void validateType(String type) {
        if (!"INCOME".equals(type) && !"EXPENSE".equals(type)) {
            throw new IllegalArgumentException("Некорректный тип операции: " + type);
        }
    }
}
