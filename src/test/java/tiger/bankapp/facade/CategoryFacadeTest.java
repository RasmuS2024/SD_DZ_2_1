package tiger.bankapp.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;
import tiger.bankapp.service.CategoryService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryFacadeTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryFacade categoryFacade;

    @Test
    void testCreateIncome() {
        Category category = new Category(1, OperationType.INCOME, "Зарплата");
        when(categoryService.createIncomeCategory("Зарплата")).thenReturn(category);

        Category result = categoryFacade.createIncomeCategory("Зарплата");

        assertEquals("Зарплата", result.getName());
        assertEquals(OperationType.INCOME, result.getType());
    }

    @Test
    void testGetExpenseCategories() {
        Category category = new Category(1, OperationType.EXPENSE, "Продукты");
        when(categoryService.getExpenseCategories()).thenReturn(List.of(category));

        List<Category> result = categoryFacade.getExpenseCategories();

        assertFalse(result.isEmpty());
        assertEquals("Продукты", result.get(0).getName());
    }

    @Test
    void testDelete() {
        when(categoryService.deleteCategory(1)).thenReturn(true);
        assertTrue(categoryFacade.deleteCategory(1));
        verify(categoryService).deleteCategory(1);
    }
}
