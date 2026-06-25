package database;

import model.Registration;
import model.Tournament;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory data store (pengganti database).
 * Semua data di-seed di sini sebagai data awal.
 */
public class DataStore {

    private static final List<User> users = new ArrayList<>();
    private static final List<Tournament> tournaments = new ArrayList<>();
    private static final List<Registration> registrations = new ArrayList<>();
    private static int ticketCounter = 1;

    static {
        // Seed users
        users.add(new User("admin", "admin123", "ADMIN"));
        users.add(new User("Player01", "pass01", "PLAYER"));
        users.add(new User("Player02", "pass02", "PLAYER"));
        users.add(new User("Player03", "pass03", "PLAYER"));

        // Seed tournaments
        tournaments.add(new Tournament("T01", "Valorant Cup 2026", "Valorant", 50000, 16));
        tournaments.add(new Tournament("T02", "FC 25 Campus League", "EA FC 25", 35000, 32));
        tournaments.add(new Tournament("T03", "CS2 Weekend Brawl", "Counter-Strike 2", 40000, 8));
    }

    // ---- User ----
    public static User findUser(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // ---- Tournament ----
    public static List<Tournament> getAllTournaments() { return tournaments; }

    public static Tournament findTournamentById(String id) {
        return tournaments.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public static List<Tournament> getOpenTournaments() {
        List<Tournament> open = new ArrayList<>();
        for (Tournament t : tournaments) {
            if (t.isOpen()) open.add(t);
        }
        return open;
    }

    public static List<Tournament> getTournamentsByGame(String game) {
        List<Tournament> result = new ArrayList<>();
        for (Tournament t : tournaments) {
            if (t.getGameCategory().equalsIgnoreCase(game) && t.isOpen()) result.add(t);
        }
        return result;
    }

    public static void addTournament(Tournament t) { tournaments.add(t); }

    public static boolean removeTournament(String id) {
        return tournaments.removeIf(t -> t.getId().equalsIgnoreCase(id));
    }

    public static String generateTournamentId() {
        int next = tournaments.size() + 1;
        return String.format("T%02d", next);
    }

    // ---- Registration ----
    public static List<Registration> getAllRegistrations() { return registrations; }

    public static List<Registration> getRegistrationsByUser(String username) {
        List<Registration> result = new ArrayList<>();
        for (Registration r : registrations) {
            if (r.getUsername().equals(username)) result.add(r);
        }
        return result;
    }

    public static List<Registration> getRegistrationsByTournament(String tournamentId) {
        List<Registration> result = new ArrayList<>();
        for (Registration r : registrations) {
            if (r.getTournamentId().equalsIgnoreCase(tournamentId)) result.add(r);
        }
        return result;
    }

    public static boolean hasRegistered(String username, String tournamentId) {
        return registrations.stream()
                .anyMatch(r -> r.getUsername().equals(username)
                        && r.getTournamentId().equalsIgnoreCase(tournamentId));
    }

    public static Registration addRegistration(String username, Tournament tournament) {
        String ticketId = String.format("TKT-%04d", ticketCounter++);
        Registration reg = new Registration(ticketId, username, tournament);
        registrations.add(reg);
        tournament.incrementQuota();
        return reg;
    }
}