package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class AddExpenseCommand implements Command {
    private final CommandHandler handler;

    public AddExpenseCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleAddExpense();
    }

    @Override
    public String getLabel() {
        return "Добавить расход";
    }

    @Override
    public int getOrder() {
        return 4;
    }
}

