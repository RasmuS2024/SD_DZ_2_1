package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

@Component
public class DeleteAccountCommand implements Command {
    private final CommandHandler handler;

    public DeleteAccountCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleDeleteAccount();
    }

    @Override
    public String getLabel() {
        return "Удалить счет";
    }

    @Override
    public int getOrder() {
        return 9;
    }
}

