package tiger.bankapp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.Category;
import tiger.bankapp.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String type, String name) {
        Category category = new Category(null, type, name);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Integer id) {
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
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setType(type);
                    category.setName(name);
                    categoryRepository.update(category);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteCategory(Integer id) {
        return categoryRepository.deleteById(id);
    }
}