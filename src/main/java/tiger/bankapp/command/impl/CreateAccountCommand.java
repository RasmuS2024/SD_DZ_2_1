package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.BankAccount;

public class CreateAccountCommand implements Command {

    private final FacadeContext facades;
    private final ConsoleHelper console;

    public CreateAccountCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        String name = console.readString("Название счета: ");
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Название счета не может быть пустым");
        }

        BankAccount account = facades.accountFacade().createAccount(name);
        console.printSuccess("Счет успешно создан: " + account);
    }

    @Override
    public String getLabel() {
        return "Создать счет";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}


