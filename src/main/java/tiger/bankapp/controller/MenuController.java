package tiger.bankapp.controller;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.enums.ImportFormat;

import java.util.Arrays;

@Component
public class MenuController {
    private final ConsoleHelper console;
    private final CommandHandler handler;

    @Autowired
    public MenuController(CommandHandler handler) {
        this.console = new ConsoleHelper();
        this.handler = handler;
    }

    public void start() {
        console.printMessage("Добро пожаловать в ТигрБанк!");

        while (true) {
            try {
                if (!processChoice(showMainMenu())) {
                    break;
                }
            } catch (BankingException e) {
                console.printError("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                console.printError("Непредвиденная ошибка: " + e.getMessage());
            }
        }

        console.close();
    }

    private int showMainMenu() {
        String[] items = {
                "Создать счет",
                "Создать категорию",
                "Добавить доход",
                "Добавить расход",
                "Показать все счета",
                "Показать все категории",
                "Показать операции счета",
                "Удалить операцию",
                "Удалить счет",
                "Удалить категорию",
                "Редактировать счет",
                "Редактировать категорию",
                "Редактировать операцию",
                "Разница доходов и расходов за период",
                "ИМПОРТ данных (JSON/YAML/CSV)",
                "ЭКСПОРТ данных (JSON/YAML/CSV)"
        };

        console.printMenu("ГЛАВНОЕ МЕНЮ", items);
        return console.readInt("Выберите действие: ");
    }

    private boolean processChoice(int choice) {
        switch (choice) {
            case 1: handler.handleCreateAccount(); break;
            case 2: handler.handleCreateCategory(); break;
            case 3: handler.handleAddIncome(); break;
            case 4: handler.handleAddExpense(); break;
            case 5: handler.handleShowAccounts(); break;
            case 6: handler.handleShowCategories(); break;
            case 7: handler.handleShowOperations(); break;
            case 8: handler.handleDeleteOperation(); break;
            case 9: handler.handleDeleteAccount(); break;
            case 10: handler.handleDeleteCategory(); break;
            case 11: handler.handleEditAccount(); break;
            case 12: handler.handleEditCategory(); break;
            case 13: handler.handleEditOperation(); break;
            case 14: handler.handleDifferenceForPeriod(); break;
            case 15: showImportMenu(); break;
            case 16: showExportMenu(); break;
            case 0: return false;
            default: console.printError("Неверный выбор. Пожалуйста, выберите пункт от 0 до 16");
        }
        console.waitForEnter();
        return true;
    }

    private void showImportMenu() {
        String path = console.readString("Введите путь к файлу (например, data.csv): ");

        try {
            // Просто передаем путь, Handler сам определит формат по расширению
            handler.handleImport(path);
            console.printSuccess("Файл успешно обработан!");
        } catch (Exception e) {
            console.printError("Ошибка: " + e.getMessage());
        }
    }

    private void showExportMenu() {
        ImportFormat format = selectFormat();
        if (format == null) {
            console.printError("Неверный выбор или отмена.");
            return;
        }

        String path = console.readString("Введите имя файла для сохранения: ");
        handler.handleExport(path, format); // Передаем объект Enum
    }

    private ImportFormat selectFormat() {
        // Получаем список всех форматов из Enum динамически
        ImportFormat[] formats = ImportFormat.values();

        // Формируем массив строк для вывода в консоль
        String[] menuItems = Arrays.stream(formats)
                .map(ImportFormat::getLabel)
                .toArray(String[]::new);

        console.printMenu("ВЫБОР ФОРМАТА ДЛЯ ЭКСПОРТА", menuItems);
        int choice = console.readInt("Выберите номер формата (0 для отмены): ");

        // Простая валидация выбора
        if (choice < 1 || choice > formats.length) {
            return null;
        }

        return formats[choice - 1];
    }
}