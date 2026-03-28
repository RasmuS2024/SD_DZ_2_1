package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class EditOperationCommand implements Command {
    private final CommandHandler handler;

    public EditOperationCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleEditOperation();
    }

    @Override
    public String getLabel() {
        return "Редактировать операцию";
    }

    @Override
    public int getOrder() {
        return 13;
    }
}

