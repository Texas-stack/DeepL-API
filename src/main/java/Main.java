import repository.TranslationRepository;
import services.DeeplService;

import java.util.List;
import java.util.Scanner;

/**
 * Главный класс программы, обеспечивающий интерфейс для перевода текста и просмотра истории переводов.
 * Пользователь может выбрать опцию для перевода текста, просмотра истории или выхода из программы.
 */
public class Main {

    /**
     * Точка входа в программу. Основной цикл программы для выбора опции и перевода текста.
     * Программа продолжает работать, пока пользователь не выберет выход.
     *
     * @param args Массив аргументов командной строки (не используется в данном классе).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DeeplService translator = new DeeplService();
        TranslationRepository translationRepository = new TranslationRepository();

        String sourceLang = "", targetLang = "";
        boolean isLanguageChosen = false;

        // Основной цикл программы
        while (true) {
            // Если язык перевода не выбран, предлагается выбрать опцию
            if (!isLanguageChosen) {
                System.out.println("Выберите опцию:");
                System.out.println("1. Перевести текст");
                System.out.println("2. Посмотреть историю переводов");
                System.out.println("3. Выход");
                int choice = scanner.nextInt();
                scanner.nextLine();

                // Обработка выбора пользователя
                if (choice == 3) {
                    System.out.println("Выход из программы...");
                    break;
                } else if (choice == 2) {
                    // Вывод истории переводов
                    List<String> history = translationRepository.getTranslationHistory();
                    if (history.isEmpty()) {
                        System.out.println("История пуста.");
                    } else {
                        System.out.println("Последние переводы:");
                        for (String entry : history) {
                            System.out.println(entry);
                        }
                    }
                    continue;
                }

                // Выбор языка перевода
                System.out.println("Выберите язык перевода:");
                System.out.println("1. С русского на английский");
                System.out.println("2. С английского на русский");
                int langChoice = scanner.nextInt();
                scanner.nextLine();

                // Установка выбранных языков
                switch (langChoice) {
                    case 1 -> {
                        sourceLang = "RU";
                        targetLang = "EN";
                    }
                    case 2 -> {
                        sourceLang = "EN";
                        targetLang = "RU";
                    }
                    default -> {
                        System.out.println("Некорректный выбор.");
                        continue;
                    }
                }
                isLanguageChosen = true;
            }

            // Ввод текста для перевода
            System.out.println("Введите текст для перевода (напишите '123' для смены языка):");
            String text = scanner.nextLine();

            // Проверка команды для смены языка
            if (text.equalsIgnoreCase("123")) {
                isLanguageChosen = false;
                continue;
            }

            // Перевод текста
            String translatedText = translator.translateText(text, sourceLang, targetLang);
            System.out.println("Перевод: " + translatedText);
        }

        // Закрытие сканера
        scanner.close();
    }
}
