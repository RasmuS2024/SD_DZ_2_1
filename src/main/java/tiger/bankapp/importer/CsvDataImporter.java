package tiger.bankapp.importer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiger.bankapp.exceptions.ImportException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.factory.AccountFactory;
import tiger.bankapp.factory.CategoryFactory;
import tiger.bankapp.factory.OperationFactory;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CsvDataImporter extends DataImporter {
    private final AccountFactory accountFactory;
    private final CategoryFactory categoryFactory;
    private final OperationFactory operationFactory;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Autowired
    public CsvDataImporter(AccountFacade accountFacade,
                           CategoryFacade categoryFacade,
                           OperationFacade operationFacade,
                           AccountFactory accountFactory,
                           CategoryFactory categoryFactory,
                           OperationFactory operationFactory) {
        super(accountFacade, categoryFacade, operationFacade);
        this.accountFactory = accountFactory;
        this.categoryFactory = categoryFactory;
        this.operationFactory = operationFactory;
    }

    @Override
    protected ImportData parseFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.printf("Парсим CSV файл: %s%n", filePath);
            ImportData data = new ImportData();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;

                String[] parts = line.split(";");
                if (parts.length < 2) continue;

                String recordType = parts[0].toUpperCase();

                switch (recordType) {
                    case "ACCOUNT" -> {
                        if (parts.length < 4) continue;
                        BankAccount account = accountFactory.createAccountWithId(
                                Long.parseLong(parts[1]),
                                parts[2]
                        );
                        if (parts.length > 3 && !parts[3].isEmpty()) {
                            double balance = Double.parseDouble(parts[3].replace(",", "."));
                            account.deposit(balance);
                        }
                        data.getAccounts().add(account);
                    }

                    case "CATEGORY" -> {
                        if (parts.length < 4) continue;
                        Category category = categoryFactory.createCategory(
                                Integer.parseInt(parts[1]),
                                parts[2],
                                parts[3]
                        );
                        data.getCategories().add(category);
                    }

                    case "OPERATION" -> {
                        if (parts.length < 8) continue;

                        Integer id = Integer.parseInt(parts[1]);
                        String type = parts[2];
                        Long accountId = Long.parseLong(parts[3]);
                        double amount = Double.parseDouble(parts[4]);

                        LocalDateTime date = LocalDateTime.parse(parts[5], DATE_FORMATTER);

                        String description = parts.length > 6 ? parts[6] : "";

                        Integer categoryId = parts.length > 7 && !parts[7].isEmpty()
                                ? Integer.parseInt(parts[7])
                                : null;

                        Operation op = operationFactory.createOperationWithAllFields(
                                id, type, accountId, amount, date, description, categoryId
                        );
                        data.getOperations().add(op);
                    }

                    default -> System.out.printf("Неизвестный тип записи: %s%n", recordType);
                }
            }
            return data;
        } catch (Exception e) {
            throw new ImportException("Ошибка парсинга CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.CSV;
    }
}