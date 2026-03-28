package tiger.bankapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void testCreateAccount() {
        BankAccount account = new BankAccount(1L, "Тестовый счет", 0.0);

        assertEquals(1L, account.getId());
        assertEquals("Тестовый счет", account.getName());
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void testDeposit() {
        BankAccount account = new BankAccount(1L, "Тестовый счет", 0.0);

        account.deposit(1000);
        assertEquals(1000.0, account.getBalance());

        account.deposit(500);
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        BankAccount account = new BankAccount(1L, "Тестовый счет", 0.0);
        account.deposit(1000);

        boolean result = account.withdraw(300);

        assertTrue(result);
        assertEquals(700.0, account.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        BankAccount account = new BankAccount(1L, "Тестовый счет", 0.0);
        account.deposit(500);

        boolean result = account.withdraw(1000);

        assertFalse(result);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void testToString() {
        BankAccount account = new BankAccount(1L, "Тестовый счет", 1500.0);

        String expected = "Счет{id=1, name='Тестовый счет', balance=1500,00}";
        assertEquals(expected, account.toString());
    }
}