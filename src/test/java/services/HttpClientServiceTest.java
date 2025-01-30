package services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Config;

import java.net.HttpURLConnection;

class HttpClientServiceTest {

    private HttpClientService httpClientService;

    @BeforeEach
    void setUp() {
        httpClientService = new HttpClientService();
    }

    @Test
    void testSendPostRequest() throws Exception {
        // Подготовка мока HttpURLConnection
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getInputStream()).thenReturn(new java.io.ByteArrayInputStream("{\"text\":\"test\"}".getBytes()));

        //Подготовка данных
        String API_URL = "https://api-free.deepl.com/v2/translate";
        String requestBody = "text=" + "п" + "&source_lang=" + "RU" + "&target_lang=" + "EN";
        String authHeader = "DeepL-Auth-Key " + Config.API_KEY;

        // Проверка, что метод отрабатывает корректно
        String response = httpClientService.sendPostRequest(API_URL, requestBody, authHeader);
        assertNotNull(response); // Ответ не должен быть null
    }
}
