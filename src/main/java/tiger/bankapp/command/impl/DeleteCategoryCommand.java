package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

@Component
public class DeleteCategoryCommand implements Command {
    private final CommandHandler handler;

    public DeleteCategoryCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleDeleteCategory();
    }

    @Override
    public String getLabel() {
        return "Удалить категорию";
    }

    @Override
    public int getOrder() {
        return 10;
    }
}

