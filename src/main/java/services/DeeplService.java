package services;

import utils.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Сервис для перевода текста с использованием API DeepL.
 * Этот класс отправляет запросы к API DeepL для перевода текста с одного языка на другой.
 * Для работы используется ключ API, который получается из класса {@link Config}.
 */
public class DeeplService {

    /**
     * URL-адрес API для перевода текста.
     */
    private static final String API_URL = "https://api-free.deepl.com/v2/translate";

    /**
     * Служба для отправки HTTP-запросов.
     */
    private final HttpClientService httpClient;

    /**
     * Logger для записи ошибок.
     */
    private static final Logger LOGGER = Logger.getLogger(DeeplService.class.getName());

    /**
     * Конструктор, инициализирующий службу отправки HTTP-запросов.
     */
    public DeeplService() {
        this.httpClient = new HttpClientService();
    }

    /**
     * Переводит текст с исходного языка на целевой язык с использованием API DeepL.
     *
     * @param text       Текст, который нужно перевести.
     * @param sourceLang Код исходного языка (например, "RU" для русского).
     * @param targetLang Код целевого языка (например, "EN" для английского).
     * @return Переведённый текст, если запрос был успешным, или сообщение об ошибке в случае неудачи.
     */
    public String translateText(String text, String sourceLang, String targetLang) {
        try {
            // Формирование тела запроса
            String requestBody = "text=" + text + "&source_lang=" + sourceLang + "&target_lang=" + targetLang;
            // Формирование заголовка авторизации с использованием API ключа
            String authHeader = "DeepL-Auth-Key " + Config.API_KEY;
            // Отправка POST-запроса и получение ответа
            String response = httpClient.sendPostRequest(API_URL, requestBody, authHeader);
            // Парсинг ответа для получения переведённого текста
            return parseTranslation(response);
        } catch (Exception e) {
            // Логирование ошибки с использованием Logger
            LOGGER.log(Level.SEVERE, "Ошибка при переводе текста: ", e);
            return "Ошибка при переводе."; // Возвращается сообщение об ошибке в случае исключений
        }
    }

    /**
     * Извлекает переведённый текст из JSON-ответа API.
     *
     * @param jsonResponse Ответ от API в формате JSON.
     * @return Переведённый текст.
     */
    private String parseTranslation(String jsonResponse) {
        // Поиск начала и конца переведённого текста в JSON-ответе
        int start = jsonResponse.indexOf("\"text\":\"") + 8;
        int end = jsonResponse.indexOf("\"}", start);
        // Возврат переведённого текста
        return jsonResponse.substring(start, end);
    }
}
