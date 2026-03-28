package tiger.bankapp.helpers;

import java.util.Scanner;

public class ConsoleHelper {
    private final Scanner scanner;

    public ConsoleHelper() {
        this.scanner = new Scanner(System.in);
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число");
            }
        }
    }

    public Long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число");
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.out.println("Ошибка: " + error);
    }

    public void printSuccess(String message) {
        System.out.println(message);
    }

    public void printMenu(String title, String[] menuItems) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(title);
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println((i + 1) + ". " + menuItems[i]);
        }
        System.out.println("0. Выход");
        System.out.println("-------------------->");
    }

    public void waitForEnter() {
        System.out.println("\nНажмите Enter чтобы продолжить...");
        scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}