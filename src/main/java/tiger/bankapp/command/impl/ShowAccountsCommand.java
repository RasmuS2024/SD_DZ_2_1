package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class ShowAccountsCommand implements Command {
    private final CommandHandler handler;

    public ShowAccountsCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleShowAccounts();
    }

    @Override
    public String getLabel() {
        return "Показать все счета";
    }

    @Override
    public int getOrder() {
        return 5;
    }
}

