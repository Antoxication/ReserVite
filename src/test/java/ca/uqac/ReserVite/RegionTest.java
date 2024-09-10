package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionTest {

    private Region region;

    @BeforeEach
    void setUp() {
        // Initialisation de la région avec toutes les informations
        region = new Region("Canada", "Québec", "Saguenay", "Chicoutimi", "123 Rue Principale");
    }

    @Test
    void testRegionComplete() {
        // Vérification que les attributs sont bien définis
        assertEquals("Canada", region.pays());
        assertEquals("Québec", region.province());
        assertEquals("Saguenay", region.ville());
        assertEquals("Chicoutimi", region.quartier());
        assertEquals("123 Rue Principale", region.rue());
    }

    @Test
    void testRegionPartielle() {
        // Test avec des attributs chaîne vide (indifférent)
        Region regionPartielle = new Region("Canada", "", "", "", "123 Rue Principale");
        assertEquals("Canada", regionPartielle.pays());
        assertEquals(regionPartielle.province(), "");
        assertEquals(regionPartielle.ville(), "");
        assertEquals(regionPartielle.quartier(), "");
        assertEquals("123 Rue Principale", regionPartielle.rue());
    }

    @Test
    void testToStringComplete() {
        // Test de la méthode toString pour une région complète
        String expected = "Region{pays='Canada', province='Québec', ville='Saguenay', quartier='Chicoutimi', rue='123 Rue Principale'}";
        assertEquals(expected, region.toString());
    }

    @Test
    void testToStringIndifferent() {
        // Test de la méthode toString pour une région avec des champs indifférents
        Region regionIndifferent = new Region("", "", "", "", "");
        String expected = "Region{pays='Indifférent', province='Indifférent', ville='Indifférent', quartier='Indifférent', rue='Indifférent'}";
        assertEquals(expected, regionIndifferent.toString());
    }
}
