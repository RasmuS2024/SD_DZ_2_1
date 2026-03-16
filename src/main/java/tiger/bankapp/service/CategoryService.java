package tiger.bankapp.service;

import tiger.bankapp.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String type, String name);
    Category getCategory(Integer id);
    Category createIncomeCategory(String name);
    Category createExpenseCategory(String name);
    List<Category> getAllCategories();
    List<Category> getIncomeCategories();
    List<Category> getExpenseCategories();
    boolean updateCategory(Integer id, String type, String name);
    boolean deleteCategory(Integer id);
}