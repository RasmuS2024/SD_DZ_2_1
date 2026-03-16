package tiger.bankapp.model;

public class BankAccount {
    private Long id;
    private String name;
    private int balance;

    public BankAccount(Long id, String name) {
        this.id = id;
        this.name = name;
        this.balance = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(int amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Счет{id=%d, name='%s', balance=%d}", id, name, balance);
    }
}