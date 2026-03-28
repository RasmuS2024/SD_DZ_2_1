package tiger.bankapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import tiger.bankapp.command.Command;
import tiger.bankapp.command.TimedCommand;
import tiger.bankapp.command.impl.*;
import tiger.bankapp.controller.*;
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

        CommandHandler handler = createCommandHandler(mapper, console, display);

        List<Command> commands = createCommands(handler, console);

        return new MenuController(console, commands);
    }

    private static CommandHandler createCommandHandler(
            ObjectMapper mapper,
            ConsoleHelper console,
            DisplayHelper display
    ) {
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

        return new CommandHandler(importers, exporters, facades, console, display);
    }

    private static List<Command> createCommands(CommandHandler handler, ConsoleHelper console) {
        List<Command> commands = new ArrayList<>();

        // Счета и Категории
        commands.add(new CreateAccountCommand(handler));
        commands.add(new EditAccountCommand(handler));
        commands.add(new DeleteAccountCommand(handler));
        commands.add(new ShowAccountsCommand(handler));
        commands.add(new CreateCategoryCommand(handler));
        commands.add(new EditCategoryCommand(handler));
        commands.add(new DeleteCategoryCommand(handler));
        commands.add(new ShowCategoriesCommand(handler));

        // Операции
        commands.add(new AddIncomeCommand(handler));
        commands.add(new AddExpenseCommand(handler));
        commands.add(new EditOperationCommand(handler));
        commands.add(new DeleteOperationCommand(handler));
        commands.add(new ShowOperationsCommand(handler));

        // Аналитика и сервис
        // замеряемые команды
        addTimedCommand(commands, new BalanceVerificationCommand(handler));
        addTimedCommand(commands, new CategoryReportCommand(handler));
        addTimedCommand(commands, new DifferenceForPeriodCommand(handler));
        addTimedCommand(commands, new ExportDataCommand(handler, console));
        addTimedCommand(commands, new ImportDataCommand(handler, console));

        return commands;
    }

    private static void addTimedCommand(List<Command> list, Command command) {
        list.add(new TimedCommand(command));
    }
}

