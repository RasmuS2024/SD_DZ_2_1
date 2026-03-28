package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.Category;

public class CreateCategoryCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public CreateCategoryCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        System.out.println("Выберите тип категории:");
        System.out.println("1. Доход");
        System.out.println("2. Расход");

        String type = switch (console.readInt("Ваш выбор: ")) {
            case 1 -> "INCOME";
            case 2 -> "EXPENSE";
            default -> {
                console.printError("Неверный выбор. Используется INCOME");
                yield "INCOME";
            }
        };

        String name = console.readString("Название категории: ");
        Category category = facades.categoryFacade().createCategory(type, name);
        console.printSuccess("Категория успешно создана: " + category);
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

