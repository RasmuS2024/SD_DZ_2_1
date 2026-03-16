package tiger.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tiger.bankapp.controller.MenuController;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        try (ConfigurableApplicationContext context = SpringApplication.run(Main.class, args)) {

            // Получаем контроллер меню из контейнера Spring
            MenuController menuController = context.getBean(MenuController.class);

            // Запускаем меню
            menuController.start();

        } finally {
            System.out.println("Программа завершена");
        }
    }
}