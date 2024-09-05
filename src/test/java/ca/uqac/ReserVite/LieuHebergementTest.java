package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class LieuHebergementTest {

    private LieuHebergement lieuHebergement;
    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    public void setUp() {
        // Initialisation de LieuHebergement et de quelques Chambres
        lieuHebergement = new LieuHebergement("Hôtel", "Le Grand Palace", "123 Rue du Paradis");
        chambre1 = new Chambre("Simple", 80.0, true);
        chambre2 = new Chambre("Double", 120.0, true);
    }

    @Test
    public void testGetType() {
        assertEquals("Hôtel", lieuHebergement.getType());
    }

    @Test
    public void testSetType() {
        lieuHebergement.setType("Auberge");
        assertEquals("Auberge", lieuHebergement.getType());
    }

    @Test
    public void testGetNom() {
        assertEquals("Le Grand Palace", lieuHebergement.getNom());
    }

    @Test
    public void testSetNom() {
        lieuHebergement.setNom("Petit Palace");
        assertEquals("Petit Palace", lieuHebergement.getNom());
    }

    @Test
    public void testGetAdresse() {
        assertEquals("123 Rue du Paradis", lieuHebergement.getAdresse());
    }

    @Test
    public void testSetAdresse() {
        lieuHebergement.setAdresse("456 Avenue du Soleil");
        assertEquals("456 Avenue du Soleil", lieuHebergement.getAdresse());
    }

    @Test
    public void testAjouterChambre() {
        lieuHebergement.ajouterChambre(chambre1);
        List<Chambre> chambres = lieuHebergement.getChambres();
        assertEquals(1, chambres.size());
        assertEquals(chambre1, chambres.get(0));
    }

    @Test
    public void testGetChambres() {
        lieuHebergement.ajouterChambre(chambre1);
        lieuHebergement.ajouterChambre(chambre2);
        List<Chambre> chambres = lieuHebergement.getChambres();
        assertEquals(2, chambres.size());
        assertTrue(chambres.contains(chambre1));
        assertTrue(chambres.contains(chambre2));
    }

    @Test
    public void testToString() {
        String expected = "LieuHebergement{type='Hôtel', nom='Le Grand Palace', adresse='123 Rue du Paradis', chambres=[]}";
        assertEquals(expected, lieuHebergement.toString());
    }
}
