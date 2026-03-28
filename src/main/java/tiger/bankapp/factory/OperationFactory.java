package tiger.bankapp.factory;

import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.OperationType;

import java.time.LocalDateTime;

public interface OperationFactory {

    Operation createOperation(OperationType type, Long accountId, double amount,
                              Integer categoryId, String description);

    Operation createOperationWithAllFields(Integer id, OperationType type, Long accountId,
                                           double amount, LocalDateTime date,
                                           String description, Integer categoryId);
}