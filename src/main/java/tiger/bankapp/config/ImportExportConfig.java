package tiger.bankapp.config;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ImportExportConfig {

    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static final Locale NUMBER_LOCALE = Locale.US;

}
