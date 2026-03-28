package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;

public class CategoryReportCommand implements Command {

    private final CommandHandler handler;

    public CategoryReportCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.handleCategoryReport();
    }

    @Override
    public String getLabel() {
        return "Отчет по категориям (доходы/расходы)";
    }

    @Override
    public int getOrder() {
        return 15;
    }
}