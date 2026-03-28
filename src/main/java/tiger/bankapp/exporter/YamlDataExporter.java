package tiger.bankapp.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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

public class YamlDataExporter extends DataExporter {

    private final ObjectMapper mapper;

    public YamlDataExporter(FacadeContext facades) {
        super(facades);

        this.mapper = new ObjectMapper(new YAMLFactory());
        this.mapper.registerModule(new JavaTimeModule());

        // Отключаем запись дат в виде чисел
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Включаем красивый отступ
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    protected void serializeData(ImportData data, String filePath) {
        try {
            mapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new BankingException("Ошибка записи YAML: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.YAML;
    }
}
