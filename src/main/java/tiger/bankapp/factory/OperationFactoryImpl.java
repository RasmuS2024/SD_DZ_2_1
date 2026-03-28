package tiger.bankapp.factory;

import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.OperationType;

import java.time.LocalDateTime;

public class OperationFactoryImpl implements OperationFactory {
    /**
     * Создание новой операции (ID = null, дата = сейчас)
     */
    @Override
    public Operation createOperation(OperationType type, Long accountId, double amount,
                                     Integer categoryId, String description) {
        return new Operation(null, type, accountId, amount,
                LocalDateTime.now(), description, categoryId);
    }

    /**
     * Создание операции со всеми полями (для импорта)
     */
    @Override
    public Operation createOperationWithAllFields(Integer id, OperationType type, Long accountId,
                                                  double amount, LocalDateTime date,
                                                  String description, Integer categoryId) {
        return new Operation(id, type, accountId, amount, date, description, categoryId);
    }

}
