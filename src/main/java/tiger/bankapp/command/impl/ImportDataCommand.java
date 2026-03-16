package tiger.bankapp.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.importer.DataImporter;
import tiger.bankapp.model.enums.ImportFormat;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImportDataCommand implements Command {
    private final List<DataImporter> importers; // Внедряем все импортеры
    private final ConsoleHelper console;

    @Override
    public void execute() {
        String path = console.readString("Введите путь к файлу: ");
        ImportFormat format = ImportFormat.getByFileName(path);

        DataImporter importer = importers.stream()
                .filter(i -> i.getSupportedFormat() == format)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Формат не поддерживается"));

        importer.importData(path);
        System.out.println("Данные импортированы!");
    }

    @Override
    public String getDescription() { return "Импорт данных (JSON/YAML/CSV)"; }
}

