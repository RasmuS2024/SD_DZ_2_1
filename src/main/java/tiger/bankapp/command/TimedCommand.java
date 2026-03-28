package tiger.bankapp.command;

/**
 * Декоратор для замера времени выполнения команд
 */
public class TimedCommand implements Command {
    private final Command delegate;

    public TimedCommand(Command delegate) {
        this.delegate = delegate;
    }

    @Override
    public void execute() {
        long start = System.nanoTime();
        try {
            delegate.execute();
        } finally {
            long duration = System.nanoTime() - start;
            double millis = duration / 1_000_000.0;

            System.out.printf("%n Команда '%s' выполнена за %.3f мс%n",
                    getLabel(), millis);
        }
    }

    @Override
    public String getLabel() {
        return delegate.getLabel();
    }

    @Override
    public int getOrder() {
        return delegate.getOrder();
    }
}
