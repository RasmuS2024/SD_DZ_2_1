package tiger.bankapp.controller;

import org.springframework.stereotype.Component;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.service.FinanceFacade;

@Component
public class MenuController {
    private final ConsoleHelper console;
    private final CommandHandler handler;

    public MenuController(FinanceFacade finance) {
        this.console = new ConsoleHelper();
        this.handler = new CommandHandler(finance, console, new DisplayHelper());
    }

    public void start() {
        while (true) {
            try {
                if (!processChoice(showMainMenu())) break;
            } catch (Exception e) {
                console.printError(e.getMessage());
            }
        }
        console.close();
    }

    private int showMainMenu() {
        String[] items = {
                "Создать счет", "Создать категорию", "Добавить доход", "Добавить расход",
                "Показать все счета", "Показать все категории", "Показать операции счета",
                "Удалить операцию", "Удалить счет", "Удалить категорию", "Редактировать счет",
                "Редактировать категорию", "Редактировать операцию", "Разница за период"
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
            case 0: return false;
            default: console.printError("Неверный выбор");
        }
        console.waitForEnter();
        return true;
    }
}