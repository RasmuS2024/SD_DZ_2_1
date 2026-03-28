package tiger.bankapp.command.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.AnalyticsFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddExpenseCommandTest {

    @Mock private AccountFacade accountFacade;
    @Mock private CategoryFacade categoryFacade;
    @Mock private OperationFacade operationFacade;
    @Mock private AnalyticsFacade analyticsFacade;

    @Mock private ConsoleHelper console;
    @Mock private DisplayHelper display;

    private AddExpenseCommand command;

    @BeforeEach
    void setUp() {
        FacadeContext facades = new FacadeContext(
                accountFacade,
                categoryFacade,
                operationFacade,
                analyticsFacade
        );
        command = new AddExpenseCommand(facades, console, display);
    }

    /**
     * Проверяет реализацию команды: execute должен взаимодействовать с фасадами и консолью.
     */
    @Test
    void testExecute() {
        // Arrange
        Long accountId = 1L;
        int amount = 100;
        Integer categoryId = 1;
        String description = "Покупка";

        when(console.readLong(anyString())).thenReturn(accountId);
        when(console.readInt("Сумма:")).thenReturn(amount);
        when(categoryFacade.getExpenseCategories()).thenReturn(List.of(new Category()));
        when(console.readInt("ID категории:")).thenReturn(categoryId);
        when(console.readString("Описание:")).thenReturn(description);
        when(operationFacade.addExpense(accountId, amount, categoryId, description)).thenReturn(new Operation());
        when(accountFacade.getAccount(accountId)).thenReturn(new BankAccount());

        // Act
        command.execute();

        // Assert
        verify(display).showCategoriesForSelection(categoryFacade.getExpenseCategories(), "расходов");
        verify(operationFacade).addExpense(accountId, amount, categoryId, description);
        verify(console).printSuccess(anyString());
        verify(console).printMessage(anyString()); // showAccountBalance
    }

    /**
     * Проверяет корректность метаданных команды (текст метки и порядок в меню).
     */
    @Test
    void testCommandMetadata() {
        assertEquals("Добавить расход", command.getLabel());
        assertEquals(4, command.getOrder());
    }
}
