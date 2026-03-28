package tiger.bankapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import tiger.bankapp.command.Command;
import tiger.bankapp.command.TimedCommand;
import tiger.bankapp.command.impl.*;
import tiger.bankapp.facade.*;
import tiger.bankapp.factory.*;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.helpers.DisplayHelper;
import tiger.bankapp.importer.*;
import tiger.bankapp.exporter.*;
import tiger.bankapp.repository.*;
import tiger.bankapp.service.*;

import java.util.List;
import java.util.ArrayList;

public class AppContainer {

    // конструктор блокируется
    private AppContainer() {
        throw new IllegalStateException("Utility class");
    }

    public static MenuController assemble(ConsoleHelper console, ObjectMapper mapper) {
        var display = new DisplayHelper();

        var accFact = new AccountFactory();
        var catFact = new CategoryFactory();
        var opFact = new OperationFactory();

        var accRepo = new AccountRepository();
        var catRepo = new CategoryRepository(catFact);
        var opRepo = new OperationRepository();

        var accService = new AccountServiceImpl(accRepo, accFact);
        var catService = new CategoryServiceImpl(catRepo, catFact);
        var opService = new OperationServiceImpl(opRepo, accService, opFact);

        // единый контекст для фасадов
        var facades = new FacadeContext(
                new AccountFacade(accService),
                new CategoryFacade(catService),
                new OperationFacade(opService),
                new AnalyticsFacade(opService, accService, catService)
        );

        var importers = List.of(
                new CsvDataImporter(facades, accFact, catFact, opFact),
                new JsonDataImporter(facades, mapper),
                new YamlDataImporter(facades)
        );

        var exporters = List.of(
                new CsvDataExporter(facades),
                new JsonDataExporter(facades, mapper),
                new YamlDataExporter(facades)
        );

        List<Command> commands = createCommands(facades, importers, exporters, console, display);

        return new MenuController(console, commands);
    }

    private static List<Command> createCommands(FacadeContext facades, List<DataImporter> importers, List<DataExporter> exporters, ConsoleHelper console, DisplayHelper display) {
        List<Command> commands = new ArrayList<>();

        // Счета и Категории
        commands.add(new CreateAccountCommand(facades, console));
        commands.add(new EditAccountCommand(facades, console));
        commands.add(new DeleteAccountCommand(facades, console));
        commands.add(new ShowAccountsCommand(facades, display));
        commands.add(new CreateCategoryCommand(facades, console));
        commands.add(new EditCategoryCommand(facades, console));
        commands.add(new DeleteCategoryCommand(facades, console));
        commands.add(new ShowCategoriesCommand(facades, display));

        // Операции
        commands.add(new AddIncomeCommand(facades, console, display));
        commands.add(new AddExpenseCommand(facades, console, display));
        commands.add(new EditOperationCommand(facades, console));
        commands.add(new DeleteOperationCommand(facades, console));
        commands.add(new ShowOperationsCommand(facades, console, display));

        // Аналитика и сервис
        // замеряемые команды
        addTimedCommand(commands, new BalanceVerificationCommand(facades, console));
        addTimedCommand(commands, new CategoryReportCommand(facades, console));
        addTimedCommand(commands, new DifferenceForPeriodCommand(facades, console));
        addTimedCommand(commands, new ExportDataCommand(facades, exporters, console));
        addTimedCommand(commands, new ImportDataCommand(facades, importers, console));

        return commands;
    }

    private static void addTimedCommand(List<Command> list, Command command) {
        list.add(new TimedCommand(command));
    }
}

