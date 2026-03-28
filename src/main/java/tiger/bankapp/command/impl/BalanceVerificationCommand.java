package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.BankAccount;

public class BalanceVerificationCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public BalanceVerificationCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        System.out.println("Выберите действие:");
        System.out.println("1. Проверить и исправить все счета");
        System.out.println("2. Проверить конкретный счет");

        int choice = console.readInt("Ваш выбор: ");

        switch (choice) {
            case 1 -> {
                int fixed = facades.analyticsFacade().verifyAndFixAllAccounts();
                if (fixed > 0) {
                    console.printSuccess("Проверка завершена. Исправлено счетов: " + fixed);
                } else {
                    console.printSuccess("Проверка завершена. Все балансы корректны.");
                }
            }
            case 2 -> {
                Long id = console.readLong("ID счета: ");

                BankAccount account = facades.accountFacade().getAccount(id);
                if (account == null) {
                    console.printError("Счет с ID " + id + " не найден");
                    return;
                }

                boolean wasFixed = facades.analyticsFacade().verifyAndFixAccountBalance(id);

                if (wasFixed) {
                    console.printSuccess("Баланс счета " + id + " был исправлен. Текущий баланс: " +
                            facades.accountFacade().getAccount(id).getBalance());
                } else {
                    console.printSuccess("Счет " + id + ": баланс корректен (" +
                            account.getBalance() + ")");
                }
            }
            default -> console.printError("Неверный выбор");
        }
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