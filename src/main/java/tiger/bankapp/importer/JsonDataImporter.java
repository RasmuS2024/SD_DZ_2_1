package tiger.bankapp.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import tiger.bankapp.exceptions.ImportException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;
import java.io.IOException;

public class JsonDataImporter extends DataImporter {
    private final ObjectMapper objectMapper;

    public JsonDataImporter(AccountFacade accountFacade,
                            CategoryFacade categoryFacade,
                            OperationFacade operationFacade,
                            ObjectMapper objectMapper) { // Принимаем настроенный извне
        super(accountFacade, categoryFacade, operationFacade);
        this.objectMapper = objectMapper;
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