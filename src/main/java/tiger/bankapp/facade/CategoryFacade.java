package tiger.bankapp.facade;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.Category;
import tiger.bankapp.service.CategoryService;

import java.util.List;

@Component
public class CategoryFacade {
    private final CategoryService categoryService;

    @Autowired
    public CategoryFacade(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Category createCategory(String type, String name) {
        return categoryService.createCategory(type, name);
    }

    public Category createIncomeCategory(String name) {
        return categoryService.createIncomeCategory(name);
    }

    public Category createExpenseCategory(String name) {
        return categoryService.createExpenseCategory(name);
    }

    public Category getCategory(Integer id) {
        return categoryService.getCategory(id);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public List<Category> getIncomeCategories() {
        return categoryService.getIncomeCategories();
    }

    public List<Category> getExpenseCategories() {
        return categoryService.getExpenseCategories();
    }

    public boolean updateCategory(Integer id, String type, String name) {
        return categoryService.updateCategory(id, type, name);
    }

    public boolean deleteCategory(Integer id) {
        return categoryService.deleteCategory(id);
    }
}