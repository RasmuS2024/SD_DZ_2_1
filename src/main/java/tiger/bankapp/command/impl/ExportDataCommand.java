package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.exporter.DataExporter;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.model.enums.ImportFormat;

import java.util.Arrays;
import java.util.List;

public class ExportDataCommand implements Command {

    private final FacadeContext facades;
    private final List<DataExporter> exporters;
    private final ConsoleHelper console;

    public ExportDataCommand(FacadeContext facades, List<DataExporter> exporters, ConsoleHelper console) {
        this.facades = facades;
        this.exporters = exporters;
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

        String finalPath = path.toLowerCase().endsWith(format.getExtension())
                ? path
                : path + format.getExtension();

        DataExporter exporter = exporters.stream()
                .filter(e -> e.getSupportedFormat() == format)
                .findFirst()
                .orElseThrow(() -> new ValidationException("Экспортер для " + format.getLabel() + " не найден"));

        exporter.exportData(finalPath);
        console.printSuccess("Данные успешно экспортированы в файл: " + finalPath);
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
