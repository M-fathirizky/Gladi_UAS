package testing;

import model.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tournament Model Tests")
class TournamentTest {

    private Tournament tournament;

    @BeforeEach
    void setUp() {
        tournament = new Tournament("T01", "Valorant Cup 2026", "Valorant", 50000, 4);
    }

    @Test
    @DisplayName("Tournament dibuat dengan nilai awal yang benar")
    void testInitialValues() {
        assertEquals("T01", tournament.getId());
        assertEquals("Valorant Cup 2026", tournament.getName());
        assertEquals("Valorant", tournament.getGameCategory());
        assertEquals(50000, tournament.getRegistrationFee());
        assertEquals(4, tournament.getMaxQuota());
        assertEquals(0, tournament.getCurrentQuota());
        assertEquals("OPEN", tournament.getStatus());
    }

    @Test
    @DisplayName("getRemainingSlots mengembalikan sisa slot yang benar")
    void testGetRemainingSlots() {
        assertEquals(4, tournament.getRemainingSlots());
        tournament.incrementQuota();
        assertEquals(3, tournament.getRemainingSlots());
    }

    @Test
    @DisplayName("isOpen mengembalikan true saat status OPEN")
    void testIsOpenWhenOpen() {
        assertTrue(tournament.isOpen());
    }

    @Test
    @DisplayName("Status berubah menjadi FULL saat kuota penuh")
    void testStatusChangesToFullWhenQuotaReached() {
        tournament.incrementQuota();
        tournament.incrementQuota();
        tournament.incrementQuota();
        assertEquals("OPEN", tournament.getStatus());

        tournament.incrementQuota();
        assertEquals("FULL", tournament.getStatus());
        assertFalse(tournament.isOpen());
    }

    @Test
    @DisplayName("setStatus mengubah status dengan benar")
    void testSetStatus() {
        tournament.setStatus("CLOSED");
        assertEquals("CLOSED", tournament.getStatus());
        assertFalse(tournament.isOpen());

        tournament.setStatus("OPEN");
        assertTrue(tournament.isOpen());
    }

    @Test
    @DisplayName("Setter mengupdate nilai dengan benar")
    void testSetters() {
        tournament.setName("Valorant Masters");
        tournament.setGameCategory("Valorant 2");
        tournament.setRegistrationFee(75000);
        tournament.setMaxQuota(8);

        assertEquals("Valorant Masters", tournament.getName());
        assertEquals("Valorant 2", tournament.getGameCategory());
        assertEquals(75000, tournament.getRegistrationFee());
        assertEquals(8, tournament.getMaxQuota());
    }
}