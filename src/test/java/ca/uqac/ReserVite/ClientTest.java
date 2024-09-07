package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client("John Doe", "123 Main St", "john@example.com", "555-1234");
    }

    @Test
    public void testClientCreation() {
        assertEquals("John Doe", client.nom());
        assertEquals("123 Main St", client.adresse());
        assertEquals("john@example.com", client.courriel());
        assertEquals("555-1234", client.telephone());
    }

    @Test
    public void testToString() {
        String expected = "Client: John Doe\n" +
                        "Adresse: 123 Main St\n" +
                        "Courriel: john@example.com\n" +
                        "Téléphone: 555-1234";
        assertEquals(expected, client.toString());
    }
}
