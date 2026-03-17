package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

@Component
public class EditCategoryCommand implements Command {
    private final CommandHandler handler;

    public EditCategoryCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleEditCategory();
    }

    @Override
    public String getLabel() {
        return "Редактировать категорию";
    }

    @Override
    public int getOrder() {
        return 12;
    }
}

