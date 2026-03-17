package tiger.bankapp.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Operation;
import tiger.bankapp.service.OperationService;
import tiger.bankapp.service.AccountService;
import tiger.bankapp.service.CategoryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnalyticsFacade {
    private final OperationService operationService;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public void printBalanceForPeriod(LocalDateTime from, LocalDateTime to) {
        List<Operation> operations = operationService.getOperationsInPeriod(from, to);

        double totalIncome = operations.stream()
                .filter(op -> "INCOME".equals(op.getType()))
                .mapToDouble(Operation::getAmount)
                .sum();

        double totalExpense = operations.stream()
                .filter(op -> "EXPENSE".equals(op.getType()))
                .mapToDouble(Operation::getAmount)
                .sum();

        double difference = totalIncome - totalExpense;

        System.out.println("\n" + "=".repeat(40));
        System.out.println("ОТЧЕТ ЗА ПЕРИОД: " + from.toLocalDate() + " - " + to.toLocalDate());
        System.out.println("=".repeat(40));
        System.out.printf("Доходы:  %.2f%n", totalIncome);
        System.out.printf("Расходы: %.2f%n", totalExpense);
        System.out.printf("Разница: %.2f%n", difference);

        if (difference > 0) {
            System.out.println("Результат: ПРИБЫЛЬ");
        } else if (difference < 0) {
            System.out.println("Результат: УБЫТОК");
        } else {
            System.out.println("Результат: БАЛАНС НУЛЕВОЙ");
        }
    }

    public Map<String, Double> getIncomeByCategory(LocalDateTime from, LocalDateTime to) {
        return getSumByCategory(from, to, "INCOME");
    }

    public Map<String, Double> getExpenseByCategory(LocalDateTime from, LocalDateTime to) {
        return getSumByCategory(from, to, "EXPENSE");
    }

    private Map<String, Double> getSumByCategory(LocalDateTime from, LocalDateTime to, String type) {
        return operationService.getOperationsInPeriod(from, to).stream()
                .filter(op -> type.equals(op.getType()))
                .collect(Collectors.groupingBy(
                        op -> {
                            var cat = categoryService.getCategory(op.getCategoryId());
                            return (cat != null) ? cat.getName() : "Без категории";
                        },
                        Collectors.summingDouble(Operation::getAmount)
                ));
    }

    public double getTotalBalance() {
        return accountService.getAllAccounts().stream()
                .mapToDouble(BankAccount::getBalance)
                .sum();
    }

    public void printFullAnalytics(LocalDateTime from, LocalDateTime to) {
        printBalanceForPeriod(from, to);

        System.out.println("\nДоходы по категориям:");
        getIncomeByCategory(from, to).forEach((cat, sum) ->
                System.out.printf("  - %s: %.2f%n", cat, sum));

        System.out.println("\nРасходы по категориям:");
        getExpenseByCategory(from, to).forEach((cat, sum) ->
                System.out.printf("  - %s: %.2f%n", cat, sum));

        System.out.printf("%nОбщий текущий баланс всех счетов: %.2f%n", getTotalBalance());
    }

    /**
     * Пересчитывает баланс счета
     * @param accountId id счета
     * @return истина если баланс исправлен
     */
    public boolean verifyAndFixAccountBalance(Long accountId) {
        BankAccount account = accountService.getAccount(accountId);
        if (account == null) {
            return false;
        }

        List<Operation> operations = operationService.getAccountOperations(accountId);

        double calculatedBalance = operations.stream()
                .mapToDouble(op -> "INCOME".equals(op.getType()) ? op.getAmount() : -op.getAmount())
                .sum();

        double currentBalance = account.getBalance();

        if (Math.abs(calculatedBalance - currentBalance) > 0.001) {
            account.setBalance(calculatedBalance);
            accountService.updateAccount(accountId, account.getName());
            return true;
        }

        return false;
    }

    /**
     * Исправление баланса всех счетов
     * @return количество обработанных счетов
     */
    public int verifyAndFixAllAccounts() {
        List<BankAccount> accounts = accountService.getAllAccounts();
        int fixedCount = 0;

        for (BankAccount account : accounts) {
            if (verifyAndFixAccountBalance(account.getId())) {
                fixedCount++;
            }
        }

        return fixedCount;
    }

}
