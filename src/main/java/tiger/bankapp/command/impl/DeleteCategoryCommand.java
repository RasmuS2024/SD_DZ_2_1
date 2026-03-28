package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;

public class DeleteCategoryCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public DeleteCategoryCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        Integer categoryId = console.readInt("ID категории: ");

        facades.categoryFacade().deleteCategory(categoryId);
        console.printSuccess("Категория удалена");
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

