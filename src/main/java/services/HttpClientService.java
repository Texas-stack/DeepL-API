package services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

/**
 * Сервис для отправки HTTP POST-запросов.
 * Этот класс реализует метод для отправки POST-запросов на указанный URL с данными и заголовками.
 */
public class HttpClientService {

    /**
     * Отправляет HTTP POST-запрос на указанный API URL с данными и заголовками авторизации.
     *
     * @param apiUrl     URL-адрес API, на который отправляется запрос.
     * @param body       Тело запроса, включающее параметры, которые будут отправлены.
     * @param authHeader Заголовок авторизации, содержащий ключ API или токен.
     * @return Ответ от сервера в виде строки.
     * @throws Exception Если возникнут ошибки при отправке запроса или получении ответа.
     */
    public String sendPostRequest(String apiUrl, String body, String authHeader) throws Exception {
        // Создание объекта URL для указания API
        HttpURLConnection conn = getHttpURLConnection(apiUrl, authHeader);

        // Отправка данных в теле запроса
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
        }

        // Чтение ответа от сервера
        Scanner scanner = new Scanner(conn.getInputStream());
        // Считывание всего ответа как строку
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();
        return response;
    }

    private static HttpURLConnection getHttpURLConnection(String apiUrl, String authHeader) throws IOException {
        // Использование URI для создания объекта URL
        URI uri = URI.create(apiUrl);
        URL url = uri.toURL(); // Преобразуем URI в URL
        // Открытие соединения с API
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Установка метода запроса как POST
        conn.setRequestMethod("POST");
        // Установка заголовков для авторизации и типа контента
        conn.setRequestProperty("Authorization", authHeader);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // Разрешение отправки данных в теле запроса
        conn.setDoOutput(true);
        return conn;
    }
}
