package main;

import model.User;
import service.AdminService;
import service.AuthService;
import service.PlayerService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService(sc);

        while (true) {
            User user = auth.login();
            System.out.println("Login berhasil: " + user.getUsername());

            if (user == null) break;

            boolean logout;
            if (user.isAdmin()) {
                logout = new AdminService(sc).showMenu(user.getUsername());
            } else {
                logout = new PlayerService(sc).showMenu(user.getUsername());
            }

            if (!logout) break;

            System.out.println("\nAnda telah logout. Silakan login kembali.");
        }

        System.out.println("Program ditutup. Sampai jumpa!");
        sc.close();
    }
}
