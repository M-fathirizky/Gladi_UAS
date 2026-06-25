package service;

import database.DataStore;
import model.User;
import ui.Display;

import java.util.Scanner;

public class AuthService {

    private final Scanner sc;

    public AuthService(Scanner sc) {
        this.sc = sc;
    }

    public User login() {
        int attempts = 0;
        while (attempts < 3) {
            Display.header();
            System.out.println("Silakan login untuk melanjutkan.");
            Display.line();
            System.out.print("Username : ");
            String username = sc.nextLine().trim();
            System.out.print("Password : ");
            String password = sc.nextLine().trim();

            User user = DataStore.findUser(username, password);
            if (user != null) {
                Display.success("Login berhasil sebagai " + user.getRole() + ".");
                Display.line();
                return user;
            }

            attempts++;
            Display.error("Username atau password salah. Percobaan ke-" + attempts + "/3");
        }
        System.out.println("Terlalu banyak percobaan. Program ditutup.");
        return null;
    }
}
