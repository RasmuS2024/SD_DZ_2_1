package tiger.bankapp.factory;

import tiger.bankapp.model.Category;
import tiger.bankapp.model.enums.OperationType;

public interface CategoryFactory {

    Category createCategory(Integer id, String typeStr, String name);

    Category createCategory(Integer id, OperationType type, String name);
}