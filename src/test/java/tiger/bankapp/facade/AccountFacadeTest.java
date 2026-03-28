package tiger.bankapp.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.service.AccountService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountFacadeTest {

    @Mock
    private AccountService accountService;

    private AccountFacade accountFacade;

    @BeforeEach
    void setUp() {
        accountFacade = new AccountFacade(accountService);
    }

    @Test
    void createAccount_returnsAccount() {
        BankAccount expected = new BankAccount(1L, "John", 0.0);
        when(accountService.createAccount("John")).thenReturn(expected);

        BankAccount result = accountFacade.createAccount("John");

        assertEquals(expected, result);
        verify(accountService).createAccount("John");
    }

    @Test
    void createAccountWithBalance_returnsAccount() {
        BankAccount expected = new BankAccount(1L, "John", 100.0);
        when(accountService.createAccountWithBalance("John", 100.0)).thenReturn(expected);

        BankAccount result = accountFacade.createAccountWithBalance("John", 100.0);

        assertEquals(expected, result);
    }

    @Test
    void getAccount_returnsAccount() {
        BankAccount expected = new BankAccount(1L, "John", 0.0);
        when(accountService.getAccount(1L)).thenReturn(expected);

        assertEquals(expected, accountFacade.getAccount(1L));
    }

    @Test
    void getAllAccounts_returnsList() {
        List<BankAccount> expected = Arrays.asList(
                new BankAccount(1L, "John", 100.0),
                new BankAccount(2L, "Jane", 200.0)
        );
        when(accountService.getAllAccounts()).thenReturn(expected);

        List<BankAccount> result = accountFacade.getAllAccounts();

        assertEquals(2, result.size());
        assertEquals("John", result.getFirst().getName());
    }

    @Test
    void updateAccount_returnsTrue() {
        when(accountService.updateAccount(1L, "NewName")).thenReturn(true);
        assertTrue(accountFacade.updateAccount(1L, "NewName"));
    }

    @Test
    void deleteAccount_returnsTrue() {
        when(accountService.deleteAccount(1L)).thenReturn(true);
        assertTrue(accountFacade.deleteAccount(1L));
    }

    @Test
    void deposit_returnsTrue() {
        when(accountService.deposit(1L, 50.0)).thenReturn(true);
        assertTrue(accountFacade.deposit(1L, 50.0));
    }

    @Test
    void withdraw_returnsFalse_whenFailed() {
        when(accountService.withdraw(1L, 100.0)).thenReturn(false);
        assertFalse(accountFacade.withdraw(1L, 100.0));
    }
}