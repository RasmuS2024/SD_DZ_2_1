package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

@Component
public class DifferenceForPeriodCommand implements Command {
    private final CommandHandler handler;

    public DifferenceForPeriodCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleDifferenceForPeriod();
    }

    @Override
    public String getLabel() {
        return "Разница доходов и расходов за период";
    }

    @Override
    public int getOrder() {
        return 14;
    }
}

