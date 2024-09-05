package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChambreTest {

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        // Initialisation de l'objet Chambre avant chaque test
        chambre = new Chambre("Simple", 100.0, true);
    }

    @Test
    void testChambreCreation() {
        // Vérifie que la chambre est correctement initialisée
        assertEquals("Simple", chambre.getType());
        assertEquals(100.0, chambre.getPrix());
        assertTrue(chambre.isDisponible());
    }

    @Test
    void testChambreSetters() {
        // Modifie les attributs de la chambre
        chambre.setType("Double");
        chambre.setPrix(150.0);
        chambre.setDisponible(false);

        // Vérifie que les setters ont bien modifié les attributs
        assertEquals("Double", chambre.getType());
        assertEquals(150.0, chambre.getPrix());
        assertFalse(chambre.isDisponible());
    }

    @Test
    void testToString() {
        // Vérifie la sortie de la méthode toString
        String expected = "Chambre{type='Simple', prix=100.0, disponible=true}";
        assertEquals(expected, chambre.toString());
    }
}
