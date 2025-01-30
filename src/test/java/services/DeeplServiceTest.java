package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

class DeeplServiceTest {

    private DeeplService deeplService;
    private HttpClientService mockHttpClient;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Создание экземпляра DeeplService с пустым конструктором
        deeplService = new DeeplService();

        // Создание мока для HttpClientService
        mockHttpClient = mock(HttpClientService.class);

        // Использование рефлексии для установки мока в поле httpClient
        Field field = DeeplService.class.getDeclaredField("httpClient");
        field.setAccessible(true); // Даем доступ к приватному полю
        field.set(deeplService, mockHttpClient);
    }

    @Test
    void testTranslateText() throws Exception {
        // Подготовка данных
        String text = "Привет, мир!";
        String sourceLang = "RU";
        String targetLang = "EN";

        // Мокируем ответ API с правильной структурой JSON
        String mockResponse = "{\"translations\":[{\"detected_source_language\":\"RU\",\"text\":\"Hello, world!\"}]}";

        // Устанавливаем поведение для мока
        when(mockHttpClient.sendPostRequest(anyString(), anyString(), anyString())).thenReturn(mockResponse);

        // Вызов метода перевода
        String translatedText = deeplService.translateText(text, sourceLang, targetLang);

        // Печатаем в консоль для отладки
        System.out.println("Translated text: " + translatedText);

        // Проверка результатов
        assertEquals("Hello, world!", translatedText);  // Ожидаемый перевод
    }
}
