package tiger.bankapp.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.model.Operation;
import tiger.bankapp.service.OperationService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationFacadeTest {

    @Mock
    private OperationService operationService;

    @InjectMocks
    private OperationFacade operationFacade;

    /**
     * Проверяет делегирование создания доходной операции сервису через паттерн Facade.
     */
    @Test
    void testAddIncome() {
        Operation operation = new Operation();
        when(operationService.createIncome(1L, 100.0, 5, "Зарплата")).thenReturn(operation);

        Operation result = operationFacade.addIncome(1L, 100.0, 5, "Зарплата");

        assertNotNull(result);
        verify(operationService).createIncome(1L, 100.0, 5, "Зарплата");
    }

    /**
     * Проверяет вызов обновления описания и категории операции.
     */
    @Test
    void testUpdateOperation() {
        when(operationService.updateOperation(1, "Новое", 10)).thenReturn(true);

        boolean result = operationFacade.updateOperation(1, "Новое", 10);

        assertTrue(result);
        verify(operationService).updateOperation(1, "Новое", 10);
    }

    /**
     * Проверяет вызов методов импорта операций с сохранением исторической даты.
     */
    @Test
    void testImportExpense() {
        LocalDateTime date = LocalDateTime.now();
        operationFacade.importExpense(2L, 50.0, 3, "Обед", date);

        verify(operationService).importExpense(2L, 50.0, 3, "Обед", date);
    }

    /**
     * Проверяет корректность вызова удаления операции через фасад.
     */
    @Test
    void testDeleteOperation() {
        when(operationService.deleteOperation(100)).thenReturn(true);

        boolean deleted = operationFacade.deleteOperation(100);

        assertTrue(deleted);
        verify(operationService).deleteOperation(100);
    }
}
