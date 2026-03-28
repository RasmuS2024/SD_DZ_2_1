package tiger.bankapp.importer;

import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.ImportException;
import tiger.bankapp.factory.AccountFactory;
import tiger.bankapp.factory.CategoryFactory;
import tiger.bankapp.factory.OperationFactory;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.ImportFormat;
import tiger.bankapp.model.enums.OperationType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;

import static tiger.bankapp.config.ImportExportConfig.DATE_FORMATTER;

public class CsvDataImporter extends DataImporter {
    private final AccountFactory accountFactory;
    private final CategoryFactory categoryFactory;
    private final OperationFactory operationFactory;

    public CsvDataImporter(FacadeContext facades,
                           AccountFactory accountFactory,
                           CategoryFactory categoryFactory,
                           OperationFactory operationFactory) {
        super(facades);
        this.accountFactory = accountFactory;
        this.categoryFactory = categoryFactory;
        this.operationFactory = operationFactory;
    }

    @Override
    protected ImportData parseFile(String filePath) {
        ImportData data = new ImportData();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (shouldSkipLine(line)) continue;
                processLine(line, data);
            }
            return data;
        } catch (Exception e) {
            throw new ImportException("Ошибка парсинга CSV: " + e.getMessage(), e);
        }
    }

    private boolean shouldSkipLine(String line) {
        return line.isBlank() || line.startsWith("#");
    }

    private void processLine(String line, ImportData data) {
        String[] parts = line.split(";");
        if (parts.length < 2) return;
        String recordType = parts[0].toUpperCase();

        switch (recordType) {
            case "ACCOUNT" -> processAccount(parts, data);
            case "CATEGORY" -> processCategory(parts, data);
            case "OPERATION" -> processOperation(parts, data);
        }
    }

    private void processAccount(String[] parts, ImportData data) {
        if (parts.length < 3) return;
        BankAccount account = accountFactory.createAccountWithId(
                Long.parseLong(parts[1]),
                parts[2]
        );
        if (parts.length > 3 && !parts[3].isEmpty()) {
            account.deposit(Double.parseDouble(parts[3].replace(",", ".")));
        }
        data.getAccounts().add(account);
    }

    private void processCategory(String[] parts, ImportData data) {
        if (parts.length < 4) return;
        Category category = categoryFactory.createCategory(
                Integer.parseInt(parts[1]),
                parts[2],
                parts[3]
        );
        data.getCategories().add(category);
    }

    private void processOperation(String[] parts, ImportData data) {
        if (parts.length < 6) return;
        Operation op = operationFactory.createOperationWithAllFields(
                Integer.parseInt(parts[1]),
                OperationType.valueOf(parts[2].toUpperCase()),
                Long.parseLong(parts[3]),
                Double.parseDouble(parts[4]),
                LocalDateTime.parse(parts[5], DATE_FORMATTER),
                parts.length > 6 ? parts[6] : "",
                parts.length > 7 && !parts[7].isEmpty() ? Integer.parseInt(parts[7]) : null
        );
        data.getOperations().add(op);
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.CSV;
    }
}
