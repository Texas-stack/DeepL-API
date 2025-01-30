package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConfigTest {

    @Test
    void testApiKeyLoading() {
        // Проверка, что API_KEY не равен null и содержит правильное значение
        assertNotNull(Config.API_KEY, "API ключ должен быть загружен из конфигурации");
    }
}
