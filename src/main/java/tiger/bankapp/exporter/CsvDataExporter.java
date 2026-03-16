package tiger.bankapp.exporter;

import org.springframework.stereotype.Component;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

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
            for (var acc : data.getAccounts()) {
                writer.println("ACCOUNT;" + acc.getId() + ";" + acc.getName() + ";" +
                        String.format("%.2f", acc.getBalance()));
            }

            // Экспорт категорий
            for (var cat : data.getCategories()) {
                writer.println("CATEGORY;" + cat.getId() + ";" + cat.getType() + ";" + cat.getName());
            }

            // Экспорт операций
            for (var op : data.getOperations()) {
                String formattedDate = op.getDate() != null
                        ? op.getDate().format(DATE_FORMATTER)
                        : "";

                writer.printf("OPERATION;%d;%s;%d;%.2f;%s;%s;%d%n",
                        op.getId(),
                        op.getType(),
                        op.getBankAccountId(),
                        op.getAmount(),
                        formattedDate,
                        op.getDescription() != null ? op.getDescription() : "",
                        op.getCategoryId() != null ? op.getCategoryId() : 0
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