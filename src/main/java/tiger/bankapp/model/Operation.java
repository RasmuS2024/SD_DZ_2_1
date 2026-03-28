package tiger.bankapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation {
    private Integer id;
    private String type;
    private Long bankAccountId;
    private double amount;
    private LocalDateTime date;
    private String description;
    private Integer categoryId;

    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return String.format("Операция{id=%d, type=%s, счет=%d, сумма=%f, дата=%s}",
                id, type, bankAccountId, amount, getFormattedDate());
    }
}