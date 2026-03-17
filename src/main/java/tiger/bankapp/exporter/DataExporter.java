package tiger.bankapp.exporter;

import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.enums.ImportFormat;

public abstract class DataExporter {
    protected final AccountFacade accountFacade;
    protected final CategoryFacade categoryFacade;
    protected final OperationFacade operationFacade;

    protected DataExporter(AccountFacade accountFacade,
                           CategoryFacade categoryFacade,
                           OperationFacade operationFacade) {
        this.accountFacade = accountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
    }

    /**
     * Шаблонный метод для экспорта
     * @return
     */
    public final void exportData(String filePath) {
        System.out.println("Экспорт в файл: " + filePath);

        // 1. Получение данных (общее)
        ImportData data = loadData();

        // 2. Сериализация (разная для форматов)
        serializeData(data, filePath);

        System.out.println("Экспорт завершён.");
    }

    protected abstract void serializeData(ImportData data, String filePath);

    /**
     * Общий метод загрузки данных
     */
    private ImportData loadData() {
        ImportData data = new ImportData();

        data.setAccounts(accountFacade.getAllAccounts());
        data.setCategories(categoryFacade.getAllCategories());
        data.setOperations(operationFacade.getAllOperations());

        return data;
    }

    public abstract ImportFormat getSupportedFormat();

}
