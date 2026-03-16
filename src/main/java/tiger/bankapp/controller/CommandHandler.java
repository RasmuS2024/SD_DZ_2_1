package tiger.bankapp.controller;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.facade.AnalyticsFacade;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.exporter.DataExporter;
import tiger.bankapp.importer.*;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.ImportFormat;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CommandHandler {
    private final AccountFacade accountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;
    private final AnalyticsFacade analyticsFacade;
    private final ConsoleHelper console;
    private final DisplayHelper display;
    private final List<DataImporter> importers;
    private final List<DataExporter> exporters;

    @Autowired
    public CommandHandler(
            List<DataImporter> importers,
            List<DataExporter> exporters,
            AccountFacade accountFacade,
            CategoryFacade categoryFacade,
            OperationFacade operationFacade,
            AnalyticsFacade analyticsFacade
    ) {
        this.importers = importers;
        this.exporters = exporters;
        this.accountFacade = accountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
        this.analyticsFacade = analyticsFacade;
        this.console = new ConsoleHelper();
        this.display = new DisplayHelper();
    }

    public void handleCreateAccount() {
        String name = console.readString("Название счета: ");
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Название счета не может быть пустым");
        }

        BankAccount account = accountFacade.createAccount(name);
        console.printSuccess("Счет успешно создан: " + account);
    }

    public void handleCreateCategory() {
        System.out.println("Выберите тип категории:");
        System.out.println("1. Доход");
        System.out.println("2. Расход");

        String type = switch (console.readInt("Ваш выбор: ")) {
            case 1 -> "INCOME";
            case 2 -> "EXPENSE";
            default -> {
                console.printError("Неверный выбор. Используется INCOME");
                yield "INCOME";
            }
        };

        String name = console.readString("Название категории: ");
        Category category = categoryFacade.createCategory(type, name);
        console.printSuccess("Категория успешно создана: " + category);
    }

    public void handleAddIncome() {
        Long accountId = console.readLong("ID счета: ");
        int amount = console.readInt("Сумма: ");

        display.showCategoriesForSelection(categoryFacade.getIncomeCategories(), "доходов");
        Integer categoryId = console.readInt("ID категории: ");
        String description = console.readString("Описание: ");

        Operation operation = operationFacade.addIncome(accountId, amount, categoryId, description);
        console.printSuccess("Доход добавлен. " + operation);
        showAccountBalance(accountId);
    }

    public void handleAddExpense() {
        Long accountId = console.readLong("ID счета: ");
        int amount = console.readInt("Сумма: ");

        display.showCategoriesForSelection(categoryFacade.getExpenseCategories(), "расходов");
        Integer categoryId = console.readInt("ID категории: ");
        String description = console.readString("Описание: ");

        Operation operation = operationFacade.addExpense(accountId, amount, categoryId, description);
        console.printSuccess("Расход добавлен. " + operation);
        showAccountBalance(accountId);
    }

    public void handleShowAccounts() {
        display.showAccounts(accountFacade.getAllAccounts());
    }

    public void handleShowCategories() {
        display.showCategories(categoryFacade.getIncomeCategories(),
                categoryFacade.getExpenseCategories());
    }

    public void handleShowOperations() {
        Long accountId = console.readLong("ID счета: ");

        BankAccount account = accountFacade.getAccount(accountId);
        display.showOperations(account, operationFacade.getAccountOperations(accountId));
    }

    public void handleDeleteOperation() {
        Integer operationId = console.readInt("ID операции: ");

        operationFacade.deleteOperation(operationId);
        console.printSuccess("Операция удалена, баланс счета скорректирован");
    }

    public void handleDeleteAccount() {
        Long accountId = console.readLong("ID счета: ");

        BankAccount account = accountFacade.getAccount(accountId);
        if (account.getBalance() != 0) {
            throw new ValidationException("Нельзя удалить счет с ненулевым балансом: " + account.getBalance());
        }

        accountFacade.deleteAccount(accountId);
        console.printSuccess("Счет удален");
    }

    public void handleDeleteCategory() {
        Integer categoryId = console.readInt("ID категории: ");

        categoryFacade.deleteCategory(categoryId);
        console.printSuccess("Категория удалена");
    }

    public void handleEditAccount() {
        Long id = console.readLong("ID счета для редактирования: ");

        BankAccount account = accountFacade.getAccount(id);
        console.printMessage("Текущее название: " + account.getName());

        String newName = console.readString("Новое название: ");
        if (newName == null || newName.trim().isEmpty()) {
            throw new ValidationException("Название счета не может быть пустым");
        }

        accountFacade.updateAccount(id, newName);
        console.printSuccess("Счет обновлен");
    }

    public void handleEditCategory() {
        Integer id = console.readInt("ID категории для редактирования: ");

        Category category = categoryFacade.getCategory(id);
        console.printMessage("Текущая категория: " + category);

        String type = console.readString("Новый тип (INCOME/EXPENSE): ");
        String name = console.readString("Новое название: ");

        categoryFacade.updateCategory(id, type, name);
        console.printSuccess("Категория обновлена");
    }

    public void handleEditOperation() {
        Integer id = console.readInt("ID операции для редактирования: ");

        Operation operation = operationFacade.getOperation(id);
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

        operationFacade.updateOperation(id, newDesc, newCatId);
        console.printSuccess("Операция обновлена");
    }

    public void handleDifferenceForPeriod() {
        console.printMessage("\nВведите даты в формате ДД.ММ.ГГГГ");

        LocalDateTime from = parseDate(console.readString("Начальная дата: "));
        LocalDateTime to = parseDate(console.readString("Конечная дата: ")).plusDays(1).minusNanos(1);

        analyticsFacade.printBalanceForPeriod(from, to);
    }

    private LocalDateTime parseDate(String dateStr) {
        try {
            String[] parts = dateStr.split("\\.");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return LocalDateTime.of(year, month, day, 0, 0);
        } catch (Exception e) {
            throw new ValidationException("Неверный формат даты. Используйте ДД.ММ.ГГГГ");
        }
    }

    private void showAccountBalance(Long accountId) {
        BankAccount account = accountFacade.getAccount(accountId);
        console.printMessage("Новый баланс: " + account.getBalance());
    }

    /**
     * Метод импорта данных из файла по нужному формату
     * @param filePath имя файла
     */
    public void handleImport(String filePath) {
        ImportFormat format = ImportFormat.getByFileName(filePath);

        DataImporter importer = importers.stream()
                .filter(i -> i.getSupportedFormat() == format)
                .findFirst()
                .orElseThrow(() -> new ValidationException("Импортер для формата " + format.getLabel() + " не найден"));

        importer.importData(filePath);

        console.printSuccess("Данные из файла " + filePath + " успешно импортированы!");
    }

    /**
     * Метод экспорта данных в файл по нужному формату
     * @param filePath имя файла
     */
    public void handleExport(String filePath, ImportFormat format) {
        String finalPath = filePath.toLowerCase().endsWith(format.getExtension())
                ? filePath
                : filePath + format.getExtension();

        ImportData dataToExport = collectAllData();

        DataExporter exporter = exporters.stream()
                .filter(e -> e.getSupportedFormat() == format)
                .findFirst()
                .orElseThrow(() -> new ValidationException("Экспортер для " + format.getLabel() + " не найден"));

        exporter.exportData(finalPath);
        console.printSuccess("Данные успешно экспортированы в файл: " + finalPath);
    }

    private ImportData collectAllData() {
        ImportData data = new ImportData();
        data.setAccounts(accountFacade.getAllAccounts());
        data.setCategories(categoryFacade.getAllCategories());
        data.setOperations(operationFacade.getAllOperations());
        return data;
    }

}