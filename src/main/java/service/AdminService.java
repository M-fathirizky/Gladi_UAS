package service;

import database.DataStore;
import model.Registration;
import model.Tournament;
import ui.Display;

import java.util.List;
import java.util.Scanner;

public class AdminService {

    private final Scanner sc;

    public AdminService(Scanner sc) {
        this.sc = sc;
    }

    /**
     * @return true  = user memilih Logout (kembali ke login)
     *         false = user memilih Keluar Program (tutup app)
     */
    public boolean showMenu(String username) {
        while (true) {
            Display.header();
            System.out.println("Selamat Datang, " + username + "! [ADMIN]");
            Display.line();
            System.out.println("Menu Admin:");
            System.out.println("1. Lihat Semua Turnamen");
            System.out.println("2. Tambah Turnamen Baru");
            System.out.println("3. Edit Turnamen");
            System.out.println("4. Hapus Turnamen");
            System.out.println("5. Laporan Pendaftar");
            System.out.println("6. Logout");
            System.out.println("7. Keluar Program");
            System.out.print("Pilih Menu : ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> viewAllTournaments();
                case "2" -> addTournament();
                case "3" -> editTournament();
                case "4" -> deleteTournament();
                case "5" -> viewRegistrationReport();
                case "6" -> { return true;  }  // logout → kembali ke login
                case "7" -> { return false; }  // keluar → tutup program
                default  -> Display.error("Pilihan tidak valid.");
            }
        }
    }

    private void viewAllTournaments() {
        Display.line();
        System.out.println("Daftar Semua Turnamen:");
        Display.tournamentTableHeader();
        List<Tournament> all = DataStore.getAllTournaments();
        if (all.isEmpty()) {
            System.out.println("Belum ada data turnamen.");
        } else {
            for (Tournament t : all) Display.tournamentRow(t);
        }
        Display.line();
        Display.pressEnter(sc);
    }

    private void addTournament() {
        Display.line();
        System.out.println("Tambah Turnamen Baru");
        Display.line();

        String id = DataStore.generateTournamentId();
        System.out.println("ID Turnamen (otomatis): " + id);

        System.out.print("Nama Turnamen         : ");
        String name = sc.nextLine().trim();

        System.out.print("Kategori Game         : ");
        String game = sc.nextLine().trim();

        double fee = 0;
        while (true) {
            System.out.print("Biaya Pendaftaran (Rp): ");
            try { fee = Double.parseDouble(sc.nextLine().trim()); break; }
            catch (NumberFormatException e) { Display.error("Masukkan angka yang valid."); }
        }

        int quota = 0;
        while (true) {
            System.out.print("Kuota Maksimal        : ");
            try { quota = Integer.parseInt(sc.nextLine().trim()); break; }
            catch (NumberFormatException e) { Display.error("Masukkan angka yang valid."); }
        }

        Tournament t = new Tournament(id, name, game, fee, quota);
        DataStore.addTournament(t);
        Display.success("Turnamen \"" + name + "\" berhasil ditambahkan dengan ID " + id);
        Display.pressEnter(sc);
    }

    private void editTournament() {
        Display.line();
        System.out.println("Edit Turnamen");
        Display.tournamentTableHeader();
        DataStore.getAllTournaments().forEach(Display::tournamentRow);
        Display.line();

        System.out.print("Masukkan ID Turnamen yang ingin diedit: ");
        String id = sc.nextLine().trim();
        Tournament t = DataStore.findTournamentById(id);
        if (t == null) { Display.error("Turnamen tidak ditemukan."); Display.pressEnter(sc); return; }

        System.out.println("Editing: " + t.getName() + " (kosongkan untuk tidak mengubah)");

        System.out.print("Nama baru [" + t.getName() + "]: ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) t.setName(name);

        System.out.print("Kategori Game baru [" + t.getGameCategory() + "]: ");
        String game = sc.nextLine().trim();
        if (!game.isEmpty()) t.setGameCategory(game);

        System.out.print("Status baru (OPEN/FULL/CLOSED) [" + t.getStatus() + "]: ");
        String status = sc.nextLine().trim().toUpperCase();
        if (status.equals("OPEN") || status.equals("FULL") || status.equals("CLOSED")) {
            t.setStatus(status);
        } else if (!status.isEmpty()) {
            Display.error("Status tidak valid, status tidak diubah.");
        }

        Display.success("Data turnamen berhasil diperbarui.");
        Display.pressEnter(sc);
    }

    private void deleteTournament() {
        Display.line();
        System.out.println("Hapus Turnamen");
        Display.tournamentTableHeader();
        DataStore.getAllTournaments().forEach(Display::tournamentRow);
        Display.line();

        System.out.print("Masukkan ID Turnamen yang ingin dihapus: ");
        String id = sc.nextLine().trim();
        Tournament t = DataStore.findTournamentById(id);
        if (t == null) { Display.error("Turnamen tidak ditemukan."); Display.pressEnter(sc); return; }

        System.out.print("Yakin ingin menghapus \"" + t.getName() + "\"? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            DataStore.removeTournament(id);
            Display.success("Turnamen berhasil dihapus.");
        } else {
            Display.info("Penghapusan dibatalkan.");
        }
        Display.pressEnter(sc);
    }

    private void viewRegistrationReport() {
        Display.line();
        System.out.println("Laporan Pendaftar Turnamen");
        Display.line();

        System.out.print("Masukkan ID Turnamen: ");
        String id = sc.nextLine().trim();
        Tournament t = DataStore.findTournamentById(id);
        if (t == null) { Display.error("Turnamen tidak ditemukan."); Display.pressEnter(sc); return; }

        List<Registration> regs = DataStore.getRegistrationsByTournament(id);
        System.out.println("Turnamen : " + t.getName() + " (" + t.getId() + ")");
        System.out.println("Total Pendaftar: " + regs.size() + " / " + t.getMaxQuota());
        Display.thinLine();

        if (regs.isEmpty()) {
            System.out.println("Belum ada pendaftar.");
        } else {
            System.out.printf("%-6s %-15s %-12s %-18s%n", "No.", "Username", "Tiket", "Waktu Daftar");
            int i = 1;
            for (Registration r : regs) {
                System.out.printf("%-6d %-15s %-12s %-18s%n",
                        i++, r.getUsername(), r.getTicketId(), r.getFormattedDate());
            }
        }
        Display.line();
        Display.pressEnter(sc);
    }
}