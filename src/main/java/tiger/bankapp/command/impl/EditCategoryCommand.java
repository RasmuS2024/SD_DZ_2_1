package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.Category;

public class EditCategoryCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public EditCategoryCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        Integer id = console.readInt("ID категории для редактирования: ");

        Category category = facades.categoryFacade().getCategory(id);
        console.printMessage("Текущая категория: " + category);

        String type = console.readString("Новый тип (INCOME/EXPENSE): ");
        String name = console.readString("Новое название: ");

        facades.categoryFacade().updateCategory(id, type, name);
        console.printSuccess("Категория обновлена");
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

