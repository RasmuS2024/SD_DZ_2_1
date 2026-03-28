package tiger.bankapp.repository;

import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category saveCategory(OperationType type, String name);
    Optional<Category> findById(Integer id);
    List<Category> findAll();
    List<Category> findByType(OperationType type);
    boolean deleteById(Integer id);
    void update(Category category);
}
