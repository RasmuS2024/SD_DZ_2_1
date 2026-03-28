package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.model.BankAccount;

public class ShowOperationsCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;
    private final DisplayHelper display;

    public ShowOperationsCommand(FacadeContext facades, ConsoleHelper console, DisplayHelper display) {
        this.facades = facades;
        this.console = console;
        this.display = display;
    }

    @Override
    public void execute() {
        Long accountId = console.readLong("ID счета: ");

        BankAccount account = facades.accountFacade().getAccount(accountId);
        display.showOperations(account, facades.operationFacade().getAccountOperations(accountId));
    }

    @Override
    public String getLabel() {
        return "Показать операции счета";
    }

    @Override
    public int getOrder() {
        return 7;
    }
}

