package service;

import database.DataStore;
import model.Registration;
import model.Tournament;
import ui.Display;

import java.util.List;
import java.util.Scanner;

public class PlayerService {

    private final Scanner sc;

    public PlayerService(Scanner sc) {
        this.sc = sc;
    }

    /**
     * @return true  = user memilih Logout (kembali ke login)
     *         false = user memilih Keluar Program (tutup app)
     */
    public boolean showMenu(String username) {
        while (true) {
            Display.header();
            System.out.println("Selamat Datang, " + username + "!");
            Display.line();
            System.out.println("Menu Pemain:");
            System.out.println("1. Lihat Daftar Turnamen Tersedia");
            System.out.println("2. Cari Turnamen Berdasarkan Game");
            System.out.println("3. Daftar Turnamen");
            System.out.println("4. Riwayat Turnamen Saya");
            System.out.println("5. Logout");
            System.out.println("6. Keluar Program");
            System.out.print("Pilih Menu : ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> viewOpenTournaments();
                case "2" -> searchByGame();
                case "3" -> registerTournament(username);
                case "4" -> viewHistory(username);
                case "5" -> { return true;  }  // logout → kembali ke login
                case "6" -> { return false; }  // keluar → tutup program
                default  -> Display.error("Pilihan tidak valid.");
            }
        }
    }

    private void viewOpenTournaments() {
        Display.line();
        System.out.println("Daftar Turnamen Terbuka:");
        Display.tournamentTableHeader();
        List<Tournament> open = DataStore.getOpenTournaments();
        if (open.isEmpty()) {
            System.out.println("Tidak ada turnamen yang tersedia saat ini.");
        } else {
            for (Tournament t : open) Display.tournamentRow(t);
        }
        Display.line();
        Display.pressEnter(sc);
    }

    private void searchByGame() {
        Display.line();
        System.out.println("Cari Turnamen Berdasarkan Game");
        System.out.println("Contoh: Valorant, EA FC 25, Counter-Strike 2");
        Display.line();
        System.out.print("Masukkan Kategori Game: ");
        String game = sc.nextLine().trim();

        List<Tournament> results = DataStore.getTournamentsByGame(game);
        Display.line();
        if (results.isEmpty()) {
            Display.info("Tidak ada turnamen ditemukan untuk game \"" + game + "\".");
        } else {
            System.out.println("Hasil Pencarian untuk \"" + game + "\":");
            Display.tournamentTableHeader();
            for (Tournament t : results) Display.tournamentRow(t);
        }
        Display.line();
        Display.pressEnter(sc);
    }

    private void registerTournament(String username) {
        Display.line();
        System.out.println("Menu Pendaftaran");
        Display.line();

        List<Tournament> open = DataStore.getOpenTournaments();
        if (open.isEmpty()) {
            Display.info("Tidak ada turnamen yang tersedia.");
            Display.pressEnter(sc);
            return;
        }
        Display.tournamentTableHeader();
        for (Tournament t : open) Display.tournamentRow(t);
        Display.line();

        System.out.print("Masukkan ID Turnamen yang ingin diikuti: ");
        String id = sc.nextLine().trim();
        Tournament t = DataStore.findTournamentById(id);

        if (t == null) {
            Display.error("Turnamen tidak ditemukan.");
            Display.pressEnter(sc);
            return;
        }
        if (!t.isOpen()) {
            Display.error("Turnamen ini sudah PENUH atau DITUTUP.");
            Display.pressEnter(sc);
            return;
        }
        if (DataStore.hasRegistered(username, id)) {
            Display.error("Anda sudah mendaftar di turnamen ini sebelumnya.");
            Display.pressEnter(sc);
            return;
        }

        Display.line();
        System.out.println("Detail Turnamen:");
        System.out.println("Nama   : " + t.getName());
        System.out.println("Game   : " + t.getGameCategory());
        System.out.printf("Biaya  : Rp%.0f%n", t.getRegistrationFee());
        System.out.println("Slot   : " + t.getRemainingSlots() + " tersisa");
        Display.line();

        System.out.print("Konfirmasi Pendaftaran? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            Registration reg = DataStore.addRegistration(username, t);
            Display.line();
            Display.success("Pendaftaran Berhasil!");
            System.out.println("Tiket pendaftaran telah masuk ke menu Riwayat Turnamen Saya.");
            System.out.println("No. Tiket : " + reg.getTicketId());
            Display.line();
        } else {
            Display.info("Pendaftaran dibatalkan.");
        }
        Display.pressEnter(sc);
    }

    private void viewHistory(String username) {
        Display.line();
        System.out.println("Riwayat Turnamen Saya - " + username);
        Display.line();

        List<Registration> history = DataStore.getRegistrationsByUser(username);
        if (history.isEmpty()) {
            Display.info("Belum ada turnamen yang Anda ikuti.");
        } else {
            System.out.printf("%-12s %-25s %-16s %-12s %-18s%n",
                    "No. Tiket", "Nama Turnamen", "Kategori Game", "Biaya", "Tgl Daftar");
            Display.thinLine();
            for (Registration r : history) {
                System.out.printf("%-12s %-25s %-16s Rp%-10.0f %-18s%n",
                        r.getTicketId(), r.getTournamentName(),
                        r.getGameCategory(), r.getFee(), r.getFormattedDate());
            }
        }
        Display.line();
        Display.pressEnter(sc);
    }
}