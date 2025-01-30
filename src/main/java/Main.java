import services.DeeplService;

import java.util.Scanner;

/**
 * Главный класс программы для перевода текста между русским и английским языками.
 * Пользователь может выбрать направление перевода и ввести текст для перевода.
 * Программа использует сервис DeeplService для выполнения перевода.
 */
public class Main {

    /**
     * Точка входа в программу. Здесь происходит выбор языка перевода, ввод текста и его перевод.
     * Программа продолжает работать, пока пользователь не выберет выход.
     *
     * @param args Массив аргументов командной строки (не используется в данном классе).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DeeplService translator = new DeeplService();

        String sourceLang = "", targetLang = "";
        boolean isLanguageChosen = false;

        // Основной цикл программы
        while (true) {
            // Проверка, выбран ли язык перевода
            if (!isLanguageChosen) {
                System.out.println("Выберите язык перевода:");
                System.out.println("1. С русского на английский");
                System.out.println("2. С английского на русский");
                System.out.println("3. Выход");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 3) {
                    System.out.println("Выход из программы...");
                    break;
                }

                // Установка языков перевода в зависимости от выбора пользователя
                switch (choice) {
                    case 1:
                        sourceLang = "RU";
                        targetLang = "EN";
                        break;
                    case 2:
                        sourceLang = "EN";
                        targetLang = "RU";
                        break;
                    default:
                        System.out.println("Некорректный выбор.");
                        continue;
                }
                isLanguageChosen = true;
            }

            // Ввод текста для перевода
            System.out.println("Введите текст для перевода (напишите '123' для выбора языка):");
            String text = scanner.nextLine();

            // Проверка на специальную команду для выбора языка
            if (text.equalsIgnoreCase("123")) {
                isLanguageChosen = false;
                continue;
            }

            // Выполнение перевода
            String translatedText = translator.translateText(text, sourceLang, targetLang);
            System.out.println("Перевод: " + translatedText);
        }

        scanner.close();
    }
}
