package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;

public class DeleteOperationCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public DeleteOperationCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        Integer operationId = console.readInt("ID операции: ");

        facades.operationFacade().deleteOperation(operationId);
        console.printSuccess("Операция удалена, баланс счета скорректирован");
    }

    @Override
    public String getLabel() {
        return "Удалить операцию";
    }

    @Override
    public int getOrder() {
        return 8;
    }
}

