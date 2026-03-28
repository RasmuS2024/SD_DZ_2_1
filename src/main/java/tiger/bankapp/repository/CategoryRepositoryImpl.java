package tiger.bankapp.repository;

import tiger.bankapp.model.Category;
import tiger.bankapp.factory.CategoryFactory;
import tiger.bankapp.model.enums.OperationType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CategoryRepositoryImpl implements CategoryRepository {
    private final Map<Integer, Category> storage = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final CategoryFactory categoryFactory;

    public CategoryRepositoryImpl(CategoryFactory categoryFactory) {
        this.categoryFactory = categoryFactory;
    }

    public Category saveCategory(OperationType type, String name) {
        int id = nextId.getAndIncrement();
        Category category = categoryFactory.createCategory(id, String.valueOf(type), name);
        storage.put(id, category);
        return category;
    }

    public Optional<Category> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Category> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Category> findByType(OperationType type) {
        return storage.values().stream()
                .filter(c -> c.getType() == type) // Enum можно сравнивать через ==
                .collect(Collectors.toList());
    }

    public boolean deleteById(Integer id) {
        return storage.remove(id) != null;
    }

    public void update(Category category) {
        storage.put(category.getId(), category);
    }

}