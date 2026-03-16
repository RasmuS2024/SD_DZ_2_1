package tiger.bankapp.model;

public class Category {
    private Integer id;
    private String type; // "INCOME" или "EXPENSE"
    private String name;

    public Category(Integer id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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