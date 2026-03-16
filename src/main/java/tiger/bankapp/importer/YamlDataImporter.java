package tiger.bankapp.importer;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import tiger.bankapp.exceptions.ImportException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.FileInputStream;


@Component
public class YamlDataImporter extends DataImporter {

    private final Yaml yaml;

    public YamlDataImporter(
            AccountFacade accountFacade,
            CategoryFacade categoryFacade,
            OperationFacade operationFacade
    ) {
        super(accountFacade, categoryFacade, operationFacade);
        LoaderOptions options = new LoaderOptions();
        this.yaml = new Yaml(new Constructor(ImportData.class, options));
    }

    @Override
    protected ImportData parseFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println("Парсим YAML файл: " + filePath);
            return yaml.loadAs(fis, ImportData.class);
        } catch (Exception e) {
            throw new ImportException("Ошибка парсинга YAML: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.YAML;
    }
}