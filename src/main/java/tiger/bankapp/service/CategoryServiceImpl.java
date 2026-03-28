package tiger.bankapp.service;

import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;
import tiger.bankapp.repository.CategoryRepository;
import tiger.bankapp.factory.CategoryFactory;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryFactory categoryFactory) {
        this.categoryRepository = categoryRepository;
        this.categoryFactory = categoryFactory;
    }

    @Override
    public Category createCategory(OperationType type, String name) {
        if (type == null) {
            throw new IllegalArgumentException("Тип категории обязателен");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название категории не может быть пустым");
        }
        return categoryRepository.saveCategory(type, name.trim());
    }

    @Override
    public Category createIncomeCategory(String name) {
        return createCategory(OperationType.INCOME, name);
    }

    @Override
    public Category createExpenseCategory(String name) {
        return createCategory(OperationType.EXPENSE, name);
    }

    @Override
    public Category getCategory(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getIncomeCategories() {
        return categoryRepository.findByType(OperationType.INCOME);
    }

    @Override
    public List<Category> getExpenseCategories() {
        return categoryRepository.findByType(OperationType.EXPENSE);
    }


    @Override
    public boolean updateCategory(Integer id, OperationType type, String name) {
        if (id == null || id <= 0) {
            return false;
        }

        return categoryRepository.findById(id)
                .map(category -> {
                    OperationType newType = (type != null) ? type : category.getType();
                    String newName = (name != null && !name.trim().isEmpty()) ? name.trim() : category.getName();

                    Category updatedCategory = categoryFactory.createCategory(id, newType, newName);

                    categoryRepository.update(updatedCategory);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteCategory(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        return categoryRepository.deleteById(id);
    }
}