package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class EditAccountCommand implements Command {
    private final CommandHandler handler;

    public EditAccountCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleEditAccount();
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

