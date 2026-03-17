package tiger.bankapp.service;

import org.springframework.stereotype.Service;
import tiger.bankapp.model.Category;
import tiger.bankapp.repository.CategoryRepository;
import tiger.bankapp.factory.CategoryFactory;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryFactory categoryFactory) {
        this.categoryRepository = categoryRepository;
        this.categoryFactory = categoryFactory;
    }

    @Override
    public Category createCategory(String type, String name) {
        if (type == null || (!"INCOME".equals(type) && !"EXPENSE".equals(type))) {
            throw new IllegalArgumentException("Тип должен быть INCOME или EXPENSE");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название категории не может быть пустым");
        }

        return categoryRepository.saveCategory(type, name.trim());
    }

    @Override
    public Category createIncomeCategory(String name) {
        return createCategory("INCOME", name);
    }

    @Override
    public Category createExpenseCategory(String name) {
        return createCategory("EXPENSE", name);
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
        return categoryRepository.findByType("INCOME");
    }

    @Override
    public List<Category> getExpenseCategories() {
        return categoryRepository.findByType("EXPENSE");
    }

    @Override
    public boolean updateCategory(Integer id, String type, String name) {
        if (id == null || id <= 0) {
            return false;
        }

        return categoryRepository.findById(id)
                .map(category -> {
                    String newType = type != null ? type : category.getType();
                    String newName = name != null ? name.trim() : category.getName();

                    Category updatedCategory = categoryFactory.createCategory(
                            category.getId(), newType, newName
                    );

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