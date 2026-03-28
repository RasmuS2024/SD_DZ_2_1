package tiger.bankapp.importer;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import tiger.bankapp.exceptions.ImportException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.FileInputStream;
import java.io.InputStream;

public class YamlDataImporter extends DataImporter {

    private final Yaml yaml;

    public YamlDataImporter(
            AccountFacade accountFacade,
            CategoryFacade categoryFacade,
            OperationFacade operationFacade
    ) {
        super(accountFacade, categoryFacade, operationFacade);

        LoaderOptions options = new LoaderOptions();
        Constructor constructor = new Constructor(ImportData.class, options);

        this.yaml = new Yaml(constructor);
    }

    @Override
    protected ImportData parseFile(String filePath) {
        // Используем try-with-resources для автоматического закрытия файла
        try (InputStream inputStream = new FileInputStream(filePath)) {
            System.out.println("Парсим YAML файл: " + filePath);

            ImportData data = yaml.loadAs(inputStream, ImportData.class);

            if (data == null) {
                throw new ImportException("Файл пуст или имеет неверный формат");
            }

            return data;
        } catch (Exception e) {
            throw new ImportException("Ошибка парсинга YAML: " + e.getMessage());
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.YAML;
    }
}
