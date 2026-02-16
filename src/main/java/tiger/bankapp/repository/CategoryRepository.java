package tiger.bankapp.repository;

import org.springframework.stereotype.Repository;
import tiger.bankapp.model.Category;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CategoryRepository {
    private final Map<Integer, Category> storage = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(nextId.getAndIncrement());
        }
        storage.put(category.getId(), category);
        return category;
    }

    public Optional<Category> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Category> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Category> findByType(String type) {
        return storage.values().stream()
                .filter(c -> c.getType().equals(type))
                .toList();
    }

    public boolean deleteById(Integer id) {
        return storage.remove(id) != null;
    }

    public void update(Category category) {
        storage.put(category.getId(), category);
    }

}