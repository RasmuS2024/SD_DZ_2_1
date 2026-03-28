package tiger.bankapp.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;
import java.io.IOException;

public class JsonDataExporter extends DataExporter {
    private final ObjectMapper mapper;

    public JsonDataExporter(FacadeContext facades, ObjectMapper mapper) {
        super(facades);
        this.mapper = mapper;
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
