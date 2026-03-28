package tiger.bankapp.factory;

import tiger.bankapp.model.Category;

public class CategoryFactory {

    public Category createIncomeCategory(Integer id, String name) {
        return new Category(id, "INCOME", name);
    }

    public Category createExpenseCategory(Integer id, String name) {
        return new Category(id, "EXPENSE", name);
    }

    public Category createCategory(Integer id, String type, String name) {
        validateType(type);
        return new Category(id, type, name);
    }

    public Category createCategory(String type, String name) {
        validateType(type);
        return new Category(null, type, name);
    }

    private void validateType(String type) {
        if (!"INCOME".equals(type) && !"EXPENSE".equals(type)) {
            throw new IllegalArgumentException("Тип должен быть INCOME или EXPENSE");
        }
    }
}