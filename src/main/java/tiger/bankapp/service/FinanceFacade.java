package tiger.bankapp.service;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FinanceFacade {
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final OperationService operationService;

    @Autowired
    public FinanceFacade(AccountService accountService,
                         CategoryService categoryService,
                         OperationService operationService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.operationService = operationService;
    }
    
    public BankAccount createAccount(String name) {
        return accountService.createAccount(name);
    }

    public List<BankAccount> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public boolean deleteAccount(Long id) {
        return accountService.deleteAccount(id);
    }
    
    public Category createCategory(String type, String name) {
        return categoryService.createCategory(type, name);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public List<Category> getIncomeCategories() {
        return categoryService.getIncomeCategories();
    }

    public List<Category> getExpenseCategories() {
        return categoryService.getExpenseCategories();
    }

    public boolean deleteCategory(Integer id) {
        return categoryService.deleteCategory(id);
    }
    
    public Operation addIncome(Long accountId, int amount, Integer categoryId, String description) {
        return operationService.createIncome(accountId, amount, categoryId, description);
    }

    public Operation addExpense(Long accountId, int amount, Integer categoryId, String description) {
        return operationService.createExpense(accountId, amount, categoryId, description);
    }

    public List<Operation> getAccountOperations(Long accountId) {
        return operationService.getAccountOperations(accountId);
    }

    public boolean deleteOperation(Integer id) {
        return operationService.deleteOperation(id);
    }

    public void printBalanceForPeriod(LocalDateTime from, LocalDateTime to) {
        List<Operation> operations = operationService.getOperationsInPeriod(from, to);

        int income = 0;
        int expense = 0;

        for (Operation op : operations) {
            if ("INCOME".equals(op.getType())) {
                income += op.getAmount();
            } else if ("EXPENSE".equals(op.getType())) {
                expense += op.getAmount();
            }
        }

        int difference = income - expense;

        System.out.println("\n" + "=".repeat(40));
        System.out.println("ОТЧЕТ ЗА ПЕРИОД");
        System.out.println("Период: " + from.toLocalDate() + " - " + to.toLocalDate());
        System.out.println("=".repeat(40));
        System.out.println("Доходы:  " + income);
        System.out.println("Расходы: " + expense);
        System.out.println("Разница: " + difference);

        if (difference > 0) {
            System.out.println("Прибыль");
        } else if (difference < 0) {
            System.out.println("Убыток");
        } else {
            System.out.println("Ноль");
        }
    }

    public BankAccount getAccount(Long accountId) {
        return accountService.getAccount(accountId);
    }

    public boolean updateAccount(Long id, String newName) {
        return accountService.updateAccount(id, newName);
    }

    public boolean updateCategory(Integer id, String type, String name) {
        return categoryService.updateCategory(id, type, name);
    }

    public boolean updateOperation(Integer id, String description, Integer categoryId) {
        return operationService.updateOperation(id, description, categoryId);
    }

    public Category getCategory(Integer id) {
        return categoryService.getCategory(id);
    }

    public Operation getOperation(Integer id) {
        return operationService.getOperation(id);
    }
}