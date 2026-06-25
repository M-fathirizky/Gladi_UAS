package ui;

import model.Tournament;

public class Display {

    private static final String LINE = "=".repeat(52);
    private static final String THIN = "-".repeat(52);

    public static void header() {
        System.out.println(LINE);
        System.out.println("              E-SPORTS ARENA");
        System.out.println("           Tournament Hub System");
        System.out.println(LINE);
    }

    public static void line() { System.out.println(LINE); }
    public static void thinLine() { System.out.println(THIN); }

    public static void success(String msg) {
        System.out.println("[SUCCESS] " + msg);
    }

    public static void error(String msg) {
        System.out.println("[ERROR] " + msg);
    }

    public static void info(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public static void pressEnter(java.util.Scanner sc) {
        System.out.print("\nTekan Enter untuk kembali ke menu...");
        sc.nextLine();
    }

    public static void tournamentTableHeader() {
        System.out.printf("%-4s %-25s %-16s %-12s %-8s%n",
                "ID", "Nama Turnamen", "Kategori Game", "Biaya", "Kuota");
        System.out.printf("%-4s %-25s %-16s %-12s %-8s%n",
                "---", "-------------------------", "----------------", "----------", "--------");
    }

    public static void tournamentRow(Tournament t) {
        System.out.printf("%-4s %-25s %-16s Rp%-10.0f %d %s%n",
                t.getId(), t.getName(), t.getGameCategory(),
                t.getRegistrationFee(), t.getMaxQuota(), t.getStatus());
    }
}