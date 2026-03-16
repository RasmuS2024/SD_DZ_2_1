package tiger.bankapp.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tiger.bankapp.exceptions.ImportException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;
import java.io.IOException;

@Component
public class JsonDataImporter extends DataImporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonDataImporter(AccountFacade accountFacade, CategoryFacade categoryFacade, OperationFacade operationFacade) {
        super(accountFacade, categoryFacade, operationFacade);
    }

    @Override
    protected ImportData parseFile(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath), ImportData.class);
        } catch (IOException e) {
            throw new ImportException("Ошибка парсинга JSON: " + e.getMessage());
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.JSON;
    }
}