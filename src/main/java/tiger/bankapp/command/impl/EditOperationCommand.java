package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.Operation;

public class EditOperationCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public EditOperationCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        Integer id = console.readInt("ID операции для редактирования: ");

        Operation operation = facades.operationFacade().getOperation(id);
        console.printMessage("Текущее описание: " + operation.getDescription());

        String newDesc = console.readString("Новое описание: ");

        Integer newCatId = null;
        if (operation.getCategoryId() != null) {
            console.printMessage("Текущая категория ID: " + operation.getCategoryId());
            String catInput = console.readString("Новый ID категории (Enter - оставить): ");
            if (!catInput.isEmpty()) {
                newCatId = Integer.parseInt(catInput);
            }
        }

        facades.operationFacade().updateOperation(id, newDesc, newCatId);
        console.printSuccess("Операция обновлена");
    }

    @Override
    public String getLabel() {
        return "Редактировать операцию";
    }

    @Override
    public int getOrder() {
        return 13;
    }
}

