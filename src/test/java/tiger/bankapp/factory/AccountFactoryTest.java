package tiger.bankapp.factory;

import org.junit.jupiter.api.Test;
import tiger.bankapp.model.BankAccount;

import static org.junit.jupiter.api.Assertions.*;

class AccountFactoryTest {

    private final AccountFactory factory = new AccountFactoryImpl();

    /**
     * Проверяет создание счета с дефолтным ID и нулевым балансом.
     */
    @Test
    void testCreateAccount() {
        BankAccount account = factory.createAccount("Зарплатный");

        assertEquals(0L, account.getId());
        assertEquals("Зарплатный", account.getName());
        assertEquals(0.0, account.getBalance());
    }

    /**
     * Проверяет создание счета с конкретным идентификатором (используется при импорте).
     */
    @Test
    void testCreateAccountWithId() {
        BankAccount account = factory.createAccountWithId(100L, "Сберегательный");

        assertEquals(100L, account.getId());
        assertEquals("Сберегательный", account.getName());
    }

    /**
     * Проверяет создание счета с начальным положительным балансом.
     */
    @Test
    void testCreateAccountWithBalance() {
        BankAccount account = factory.createAccountWithBalance("Депозит", 500.50);

        assertEquals(500.50, account.getBalance());
        assertEquals("Депозит", account.getName());
    }
}
