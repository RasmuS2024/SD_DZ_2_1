package tiger.bankapp.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;
import tiger.bankapp.service.CategoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryFacadeTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryFacade categoryFacade;

    /**
     * Проверяет корректность конвертации строкового типа в Enum и создание категории через фасад.
     */
    @Test
    void testCreateCategory() {
        Category category = new Category(1, OperationType.INCOME, "Зарплата");
        when(categoryService.createCategory(OperationType.INCOME, "Зарплата")).thenReturn(category);

        Category result = categoryFacade.createCategory("INCOME", "Зарплата");

        assertNotNull(result);
        assertEquals(OperationType.INCOME, result.getType());
        verify(categoryService).createCategory(OperationType.INCOME, "Зарплата");
    }

    /**
     * Проверяет получение списка всех доходных категорий через вызов сервиса.
     */
    @Test
    void testGetIncomeCategories() {
        when(categoryService.getIncomeCategories()).thenReturn(List.of(new Category()));

        List<Category> result = categoryFacade.getIncomeCategories();

        assertFalse(result.isEmpty());
        verify(categoryService).getIncomeCategories();
    }

    /**
     * Проверяет проброс параметров обновления категории и корректную трансформацию типа.
     */
    @Test
    void testUpdateCategory() {
        categoryFacade.updateCategory(1, "EXPENSE", "Еда");

        verify(categoryService).updateCategory(1, OperationType.EXPENSE, "Еда");
    }

    /**
     * Проверяет вызов метода удаления категории.
     */
    @Test
    void testDeleteCategory() {
        when(categoryService.deleteCategory(1)).thenReturn(true);

        boolean deleted = categoryFacade.deleteCategory(1);

        assertTrue(deleted);
        verify(categoryService).deleteCategory(1);
    }
}
