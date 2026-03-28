package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class CreateAccountCommand implements Command {

    private final CommandHandler handler;

    public CreateAccountCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleCreateAccount();
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


