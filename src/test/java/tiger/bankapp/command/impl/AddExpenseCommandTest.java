package tiger.bankapp.command.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.controller.CommandHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddExpenseCommandTest {

    @Mock
    private CommandHandler handler;

    @InjectMocks
    private AddExpenseCommand command;

    /**
     * Проверяет реализацию паттерна Command: метод execute должен вызывать handleAddExpense у обработчика.
     */
    @Test
    void testExecute() {
        command.execute();
        verify(handler).handleAddExpense();
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
