package tiger.bankapp.exporter;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import tiger.bankapp.exceptions.BankingException;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.enums.ImportFormat;

import java.io.FileWriter;
import java.io.IOException;

public class YamlDataExporter extends DataExporter {

    private final Yaml yaml;

    public YamlDataExporter(AccountFacade accountFacade,
                            CategoryFacade categoryFacade,
                            OperationFacade operationFacade) {
        super(accountFacade, categoryFacade, operationFacade);

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        this.yaml = new Yaml(options);
    }

    @Override
    protected void serializeData(ImportData data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            yaml.dump(data, writer);
        } catch (IOException e) {
            throw new BankingException("Ошибка записи YAML: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportFormat getSupportedFormat() {
        return ImportFormat.YAML;
    }
}
