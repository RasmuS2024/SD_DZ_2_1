package tiger.bankapp.controller;

import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.service.FinanceFacade;

import java.time.LocalDateTime;

public class CommandHandler {
    private final FinanceFacade finance;
    private final ConsoleHelper console;
    private final DisplayHelper display;

    public CommandHandler(FinanceFacade finance, ConsoleHelper console, DisplayHelper display) {
        this.finance = finance;
        this.console = console;
        this.display = display;
    }

    public void handleCreateAccount() {
        String name = console.readString("Название счета: ");
        console.printSuccess("Счет создан: " + finance.createAccount(name));
    }

    public void handleCreateCategory() {
        String type = console.readString("Тип (INCOME/EXPENSE): ");
        String name = console.readString("Название категории: ");
        console.printSuccess("Категория создана: " + finance.createCategory(type, name));
    }

    public void handleAddIncome() {
        Long accountId = console.readLong("ID счета: ");
        int amount = console.readInt("Сумма: ");
        display.showCategoriesForSelection(finance.getIncomeCategories(), "доходов");
        Integer categoryId = console.readInt("ID категории: ");
        String description = console.readString("Описание: ");

        finance.addIncome(accountId, amount, categoryId, description);
        console.printSuccess("Доход добавлен");
    }

    public void handleAddExpense() {
        Long accountId = console.readLong("ID счета: ");
        int amount = console.readInt("Сумма: ");
        display.showCategoriesForSelection(finance.getExpenseCategories(), "расходов");
        Integer categoryId = console.readInt("ID категории: ");
        String description = console.readString("Описание: ");

        finance.addExpense(accountId, amount, categoryId, description);
        console.printSuccess("Расход добавлен");
    }

    public void handleShowAccounts() {
        display.showAccounts(finance.getAllAccounts());
    }

    public void handleShowCategories() {
        display.showCategories(finance.getIncomeCategories(), finance.getExpenseCategories());
    }

    public void handleShowOperations() {
        Long accountId = console.readLong("ID счета: ");
        BankAccount account = finance.getAccount(accountId);
        if (account != null) {
            display.showOperations(account, finance.getAccountOperations(accountId));
        }
    }

    public void handleDeleteOperation() {
        Integer operationId = console.readInt("ID операции: ");
        if (finance.deleteOperation(operationId)) {
            console.printSuccess("Операция удалена");
        }
    }

    public void handleDeleteAccount() {
        Long accountId = console.readLong("ID счета: ");
        if (finance.deleteAccount(accountId)) {
            console.printSuccess("Счет удален");
        }
    }

    public void handleDeleteCategory() {
        Integer categoryId = console.readInt("ID категории: ");
        if (finance.deleteCategory(categoryId)) {
            console.printSuccess("Категория удалена");
        }
    }

    public void handleDifferenceForPeriod() {
        System.out.println("\nВведите даты ДД.ММ.ГГГГ");
        LocalDateTime from = parseDate(console.readString("Начальная дата: "));
        LocalDateTime to = parseDate(console.readString("Конечная дата: ")).plusDays(1).minusNanos(1);

        finance.printBalanceForPeriod(from, to);
    }

    private LocalDateTime parseDate(String dateStr) {
        String[] parts = dateStr.split("\\.");
        return LocalDateTime.of(
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[0]), 0, 0
        );
    }

    public void handleEditAccount() {
        Long id = console.readLong("ID счета для редактирования: ");

        BankAccount account = finance.getAccount(id);
        if (account == null) {
            console.printError("Счет не найден");
            return;
        }

        console.printMessage("Текущее название: " + account.getName());
        String newName = console.readString("Новое название: ");

        if (finance.updateAccount(id, newName)) {
            console.printSuccess("Счет обновлен");
        }
    }

    public void handleEditCategory() {
        Integer id = console.readInt("ID категории для редактирования: ");

        Category category = finance.getCategory(id); // нужно добавить этот метод в FinanceFacade
        if (category == null) {
            console.printError("Категория не найдена");
            return;
        }

        console.printMessage("Текущая категория: " + category);
        String type = console.readString("Новый тип (INCOME/EXPENSE): ");
        String name = console.readString("Новое название: ");

        if (finance.updateCategory(id, type, name)) {
            console.printSuccess("Категория обновлена");
        }
    }

    public void handleEditOperation() {
        Integer id = console.readInt("ID операции для редактирования: ");

        Operation operation = finance.getOperation(id); // нужно добавить этот метод в FinanceFacade
        if (operation == null) {
            console.printError("Операция не найдена");
            return;
        }

        console.printMessage("Текущее описание: " + operation.getDescription());
        String newDesc = console.readString("Новое описание: ");

        Integer newCatId = null;
        if (operation.getCategoryId() != null) {
            console.printMessage("Текущая категория ID: " + operation.getCategoryId());
            String catInput = console.readString("Новый ID категории (Enter - оставить): ");
            if (!catInput.isEmpty()) {
                newCatId = Integer.parseInt(catInput);
            }
        }

        if (finance.updateOperation(id, newDesc, newCatId)) {
            console.printSuccess("Операция обновлена");
        }
    }
}