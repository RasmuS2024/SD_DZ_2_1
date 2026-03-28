package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.BankAccount;

public class DeleteAccountCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public DeleteAccountCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        Long accountId = console.readLong("ID счета: ");

        BankAccount account = facades.accountFacade().getAccount(accountId);
        if (account.getBalance() != 0) {
            throw new ValidationException("Нельзя удалить счет с ненулевым балансом: " + account.getBalance());
        }

        facades.accountFacade().deleteAccount(accountId);
        console.printSuccess("Счет удален");
    }

    @Override
    public String getLabel() {
        return "Удалить счет";
    }

    @Override
    public int getOrder() {
        return 9;
    }
}

