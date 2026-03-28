package tiger.bankapp.exporter;

import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static tiger.bankapp.config.ImportExportConfig.DATE_FORMATTER;
import static tiger.bankapp.config.ImportExportConfig.NUMBER_LOCALE;

public class CsvDataExporter extends DataExporter {

    public CsvDataExporter(FacadeContext facades) {
        super(facades);
    }

    @Override
    protected void serializeData(ImportData data, String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath), StandardCharsets.UTF_8)) {

            // Экспорт счетов
            for (BankAccount account : data.getAccounts()) {
                writer.printf(NUMBER_LOCALE, "ACCOUNT;%d;%s;%.2f%n",
                        account.getId(), account.getName(), account.getBalance());
            }

            // Экспорт категорий
            for (Category cat : data.getCategories()) {
                writer.printf("CATEGORY;%d;%s;%s%n",
                        cat.getId(), cat.getType(), cat.getName());
            }

            // Экспорт операций
            for (Operation op : data.getOperations()) {
                String dateStr = op.getDate() != null ? op.getDate().format(DATE_FORMATTER) : "";

                Object catId = op.getCategoryId() != null ? op.getCategoryId() : "";

                writer.printf(NUMBER_LOCALE, "OPERATION;%d;%s;%d;%.2f;%s;%s;%s%n",
                        op.getId(), op.getType(), op.getBankAccountId(),
                        op.getAmount(), dateStr, op.getDescription(), catId
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