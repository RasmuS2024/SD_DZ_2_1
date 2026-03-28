package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.DisplayHelper;

public class ShowAccountsCommand implements Command {
    private final FacadeContext facades;
    private final DisplayHelper display;

    public ShowAccountsCommand(FacadeContext facades, DisplayHelper display) {
        this.facades = facades;
        this.display = display;
    }

    @Override
    public void execute() {
        display.showAccounts(facades.accountFacade().getAllAccounts());
    }

    @Override
    public String getLabel() {
        return "Показать все счета";
    }

    @Override
    public int getOrder() {
        return 5;
    }
}

