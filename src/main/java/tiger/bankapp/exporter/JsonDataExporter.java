package tiger.bankapp.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;
import java.io.IOException;

@Component
public class JsonDataExporter extends DataExporter {
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonDataExporter(AccountFacade accountFacade, CategoryFacade categoryFacade, OperationFacade operationFacade) {
        super(accountFacade, categoryFacade, operationFacade);

        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void serializeData(ImportData data, String filePath) {
        try {
            mapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new BankingException("Ошибка записи JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.JSON;
    }
}
