package tiger.bankapp.factory;

import org.junit.jupiter.api.Test;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.OperationType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OperationFactoryTest {

    private final OperationFactory factory = new OperationFactoryImpl();

    /**
     * Проверяет создание новой операции с автоматической установкой текущей даты и пустым ID
     */
    @Test
    void testCreateOperation() {
        Operation op = factory.createOperation(OperationType.INCOME, 1L, 100.0, 5, "Тест");

        assertNull(op.getId());
        assertEquals(OperationType.INCOME, op.getType());
        assertNotNull(op.getDate());
        assertEquals("Тест", op.getDescription());
    }

    /**
     * Проверяет создание операции со всеми заданными полями
     */
    @Test
    void testCreateOperationWithAllFields() {
        LocalDateTime customDate = LocalDateTime.of(2023, 10, 10, 10, 10);
        Operation op = factory.createOperationWithAllFields(
                50, OperationType.EXPENSE, 2L, 250.0, customDate, "Импорт", 10
        );

        assertEquals(50, op.getId());
        assertEquals(OperationType.EXPENSE, op.getType());
        assertEquals(customDate, op.getDate());
        assertEquals(10, op.getCategoryId());
    }
}
