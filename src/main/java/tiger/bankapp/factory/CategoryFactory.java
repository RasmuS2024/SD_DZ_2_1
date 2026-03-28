package tiger.bankapp.factory;

import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;

public class CategoryFactory {

    public Category createCategory(Integer id, String typeStr, String name) {
        OperationType type = OperationType.valueOf(typeStr.toUpperCase());
        return new Category(id, type, name);
    }

    public Category createCategory(Integer id, OperationType type, String name) {
        return new Category(id, type, name);
    }

}
