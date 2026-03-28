package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.CommandHandler;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.enums.ImportFormat;
import java.util.Arrays;

public class ExportDataCommand implements Command {

    private final CommandHandler handler;
    private final ConsoleHelper console;

    public ExportDataCommand(CommandHandler handler, ConsoleHelper console) {
        this.handler = handler;
        this.console = console;
    }

    @Override
    public void execute() {
        ImportFormat[] formats = ImportFormat.values();
        String[] menuItems = Arrays.stream(formats)
                .map(ImportFormat::getLabel)
                .toArray(String[]::new);

        console.printMenu("ВЫБОР ФОРМАТА ДЛЯ ЭКСПОРТА", menuItems);
        int choice = console.readInt("Выберите номер формата (0 для отмены): ");

        if (choice < 1 || choice > formats.length) {
            System.out.println("Отмена экспорта.");
            return;
        }

        ImportFormat format = formats[choice - 1];

        String path = console.readString("Введите имя файла для сохранения: ");

        handler.handleExport(path, format);
    }

    @Override
    public String getLabel() {
        return "ЭКСПОРТ данных (JSON/YAML/CSV)";
    }

    @Override
    public int getOrder() {
        return 17;
    }
}
