package tiger.bankapp.helpers;

import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;

import java.util.List;

public class DisplayHelper {

    public void showAccounts(List<BankAccount> accounts) {
        System.out.println("\nСчета:");
        if (accounts.isEmpty()) {
            System.out.println("  Нет созданных счетов");
        } else {
            for (BankAccount acc : accounts) {
                System.out.println("  " + acc);
            }
        }
    }

    public void showCategories(List<Category> incomeCategories, List<Category> expenseCategories) {
        System.out.println("\nКатегории доходов:");
        if (incomeCategories.isEmpty()) {
            System.out.println("  Нет категорий доходов");
        } else {
            for (Category cat : incomeCategories) {
                System.out.println("  " + cat);
            }
        }

        System.out.println("\nКатегории расходов:");
        if (expenseCategories.isEmpty()) {
            System.out.println("  Нет категорий расходов");
        } else {
            for (Category cat : expenseCategories) {
                System.out.println("  " + cat);
            }
        }
    }

    public void showOperations(BankAccount account, List<Operation> operations) {
        System.out.println("\nОперации по счету \"" + account.getName() + "\":");
        if (operations.isEmpty()) {
            System.out.println("  Нет операций");
        } else {
            for (Operation op : operations) {
                String type = "INCOME".equals(op.getType()) ? "ДОХОД" : "РАСХОД";
                System.out.println("  [" + op.getId() + "] " + op.getFormattedDate() +
                        " | " + type + " | " + op.getAmount() + " | " + op.getDescription());
            }
            System.out.println("  Текущий баланс: " + account.getBalance());
        }
    }

    public void showCategoriesForSelection(List<Category> categories, String type) {
        System.out.println("\nДоступные категории " + type + ":");
        for (Category cat : categories) {
            System.out.println("  " + cat.getId() + ". " + cat.getName());
        }
    }
}