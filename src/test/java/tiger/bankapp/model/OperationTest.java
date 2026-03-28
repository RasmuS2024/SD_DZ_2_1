package tiger.bankapp.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Operation op = new Operation(1, "INCOME", 1L, 1000.0, now, "Зарплата", 1);

        assertAll("Проверка конструктора",
                () -> assertEquals(1, op.getId()),
                () -> assertEquals("INCOME", op.getType()),
                () -> assertEquals(1L, op.getBankAccountId()),
                () -> assertEquals(1000.0, op.getAmount()),
                () -> assertEquals(now, op.getDate()),
                () -> assertEquals("Зарплата", op.getDescription()),
                () -> assertEquals(1, op.getCategoryId())
        );
    }

    @Test
    void testGetFormattedDate() {
        LocalDateTime date = LocalDateTime.of(2024, 3, 15, 14, 30);
        Operation op = new Operation(1, "INCOME", 1L, 1000.0, date, "Тест", 1);

        assertEquals("15.03.2024 14:30", op.getFormattedDate());
    }

    @Test
    void testToString() {
        LocalDateTime date = LocalDateTime.of(2024, 3, 15, 14, 30);
        Operation op = new Operation(1, "INCOME", 1L, 1000.0, date, "Зарплата", 1);

        String expected = "Операция{id=1, type=INCOME, счет=1, сумма=1000,000000, дата=15.03.2024 14:30}";
        assertEquals(expected, op.toString());
    }

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