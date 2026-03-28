package tiger.bankapp.importer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import tiger.bankapp.exceptions.ImportException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.File;

public class YamlDataImporter extends DataImporter {

    private final ObjectMapper mapper;

    public YamlDataImporter(
            AccountFacade accountFacade,
            CategoryFacade categoryFacade,
            OperationFacade operationFacade
    ) {
        super(accountFacade, categoryFacade, operationFacade);

        this.mapper = new ObjectMapper(new YAMLFactory());

        this.mapper.registerModule(new JavaTimeModule());

        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    protected ImportData parseFile(String filePath) {
        try {
            System.out.println("Парсим YAML файл: " + filePath);
            return mapper.readValue(new File(filePath), ImportData.class);
        } catch (Exception e) {
            throw new ImportException("Ошибка парсинга YAML: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.YAML;
    }
}
