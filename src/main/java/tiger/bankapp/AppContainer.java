package tiger.bankapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import tiger.bankapp.command.Command;
import tiger.bankapp.command.TimedCommand;
import tiger.bankapp.command.impl.*;
import tiger.bankapp.controller.*;
import tiger.bankapp.facade.*;
import tiger.bankapp.factory.*;
import tiger.bankapp.helpers.ConsoleHelper;
import tiger.bankapp.importer.*;
import tiger.bankapp.exporter.*;
import tiger.bankapp.repository.*;
import tiger.bankapp.service.*;

import java.util.List;
import java.util.ArrayList;

public class AppContainer {

    private AppContainer() {
        throw new IllegalStateException("Utility class");
    }

    public static MenuController assemble(ConsoleHelper console, ObjectMapper mapper) {
        CommandHandler handler = createCommandHandler(mapper);
        List<Command> commands = createCommands(handler, console);

        return new MenuController(console, commands);
    }

    private static CommandHandler createCommandHandler(ObjectMapper mapper) {
        var accFact = new AccountFactory();
        var catFact = new CategoryFactory();
        var opFact = new OperationFactory();

        var accRepo = new AccountRepository();
        var catRepo = new CategoryRepository(catFact);
        var opRepo = new OperationRepository();

        var accService = new AccountServiceImpl(accRepo, accFact);
        var catService = new CategoryServiceImpl(catRepo, catFact);
        var opService = new OperationServiceImpl(opRepo, accService, opFact);

        var accFacade = new AccountFacade(accService);
        var catFacade = new CategoryFacade(catService);
        var opFacade = new OperationFacade(opService);
        var anFacade = new AnalyticsFacade(opService, accService, catService);

        var importers = List.of(
                new CsvDataImporter(accFacade, catFacade, opFacade, accFact, catFact, opFact),
                new JsonDataImporter(accFacade, catFacade, opFacade, mapper),
                new YamlDataImporter(accFacade, catFacade, opFacade)
        );

        var exporters = List.of(
                new CsvDataExporter(accFacade, catFacade, opFacade),
                new JsonDataExporter(accFacade, catFacade, opFacade, mapper),
                new YamlDataExporter(accFacade, catFacade, opFacade)
        );

        return new CommandHandler(importers, exporters, accFacade, catFacade, opFacade, anFacade);
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

