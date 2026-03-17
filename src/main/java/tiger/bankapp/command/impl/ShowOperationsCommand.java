package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

@Component
public class ShowOperationsCommand implements Command {
    private final CommandHandler handler;

    public ShowOperationsCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleShowOperations();
    }

    @Override
    public String getLabel() {
        return "Показать операции счета";
    }

    @Override
    public int getOrder() {
        return 7;
    }
}

