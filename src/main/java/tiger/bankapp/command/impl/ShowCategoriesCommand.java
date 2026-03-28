package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class ShowCategoriesCommand implements Command {
    private final CommandHandler handler;

    public ShowCategoriesCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleShowCategories();
    }

    @Override
    public String getLabel() {
        return "Показать все категории";
    }

    @Override
    public int getOrder() {
        return 6;
    }
}

