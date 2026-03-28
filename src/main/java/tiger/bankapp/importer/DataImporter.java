package tiger.bankapp.importer;

import lombok.RequiredArgsConstructor;
import tiger.bankapp.controller.FacadeContext;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;
import tiger.bankapp.model.enums.ImportFormat;

import java.util.List;

@RequiredArgsConstructor
public abstract class DataImporter {

    protected final FacadeContext facades;

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
            facades.accountFacade().createAccountWithBalance(
                    acc.getName(),
                    acc.getBalance()
            );
        }

        for (Category cat : data.getCategories()) {
            facades.categoryFacade().createCategory(String.valueOf(cat.getType()), cat.getName());
        }

        for (Operation op : sortedOperations) {
            if ("INCOME".equals(op.getType())) {
                facades.operationFacade().importIncome(
                        op.getBankAccountId(),
                        op.getAmount(),
                        op.getCategoryId(),
                        op.getDescription(),
                        op.getDate()
                );
            } else {
                facades.operationFacade().importExpense(
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