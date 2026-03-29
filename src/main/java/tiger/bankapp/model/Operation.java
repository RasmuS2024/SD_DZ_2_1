package tiger.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiger.bankapp.model.enums.OperationType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private Integer id;
    private OperationType type;
    private Long bankAccountId;
    private double amount;
    private LocalDateTime date;
    private String description;
    private Integer categoryId;

    public String getFormattedDate() {
        return date != null ? date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) : "";
    }

    @Override
    public String toString() {
        String typeName = (type == OperationType.INCOME) ? "Доход" : "Расход";
        return String.format(Locale.US, "Операция{id=%d, тип=%s, счет=%d, сумма=%.2f, дата=%s}",
                id, typeName, bankAccountId, amount, getFormattedDate());
    }
}