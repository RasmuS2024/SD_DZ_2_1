package tiger.bankapp.service;

import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;

import java.util.List;

public interface CategoryService {

    Category createCategory(OperationType type, String name);

    Category createIncomeCategory(String name);
    Category createExpenseCategory(String name);

    Category getCategory(Integer id);
    List<Category> getAllCategories();
    List<Category> getIncomeCategories();
    List<Category> getExpenseCategories();

    boolean updateCategory(Integer id, OperationType type, String name);

    boolean deleteCategory(Integer id);
}
