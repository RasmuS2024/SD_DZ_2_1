package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.helpers.ConsoleHelper;

import java.time.LocalDateTime;

public class DifferenceForPeriodCommand implements Command {
    private final FacadeContext facades;
    private final ConsoleHelper console;

    public DifferenceForPeriodCommand(FacadeContext facades, ConsoleHelper console) {
        this.facades = facades;
        this.console = console;
    }

    @Override
    public void execute() {
        console.printMessage("\nВведите даты в формате ДД.ММ.ГГГГ");

        LocalDateTime from = parseDate(console.readString("Начальная дата: "));
        LocalDateTime to = parseDate(console.readString("Конечная дата: ")).plusDays(1).minusNanos(1);

        facades.analyticsFacade().printBalanceForPeriod(from, to);
    }

    private LocalDateTime parseDate(String dateStr) {
        try {
            String[] parts = dateStr.split("\\.");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return LocalDateTime.of(year, month, day, 0, 0);
        } catch (Exception e) {
            throw new ValidationException("Неверный формат даты. Используйте ДД.ММ.ГГГГ");
        }
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

