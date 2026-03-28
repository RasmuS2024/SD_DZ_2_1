package tiger.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private long id;
    private String name;
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Счет{id=%s, name='%s', balance=%.2f}", id, name, balance);
    }

}