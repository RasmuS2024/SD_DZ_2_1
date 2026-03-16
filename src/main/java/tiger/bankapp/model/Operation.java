package tiger.bankapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operation {
    private Integer id;
    private String type;
    private Long bankAccountId;
    private int amount;
    private LocalDateTime date;
    private String description;
    private Integer categoryId;

    public Operation(Integer id, String type, Long bankAccountId, int amount,
                     Integer categoryId, String description) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.categoryId = categoryId;
        this.description = description;
    }

    public Integer getId() {
        return id; }

    public void setId(Integer id) {
        this.id = id; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return String.format("Операция{id=%d, type=%s, счет=%d, сумма=%d, дата=%s}",
                id, type, bankAccountId, amount, getFormattedDate());
    }
}