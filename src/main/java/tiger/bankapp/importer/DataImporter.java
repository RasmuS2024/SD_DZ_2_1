package tiger.bankapp.importer;

import lombok.RequiredArgsConstructor;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.facade.CategoryFacade;
import tiger.bankapp.facade.OperationFacade;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.ImportFormat;

import java.util.List;

@RequiredArgsConstructor
public abstract class DataImporter {

    protected final AccountFacade accountFacade;
    protected final CategoryFacade categoryFacade;
    protected final OperationFacade operationFacade;

    /**
     *  Шаблонный метод импорта
     */
    public final void importData(String filePath) {
        System.out.println("Импорт из файла: " + filePath);

        ImportData parsed = parseFile(filePath);

        saveData(parsed);

        System.out.println("Импорт завершён.");
    }

    /**
     * Метод для вывода поддерживаемого формата
     */
    public abstract ImportFormat getSupportedFormat();

    protected abstract ImportData parseFile(String filePath);

    /**
     * Общий метод сохранения
      */
    protected void saveData(ImportData data) {
        List<Operation> sortedOperations = data.getOperations().stream()
                .sorted((op1, op2) -> op1.getDate().compareTo(op2.getDate()))
                .toList();

        for (BankAccount acc : data.getAccounts()) {
            accountFacade.createAccountWithBalance(
                    acc.getName(),
                    acc.getBalance()
            );
        }

        for (Category cat : data.getCategories()) {
            categoryFacade.createCategory(cat.getType(), cat.getName());
        }

        for (Operation op : sortedOperations) {
            if ("INCOME".equals(op.getType())) {
                operationFacade.importIncome(
                        op.getBankAccountId(),
                        op.getAmount(),
                        op.getCategoryId(),
                        op.getDescription(),
                        op.getDate()
                );
            } else {
                operationFacade.importExpense(
                        op.getBankAccountId(),
                        op.getAmount(),
                        op.getCategoryId(),
                        op.getDescription(),
                        op.getDate()
                );
            }
        }
    }
}