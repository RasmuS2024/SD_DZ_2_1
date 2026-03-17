package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

@Component
public class CreateCategoryCommand implements Command {
    private final CommandHandler handler;

    public CreateCategoryCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleCreateCategory();
    }

    @Override
    public String getLabel() {
        return "Создать категорию";
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

