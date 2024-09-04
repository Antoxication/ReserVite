package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private Client client;

    @BeforeEach
    void setUp() {
        // Initialisation de l'objet Client avant chaque test
        client = new Client("Jean Dupont", "123 Rue Principale", "jean.dupont@example.com", "555-1234");
    }

    @Test
    void testClientCreation() {
        // Vérifie que le client est correctement initialisé
        assertEquals("Jean Dupont", client.getNom());
        assertEquals("123 Rue Principale", client.getAdresse());
        assertEquals("jean.dupont@example.com", client.getCourriel());
        assertEquals("555-1234", client.getTelephone());
    }

    @Test
    void testClientSetters() {
        // Modifie les attributs du client
        client.setNom("Marie Dupont");
        client.setAdresse("456 Avenue Secondaire");
        client.setCourriel("marie.dupont@example.com");
        client.setTelephone("555-5678");

        // Vérifie que les setters ont bien modifié les attributs
        assertEquals("Marie Dupont", client.getNom());
        assertEquals("456 Avenue Secondaire", client.getAdresse());
        assertEquals("marie.dupont@example.com", client.getCourriel());
        assertEquals("555-5678", client.getTelephone());
    }

    @Test
    void testToString() {
        // Vérifie la sortie de la méthode toString
        String expected = "Client{nom='Jean Dupont', adresse='123 Rue Principale', courriel='jean.dupont@example.com', telephone='555-1234'}";
        assertEquals(expected, client.toString());
    }
}
