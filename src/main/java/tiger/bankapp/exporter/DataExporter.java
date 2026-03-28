package tiger.bankapp.exporter;

import lombok.RequiredArgsConstructor;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.importer.ImportData;
import tiger.bankapp.model.enums.ImportFormat;

@RequiredArgsConstructor
public abstract class DataExporter {

    protected final FacadeContext facades;

    /**
     * Шаблонный метод для экспорта
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

        data.setAccounts(facades.accountFacade().getAllAccounts());
        data.setCategories(facades.categoryFacade().getAllCategories());
        data.setOperations(facades.operationFacade().getAllOperations());

        return data;
    }

    public abstract ImportFormat getSupportedFormat();

}
