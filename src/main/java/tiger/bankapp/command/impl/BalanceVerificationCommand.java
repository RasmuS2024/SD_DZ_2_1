package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

@Component
public class BalanceVerificationCommand implements Command {
    private final CommandHandler handler;

    public BalanceVerificationCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleBalanceVerification();
    }

    @Override
    public String getLabel() {
        return "Проверка и исправление балансов";
    }

    @Override
    public int getOrder() {
        return 18;
    }
}