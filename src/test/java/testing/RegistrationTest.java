package testing;

import model.Registration;
import model.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Registration Model Tests")
class RegistrationTest {

    private Tournament tournament;
    private Registration registration;

    @BeforeEach
    void setUp() {
        tournament = new Tournament("T02", "FC 25 Campus League", "EA FC 25", 35000, 32);
        registration = new Registration("TKT-0001", "Player01", tournament);
    }

    @Test
    @DisplayName("Registration dibuat dengan data turnamen yang benar")
    void testRegistrationCreation() {
        assertEquals("TKT-0001", registration.getTicketId());
        assertEquals("Player01", registration.getUsername());
        assertEquals("T02", registration.getTournamentId());
        assertEquals("FC 25 Campus League", registration.getTournamentName());
        assertEquals("EA FC 25", registration.getGameCategory());
        assertEquals(35000, registration.getFee());
    }

    @Test
    @DisplayName("getFormattedDate tidak null dan tidak kosong")
    void testFormattedDateNotEmpty() {
        String date = registration.getFormattedDate();
        assertNotNull(date);
        assertFalse(date.isBlank());
    }

    @Test
    @DisplayName("getFormattedDate menggunakan format dd-MM-yyyy HH:mm")
    void testFormattedDateFormat() {
        String date = registration.getFormattedDate();
        assertTrue(date.contains("-"));
        assertTrue(date.contains(":"));
        assertEquals(16, date.length());
    }
}