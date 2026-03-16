package tiger.bankapp.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Поддерживаемые приложением форматы файлов для импорта и экспорта
 */
@Getter
@RequiredArgsConstructor
public enum ImportFormat {
    JSON("JSON", ".json"),
    YAML("YAML", ".yaml"),
    CSV("CSV", ".csv");

    private final String label;
    private final String extension;

    /**
     * Метод для безопасного поиска формата из строки
     */
    public static ImportFormat getByFileName(String fileName) {
        return Arrays.stream(values())
                .filter(format -> fileName.toLowerCase().endsWith(format.extension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неподдерживаемый формат файла: " + fileName));
    }

}
