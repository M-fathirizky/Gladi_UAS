package model;

public class Tournament {
    private String id;
    private String name;
    private String gameCategory;
    private double registrationFee;
    private int maxQuota;
    private int currentQuota;
    private String status; // OPEN, FULL, CLOSED

    public Tournament(String id, String name, String gameCategory, double registrationFee, int maxQuota) {
        this.id = id;
        this.name = name;
        this.gameCategory = gameCategory;
        this.registrationFee = registrationFee;
        this.maxQuota = maxQuota;
        this.currentQuota = 0;
        this.status = "OPEN";
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getGameCategory() { return gameCategory; }
    public double getRegistrationFee() { return registrationFee; }
    public int getMaxQuota() { return maxQuota; }
    public int getCurrentQuota() { return currentQuota; }
    public String getStatus() { return status; }
    public int getRemainingSlots() { return maxQuota - currentQuota; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setGameCategory(String gameCategory) { this.gameCategory = gameCategory; }
    public void setRegistrationFee(double registrationFee) { this.registrationFee = registrationFee; }
    public void setMaxQuota(int maxQuota) { this.maxQuota = maxQuota; }
    public void setStatus(String status) { this.status = status; }

    public void incrementQuota() {
        this.currentQuota++;
        if (this.currentQuota >= this.maxQuota) {
            this.status = "FULL";
        }
    }

    public boolean isOpen() {
        return this.status.equals("OPEN");
    }

    @Override
    public String toString() {
        return String.format("%-4s %-25s %-16s Rp%-10.0f %d %s",
                id, name, gameCategory, registrationFee, maxQuota, status);
    }
}