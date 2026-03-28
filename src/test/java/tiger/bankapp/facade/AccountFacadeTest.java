package tiger.bankapp.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.service.AccountService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountFacadeTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountFacade accountFacade;

    /**
     * Проверяет делегирование создания счета сервису
     */
    @Test
    void testCreateAccount() {
        BankAccount account = new BankAccount(1L, "Счет", 0.0);
        when(accountService.createAccount("Счет")).thenReturn(account);

        BankAccount result = accountFacade.createAccount("Счет");

        assertNotNull(result);
        assertEquals("Счет", result.getName());
        verify(accountService).createAccount("Счет");
    }

    /**
     * Проверяет проброс вызова операции пополнения баланса через фасад
     */
    @Test
    void testDeposit() {
        when(accountService.deposit(1L, 500.0)).thenReturn(true);

        boolean success = accountFacade.deposit(1L, 500.0);

        assertTrue(success);
        verify(accountService).deposit(1L, 500.0);
    }

    /**
     * Проверяет корректность вызова удаления счета в сервисе
     */
    @Test
    void testDeleteAccount() {
        when(accountService.deleteAccount(1L)).thenReturn(true);

        boolean deleted = accountFacade.deleteAccount(1L);

        assertTrue(deleted);
        verify(accountService).deleteAccount(1L);
    }
}
