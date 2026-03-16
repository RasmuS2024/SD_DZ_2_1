package tiger.bankapp.importer;

import lombok.Data;
import lombok.NoArgsConstructor;
import tiger.bankapp.model.BankAccount;
import tiger.bankapp.model.Category;
import tiger.bankapp.model.Operation;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ImportData {
    private List<BankAccount> accounts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();
}