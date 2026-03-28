package tiger.bankapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.command.impl.CreateAccountCommand;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.AnalyticsFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.exceptions.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAccountCommandTest {

    @Mock private AccountFacade accountFacade;
    @Mock private CategoryFacade categoryFacade;
    @Mock private OperationFacade operationFacade;
    @Mock private AnalyticsFacade analyticsFacade;

    @Mock private ConsoleHelper console;
    @Mock private DisplayHelper display;

    private CreateAccountCommand command;

    @BeforeEach
    void setUp() {
        // Вручную создаем контекст с моками
        FacadeContext facades = new FacadeContext(
                accountFacade,
                categoryFacade,
                operationFacade,
                analyticsFacade
        );

        // Инициализируем команду
        command = new CreateAccountCommand(facades, console);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        String accountName = "Зарплатный";
        when(console.readString(anyString())).thenReturn(accountName);
        when(accountFacade.createAccount(accountName)).thenReturn(new BankAccount());

        // Act
        command.execute();

        // Assert
        verify(accountFacade).createAccount(accountName);
        verify(console).printSuccess(contains("Счет успешно создан"));
    }

    @Test
    void testExecute_ValidationError() {
        // Arrange: пользователь ввел пустую строку
        when(console.readString(anyString())).thenReturn("   ");

        // Act & Assert
        assertThrows(ValidationException.class, () -> command.execute());

        // Проверяем, что фасад НЕ вызывался
        verify(accountFacade, never()).createAccount(anyString());
    }
}
