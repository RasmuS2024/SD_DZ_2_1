package tiger.bankapp.command.impl;

import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;
import tiger.bankapp.helpers.ConsoleHelper;

@Component
public class ImportDataCommand implements Command {

    private final CommandHandler handler;
    private final ConsoleHelper console;

    public ImportDataCommand(CommandHandler handler, ConsoleHelper console) {
        this.handler = handler;
        this.console = console;
    }

    @Override
    public void execute() {
        String filePath = console.readString("Введите путь к файлу (по умолчанию - data.csv (Enter)): ");
        handler.handleImport(filePath);
    }

    @Override
    public String getLabel() {
        return "ИМПОРТ данных (JSON/YAML/CSV)";
    }

    @Override
    public int getOrder() {
        return 16;
    }
}

