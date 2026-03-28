package tiger.bankapp.model;

import org.junit.jupiter.api.Test;
import tiger.bankapp.model.enums.OperationType;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    /**
     * Проверяет корректность создания доходной категории и работу метода isIncome.
     */
    @Test
    void testIncomeCategory() {
        Category category = new Category(1, OperationType.INCOME, "Зарплата");

        assertEquals(1, category.getId());
        assertEquals(OperationType.INCOME, category.getType());
        assertEquals("Зарплата", category.getName());
        assertTrue(category.isIncome());
        assertFalse(category.isExpense());
    }

    /**
     * Проверяет корректность создания расходной категории и работу метода isExpense.
     */
    @Test
    void testExpenseCategory() {
        Category category = new Category(2, OperationType.EXPENSE, "Продукты");

        assertEquals(2, category.getId());
        assertEquals(OperationType.EXPENSE, category.getType());
        assertEquals("Продукты", category.getName());
        assertTrue(category.isExpense());
        assertFalse(category.isIncome());
    }

    /**
     * Проверяет текстовое представление категории
     */
    @Test
    void testToString() {
        Category income = new Category(1, OperationType.INCOME, "Зарплата");
        Category expense = new Category(2, OperationType.EXPENSE, "Продукты");

        assertEquals("Категория{id=1, type=Доход, name='Зарплата'}", income.toString());
        assertEquals("Категория{id=2, type=Расход, name='Продукты'}", expense.toString());
    }
}
