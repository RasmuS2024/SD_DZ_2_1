package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.DisplayHelper;

public class ShowCategoriesCommand implements Command {
    private final FacadeContext facades;
    private final DisplayHelper display;

    public ShowCategoriesCommand(FacadeContext facades, DisplayHelper display) {
        this.facades = facades;
        this.display = display;
    }

    @Override
    public void execute() {
        display.showCategories(facades.categoryFacade().getIncomeCategories(),
                facades.categoryFacade().getExpenseCategories());
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

