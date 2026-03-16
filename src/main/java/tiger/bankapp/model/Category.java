package tiger.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id;
    private String type; // "INCOME" или "EXPENSE"
    private String name;

    public boolean isIncome() {
        return "INCOME".equals(type);
    }

    public boolean isExpense() {
        return "EXPENSE".equals(type);
    }

    @Override
    public String toString() {
        String typeDisplay = isIncome() ? "Доход" : "Расход";
        return String.format("Категория{id=%d, type=%s, name='%s'}", id, typeDisplay, name);
    }
}