package tiger.bankapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tiger.bankapp.model.BankAccount;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(1L, "Тестовый счет");
    }

    @Test
    void testConstructor() {
        assertEquals(1L, account.getId());
        assertEquals("Тестовый счет", account.getName());
        assertEquals(0, account.getBalance());
    }

    @Test
    void testDeposit() {
        account.deposit(1000);
        assertEquals(1000, account.getBalance());

        account.deposit(500);
        assertEquals(1500, account.getBalance());
    }

    @Test
    void testDepositNegativeAmount() {
        account.deposit(-100);
        assertEquals(0, account.getBalance(), "Отрицательная сумма не должна изменять баланс");
    }

    @Test
    void testWithdraw() {
        account.deposit(1000);

        boolean result = account.withdraw(300);
        assertTrue(result);
        assertEquals(700, account.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        account.deposit(500);

        boolean result = account.withdraw(1000);
        assertFalse(result);
        assertEquals(500, account.getBalance(), "Баланс не должен измениться");
    }

    @Test
    void testWithdrawNegativeAmount() {
        account.deposit(500);

        boolean result = account.withdraw(-100);
        assertFalse(result);
        assertEquals(500, account.getBalance());
    }

    @Test
    void testToString() {
        String expected = "Счет{id=1, name='Тестовый счет', balance=0}";
        assertEquals(expected, account.toString());
    }
}