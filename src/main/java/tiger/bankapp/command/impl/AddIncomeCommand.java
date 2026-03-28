package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class AddIncomeCommand implements Command {
    private final CommandHandler handler;

    public AddIncomeCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleAddIncome();
    }

    @Override
    public String getLabel() {
        return "Добавить доход";
    }

    @Override
    public int getOrder() {
        return 3;
    }
}

