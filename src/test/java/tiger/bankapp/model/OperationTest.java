package tiger.bankapp.model;

import org.junit.jupiter.api.Test;
import tiger.bankapp.model.enums.OperationType;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static tiger.bankapp.config.ImportExportConfig.NUMBER_LOCALE;

class OperationTest {

    /**
     * Проверяет работу конструктора со всеми аргументами и корректность возвращаемых значений.
     */
    @Test
    void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Operation op = new Operation(1, OperationType.INCOME, 1L, 1000.0, now, "Зарплата", 1);

        assertAll("Проверка полей операции",
                () -> assertEquals(1, op.getId()),
                () -> assertEquals(OperationType.INCOME, op.getType()),
                () -> assertEquals(1L, op.getBankAccountId()),
                () -> assertEquals(1000.0, op.getAmount()),
                () -> assertEquals(now, op.getDate()),
                () -> assertEquals("Зарплата", op.getDescription()),
                () -> assertEquals(1, op.getCategoryId())
        );
    }

    /**
     * Проверяет правильность форматирования даты операции в строку.
     */
    @Test
    void testGetFormattedDate() {
        LocalDateTime date = LocalDateTime.of(2024, 3, 15, 14, 30);
        Operation op = new Operation(1, OperationType.INCOME, 1L, 1000.0, date, "Тест", 1);

        assertEquals("15.03.2024 14:30", op.getFormattedDate());
    }

    /**
     * Проверяет строковое представление объекта операции (метод toString).
     */
    @Test
    void testToString() {
        LocalDateTime date = LocalDateTime.of(2024, 3, 15, 14, 30);
        Operation op = new Operation(1, OperationType.INCOME, 1L, 1000.0, date, "Зарплата", 1);

        String expected = "Операция{id=1, тип=Доход, счет=1, сумма=1000.00, дата=15.03.2024 14:30}";

        assertEquals(expected, op.toString());
    }

    /**
     * Проверяет создание пустого объекта через конструктор без аргументов (необходимо для десериализации).
     */
    @Test
    void testNoArgsConstructor() {
        Operation op = new Operation();
        assertAll("Проверка пустого конструктора",
                () -> assertNull(op.getId()),
                () -> assertNull(op.getType()),
                () -> assertNull(op.getBankAccountId()),
                () -> assertEquals(0.0, op.getAmount()),
                () -> assertNull(op.getDate()),
                () -> assertNull(op.getDescription()),
                () -> assertNull(op.getCategoryId())
        );
    }
}
