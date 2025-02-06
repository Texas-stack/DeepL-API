package services;

import repository.TranslationRepository;
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
    private final TranslationRepository translationRepository = new TranslationRepository();

    public String translateText(String text, String sourceLang, String targetLang) {
        try {
            String requestBody = "text=" + text + "&source_lang=" + sourceLang + "&target_lang=" + targetLang;
            String authHeader = "DeepL-Auth-Key " + Config.API_KEY;
            String response = httpClient.sendPostRequest(API_URL, requestBody, authHeader);
            String translatedText = parseTranslation(response);

            // Сохраняем перевод в БД
            translationRepository.saveTranslation(text, sourceLang, targetLang, translatedText);

            return translatedText;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при переводе текста: ", e);
            return "Ошибка при переводе.";
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