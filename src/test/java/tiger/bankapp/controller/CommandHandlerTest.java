package tiger.bankapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.AnalyticsFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.exceptions.ValidationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {

    @Mock private AccountFacade accountFacade;
    @Mock private CategoryFacade categoryFacade;
    @Mock private OperationFacade operationFacade;
    @Mock private AnalyticsFacade analyticsFacade;

    @Mock private ConsoleHelper console;
    @Mock private DisplayHelper display;

    private CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        // Вручную создаем контекст с моками
        FacadeContext facades = new FacadeContext(
                accountFacade,
                categoryFacade,
                operationFacade,
                analyticsFacade
        );

        // Инициализируем хендлер
        commandHandler = new CommandHandler(
                List.of(), // importers
                List.of(), // exporters
                facades,
                console,
                display
        );
    }

    @Test
    void testHandleCreateAccount_Success() {
        // Arrange
        String accountName = "Зарплатный";
        when(console.readString(anyString())).thenReturn(accountName);
        when(accountFacade.createAccount(accountName)).thenReturn(new BankAccount());

        // Act
        commandHandler.handleCreateAccount();

        // Assert
        verify(accountFacade).createAccount(accountName);
        verify(console).printSuccess(contains("Счет успешно создан"));
    }

    @Test
    void testHandleCreateAccount_ValidationError() {
        // Arrange: пользователь ввел пустую строку
        when(console.readString(anyString())).thenReturn("   ");

        // Act & Assert
        assertThrows(ValidationException.class, () -> commandHandler.handleCreateAccount());

        // Проверяем, что фасад НЕ вызывался
        verify(accountFacade, never()).createAccount(anyString());
    }

    @Test
    void testHandleDeleteAccount_NonZeroBalance() {
        // Arrange
        Long accountId = 1L;
        BankAccount richAccount = new BankAccount();
        richAccount.deposit(100.0);

        when(console.readLong(anyString())).thenReturn(accountId);
        when(accountFacade.getAccount(accountId)).thenReturn(richAccount);

        // Act & Assert
        assertThrows(ValidationException.class, () -> commandHandler.handleDeleteAccount());

        // Проверяем, что метод удаления в фасаде не был вызван
        verify(accountFacade, never()).deleteAccount(accountId);
    }
}
