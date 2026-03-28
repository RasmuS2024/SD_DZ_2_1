package tiger.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiger.bankapp.model.enums.OperationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id;
    private OperationType type;
    private String name;

    public boolean isIncome() {
        return OperationType.INCOME.equals(type);
    }

    public boolean isExpense() {
        return OperationType.EXPENSE.equals(type);
    }

    @Override
    public String toString() {
        String typeDisplay = isIncome() ? "Доход" : "Расход";
        return String.format("Категория{id=%d, type=%s, name='%s'}", id, typeDisplay, name);
    }
}
