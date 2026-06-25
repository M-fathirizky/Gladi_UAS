package testing;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Model Tests")
class UserTest {

    @Test
    @DisplayName("User ADMIN dibuat dengan nilai yang benar")
    void testAdminUser() {
        User admin = new User("admin", "admin123", "ADMIN");
        assertEquals("admin", admin.getUsername());
        assertEquals("admin123", admin.getPassword());
        assertEquals("ADMIN", admin.getRole());
        assertTrue(admin.isAdmin());
    }

    @Test
    @DisplayName("User PLAYER dibuat dengan nilai yang benar")
    void testPlayerUser() {
        User player = new User("Player01", "pass01", "PLAYER");
        assertEquals("Player01", player.getUsername());
        assertEquals("pass01", player.getPassword());
        assertEquals("PLAYER", player.getRole());
        assertFalse(player.isAdmin());
    }

    @Test
    @DisplayName("isAdmin false untuk PLAYER, true untuk ADMIN")
    void testIsAdmin() {
        assertFalse(new User("p", "p", "PLAYER").isAdmin());
        assertTrue(new User("a", "a", "ADMIN").isAdmin());
    }
}