package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Operation;

public class AddIncomeCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;
    private final DisplayHelper display;

    public AddIncomeCommand(FacadeContext facades, ConsoleHelper console, DisplayHelper display) {
        this.facades = facades;
        this.console = console;
        this.display = display;
    }

    @Override
    public void execute() {
        Long accountId = console.readLong("ID счета: ");
        int amount = console.readInt("Сумма: ");

        display.showCategoriesForSelection(facades.categoryFacade().getIncomeCategories(), "доходов");
        Integer categoryId = console.readInt("ID категории: ");
        String description = console.readString("Описание: ");

        Operation operation = facades.operationFacade().addIncome(accountId, amount, categoryId, description);
        console.printSuccess("Доход добавлен. " + operation);
        showAccountBalance(accountId);
    }

    private void showAccountBalance(Long accountId) {
        BankAccount account = facades.accountFacade().getAccount(accountId);
        console.printMessage("Новый баланс: " + account.getBalance());
    }

    @Override
    public String getLabel() {
        return "Добавить доход";
    }

    @Override
    public int getOrder() {
        return 3;
    }
}

