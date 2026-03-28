package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.BankAccount;

public class EditAccountCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public EditAccountCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        Long id = console.readLong("ID счета для редактирования: ");

        BankAccount account = facades.accountFacade().getAccount(id);
        console.printMessage("Текущее название: " + account.getName());

        String newName = console.readString("Новое название: ");
        if (newName == null || newName.trim().isEmpty()) {
            throw new ValidationException("Название счета не может быть пустым");
        }

        facades.accountFacade().updateAccount(id, newName);
        console.printSuccess("Счет обновлен");
    }

    @Override
    public String getLabel() {
        return "Редактировать счет";
    }

    @Override
    public int getOrder() {
        return 11;
    }
}

