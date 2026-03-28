package tiger.bankapp.factory;

import org.junit.jupiter.api.Test;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;

import static org.junit.jupiter.api.Assertions.*;

class CategoryFactoryTest {

    private final CategoryFactory factory = new CategoryFactory();

    /**
     * Проверяет создание категории на основе строкового представления типа.
     */
    @Test
    void testCreateCategoryFromString() {
        Category category = factory.createCategory(1, "INCOME", "Зарплата");

        assertEquals(1, category.getId());
        assertEquals(OperationType.INCOME, category.getType());
        assertEquals("Зарплата", category.getName());
    }

    /**
     * Проверяет создание категории с использованием перечисления OperationType.
     */
    @Test
    void testCreateCategoryFromEnum() {
        Category category = factory.createCategory(2, OperationType.EXPENSE, "Еда");

        assertEquals(2, category.getId());
        assertEquals(OperationType.EXPENSE, category.getType());
    }

    /**
     * Проверяет генерацию исключения при передаче некорректной строки типа.
     */
    @Test
    void testCreateCategoryInvalidType() {
        assertThrows(IllegalArgumentException.class, () ->
                factory.createCategory(3, "INVALID", "Тест")
        );
    }
}
