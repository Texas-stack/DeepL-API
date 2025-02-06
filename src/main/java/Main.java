import repository.TranslationRepository;
import services.DeeplService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DeeplService translator = new DeeplService();
        TranslationRepository translationRepository = new TranslationRepository();

        String sourceLang = "", targetLang = "";
        boolean isLanguageChosen = false;

        while (true) {
            if (!isLanguageChosen) {
                System.out.println("Выберите опцию:");
                System.out.println("1. Перевести текст");
                System.out.println("2. Посмотреть историю переводов");
                System.out.println("3. Выход");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 3) {
                    System.out.println("Выход из программы...");
                    break;
                } else if (choice == 2) {
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

                System.out.println("Выберите язык перевода:");
                System.out.println("1. С русского на английский");
                System.out.println("2. С английского на русский");
                int langChoice = scanner.nextInt();
                scanner.nextLine();

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

            System.out.println("Введите текст для перевода (напишите '123' для смены языка):");
            String text = scanner.nextLine();

            if (text.equalsIgnoreCase("123")) {
                isLanguageChosen = false;
                continue;
            }

            String translatedText = translator.translateText(text, sourceLang, targetLang);
            System.out.println("Перевод: " + translatedText);
        }

        scanner.close();
    }
}
