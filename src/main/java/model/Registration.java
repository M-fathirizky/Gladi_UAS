package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Registration {
    private String ticketId;
    private String username;
    private String tournamentId;
    private String tournamentName;
    private String gameCategory;
    private double fee;
    private LocalDateTime registeredAt;

    public Registration(String ticketId, String username, Tournament tournament) {
        this.ticketId = ticketId;
        this.username = username;
        this.tournamentId = tournament.getId();
        this.tournamentName = tournament.getName();
        this.gameCategory = tournament.getGameCategory();
        this.fee = tournament.getRegistrationFee();
        this.registeredAt = LocalDateTime.now();
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getUsername() {
        return username;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public String getGameCategory() {
        return gameCategory;
    }

    public double getFee() {
        return fee;
    }

    public String getFormattedDate() {
        return registeredAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}
