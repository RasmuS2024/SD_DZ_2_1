package tiger.bankapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testIncomeCategory() {
        Category category = new Category(1, "INCOME", "Зарплата");

        assertEquals(1, category.getId());
        assertEquals("INCOME", category.getType());
        assertEquals("Зарплата", category.getName());
        assertTrue(category.isIncome());
        assertFalse(category.isExpense());
    }

    @Test
    void testExpenseCategory() {
        Category category = new Category(2, "EXPENSE", "Продукты");

        assertEquals(2, category.getId());
        assertEquals("EXPENSE", category.getType());
        assertEquals("Продукты", category.getName());
        assertTrue(category.isExpense());
        assertFalse(category.isIncome());
    }

    @Test
    void testToString() {
        Category income = new Category(1, "INCOME", "Зарплата");
        Category expense = new Category(2, "EXPENSE", "Продукты");

        assertEquals("Категория{id=1, type=Доход, name='Зарплата'}", income.toString());
        assertEquals("Категория{id=2, type=Расход, name='Продукты'}", expense.toString());
    }
}
