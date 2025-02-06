package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TranslationRepository {
    // Подключение к базе данных
    private static final String URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String USER = "myuser";
    private static final String PASSWORD = "mypassword";

    // Метод для сохранения перевода в базу данных
    public void saveTranslation(String sourceText, String sourceLang, String targetLang, String translatedText) {
        String sql = "INSERT INTO translations (source_text, source_lang, target_lang, translated_text) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Заполняем параметры запроса
            stmt.setString(1, sourceText);
            stmt.setString(2, sourceLang);
            stmt.setString(3, targetLang);
            stmt.setString(4, translatedText);
            // Выполняем запрос
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения истории переводов из базы данных
    public List<String> getTranslationHistory() {
        List<String> history = new ArrayList<>();
        String sql = "SELECT source_text, source_lang, target_lang, translated_text FROM translations";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Обрабатываем результаты запроса
            while (rs.next()) {
                String entry = String.format(
                        "[%s -> %s] %s → %s",
                        rs.getString("source_lang"),
                        rs.getString("target_lang"),
                        rs.getString("source_text"),
                        rs.getString("translated_text")
                );
                history.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

}
