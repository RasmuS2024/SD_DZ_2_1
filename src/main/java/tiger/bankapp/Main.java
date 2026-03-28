package tiger.bankapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import tiger.bankapp.helpers.ConsoleHelper;

public class Main {
    public static void main(String[] args) {
        ConsoleHelper console = new ConsoleHelper();
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            AppContainer.assemble(console, mapper).start();
        } catch (Exception e) {
            console.printError("Произошел критический сбой в работе приложения: " + e.getMessage());
        } finally {
            console.close();
        }
    }
}
