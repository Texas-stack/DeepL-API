package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для загрузки конфигурации из файла свойств.
 * Этот класс загружает файл конфигурации, содержащий настройки для приложения,
 * включая ключ API, и предоставляет доступ к этим настройкам.
 */
public class Config {

    /**
     * Путь к файлу конфигурации, содержащему настройки.
     */
    private static final String PROPERTIES_FILE = "config.properties"; // Путь к файлу

    /**
     * Ключ API для доступа к сервису.
     * Значение загружается из файла конфигурации.
     */
    public static String API_KEY;

    /**
     * Logger для записи ошибок и предупреждений.
     */
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

    // Статический блок инициализации, который загружает конфигурацию
    static {
        Properties properties = new Properties();
        // Попытка загрузить файл конфигурации
        InputStream input = Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (input == null) {
            LOGGER.log(Level.WARNING, "Не получилось найти API KEY " + PROPERTIES_FILE);
        } else {
            try {
                // Загрузка свойств из файла
                properties.load(input);
                // Получение ключа API из файла конфигурации
                API_KEY = properties.getProperty("api.key");
            } catch (IOException e) {
                // Логирование ошибки при загрузке конфигурации
                LOGGER.log(Level.SEVERE, "Ошибка при загрузке конфигурации", e);
            } finally {
                try {
                    // Закрытие потока, после загрузки данных
                    input.close();
                } catch (IOException ex) {
                    // Логирование ошибки при закрытии потока
                    LOGGER.log(Level.SEVERE, "Ошибка при закрытии потока конфигурации", ex);
                }
            }
        }
    }
}
