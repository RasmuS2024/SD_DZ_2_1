package tiger.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.helpers.ConsoleHelper;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class MenuController {
    private final ConsoleHelper console;
    private final Map<Integer, Command> commands;

    @Autowired
    public MenuController(ConsoleHelper console, List<Command> commandList) {
        this.console = console;
        this.commands = commandList.stream()
                .sorted(Comparator.comparingInt(Command::getOrder))
                .collect(Collectors.toMap(
                        Command::getOrder,
                        c -> c,
                        (v1, v2) -> v1,
                        TreeMap::new
                ));
    }

    public void start() {
        console.printMessage("Добро пожаловать в ТигрБанк!");

        while (true) {
            showMenu();
            int choice = console.readInt("Выберите действие (0 для выхода): ");

            if (!processChoice(choice)) {
                break;
            }

            console.waitForEnter();
        }

        console.printMessage("Программа завершена. До свидания!");
    }

    private boolean processChoice(int choice) {
        if (choice == 0) {
            return false;
        }

        Command command = commands.get(choice);
        if (command == null) {
            console.printError("Неверный выбор. Пожалуйста, выберите пункт из списка.");
            return true;
        }

        try {
            command.execute();
        } catch (BankingException e) {
            console.printError("Ошибка банковских операций: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Критический сбой: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    private void showMenu() {
        String[] labels = commands.values().stream()
                .map(Command::getLabel)
                .toArray(String[]::new);

        console.printMenu("ГЛАВНОЕ МЕНЮ", labels);
    }
}
