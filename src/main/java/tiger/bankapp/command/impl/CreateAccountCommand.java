package tiger.bankapp.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tiger.bankapp.command.Command;
import tiger.bankapp.facade.AccountFacade;
import tiger.bankapp.helpers.ConsoleHelper;

@Component
@RequiredArgsConstructor
public class CreateAccountCommand implements Command {
    private final AccountFacade accountFacade;
    private final ConsoleHelper console;

    @Override
    public void execute() {
        String name = console.readString("Название счета: ");
        accountFacade.createAccount(name);
        System.out.println("Счет успешно создан!");
    }

    @Override
    public String getDescription() { return "Создать счет"; }
}

