package tiger.bankapp.exporter;

import org.springframework.stereotype.Component;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class CsvDataExporter extends DataExporter {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public CsvDataExporter(AccountFacade accountFacade,
                           CategoryFacade categoryFacade,
                           OperationFacade operationFacade) {
        super(accountFacade, categoryFacade, operationFacade);
    }

    @Override
    protected void serializeData(ImportData data, String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath), StandardCharsets.UTF_8)) {

            // Экспорт счетов
            for (BankAccount bankAccount : data.getAccounts()) {
                String balanceStr = String.format(Locale.US, "%.2f", bankAccount.getBalance());
                writer.println("ACCOUNT;" + bankAccount.getId() + ";" + bankAccount.getName() + ";" + balanceStr);
            }

            // Экспорт категорий
            for (Category category : data.getCategories()) {
                writer.println("CATEGORY;" + category.getId() + ";" + category.getType() + ";" + category.getName());
            }

            // Экспорт операций
            for (Operation operation : data.getOperations()) {
                String formattedDate = operation.getDate() != null
                        ? operation.getDate().format(DATE_FORMATTER)
                        : "";

                writer.printf("OPERATION;%d;%s;%d;%.2f;%s;%s;%d%n",
                        operation.getId(),
                        operation.getType(),
                        operation.getBankAccountId(),
                        operation.getAmount(),
                        formattedDate,
                        operation.getDescription() != null ? operation.getDescription() : "",
                        operation.getCategoryId() != null ? operation.getCategoryId() : 0
                );
            }

            writer.flush();
        } catch (IOException e) {
            throw new BankingException("Ошибка записи CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.CSV;
    }
}