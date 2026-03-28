package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class DeleteOperationCommand implements Command {
    private final CommandHandler handler;

    public DeleteOperationCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleDeleteOperation();
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

