package tiger.bankapp.command.impl;

import tiger.bankapp.command.Command;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.ValidationException;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.importer.DataImporter;
import tiger.bankapp.model.enums.ImportFormat;

import java.util.List;

public class ImportDataCommand implements Command {

    private final FacadeContext facades;
    private final List<DataImporter> importers;
    private final ConsoleHelper console;

    public ImportDataCommand(FacadeContext facades, List<DataImporter> importers, ConsoleHelper console) {
        this.facades = facades;
        this.importers = importers;
        this.console = console;
    }

    @Override
    public void execute() {
        String filePath = console.readString("Введите путь к файлу (по умолчанию - data.csv (Enter)): ");
        // Если пользователь ничего не ввел, используем значение по умолчанию
        if (filePath == null || filePath.trim().isEmpty()) {
            filePath = "data.csv";
            console.printMessage("Используется файл по умолчанию: data.csv");
        }

        try {
            ImportFormat format = ImportFormat.getByFileName(filePath);

            DataImporter importer = importers.stream()
                    .filter(i -> i.getSupportedFormat() == format)
                    .findFirst()
                    .orElseThrow(() -> new ValidationException("Импортер для формата " + format.getLabel() + " не найден"));

            importer.importData(filePath);
            console.printSuccess("Данные из файла " + filePath + " успешно импортированы!");

        } catch (IllegalArgumentException e) {
            console.printError("Неподдерживаемый формат файла. Укажите файл с расширением .csv, .json или .yaml");
        } catch (Exception e) {
            console.printError("Ошибка при импорте: " + e.getMessage());
        }
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

