package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LieuHebergementTest {

    private LieuHebergement lieuHebergement;
    private Region region;

    @BeforeEach
    void setUp() {
        // Initialisation du LieuHebergement avec des services généraux et quelques Chambres
        region = new Region("Canada", "Québec", "Saguenay", "Chicoutimi", "123 Rue Principale");
        Chambre chambre1 = new Chambre(TypeChambre.SIMPLE, 80.0, true);
        Chambre chambre2 = new Chambre(TypeChambre.DOUBLE, 120.0, true);
        lieuHebergement = new LieuHebergement(
            "Le Grand Palace",
            TypeLieuHebergement.HOTEL,
            region,
            Arrays.asList(Service.PISCINE_INTERIEURE, Service.SALLE_DE_CONDITIONNEMENT_PHYSIQUE),
            Arrays.asList(chambre1, chambre2)
        );
    }

    @Test
    void testGetRegion() {
        // Vérifie que le getter de région fonctionne correctement
        assertEquals(region, lieuHebergement.getRegion());
    }

    @Test
    void testSetRegion() {
        // Change la région et vérifie que la mise à jour fonctionne
        Region nouvelleRegion = new Region("États-Unis", "Californie", "San Francisco", "Downtown", "456 Rue du Soleil");
        lieuHebergement.setRegion(nouvelleRegion);
        assertEquals(nouvelleRegion, lieuHebergement.getRegion());
    }

    @Test
    void testGetType() {
        assertEquals(TypeLieuHebergement.HOTEL, lieuHebergement.getType());
    }

    @Test
    void testSetType() {
        lieuHebergement.setType(TypeLieuHebergement.HOTEL);
        assertEquals(TypeLieuHebergement.HOTEL, lieuHebergement.getType());
    }

    @Test
    void testGetNom() {
        assertEquals("Le Grand Palace", lieuHebergement.getNom());
    }

    @Test
    void testSetNom() {
        lieuHebergement.setNom("Petit Palace");
        assertEquals("Petit Palace", lieuHebergement.getNom());
    }

    @Test
    void testGetChambres() {
        List<Chambre> chambres = lieuHebergement.getChambres();
        assertEquals(2, chambres.size());
    }

    @Test
    public void testGetServices() {
        List<Service> services = lieuHebergement.getServices();
        assertTrue(services.contains(Service.PISCINE_INTERIEURE));
        assertTrue(services.contains(Service.SALLE_DE_CONDITIONNEMENT_PHYSIQUE));
    }

    @Test
    public void testSetServices() {
        // Initialisation d'une nouvelle liste de services
        List<Service> nouveauxServices = Arrays.asList(Service.PISCINE_INTERIEURE, Service.SALLE_DE_CONDITIONNEMENT_PHYSIQUE);

        // Modification des services généraux
        lieuHebergement.setServices(nouveauxServices);

        // Vérification que les services ont bien été modifiés
        assertEquals(nouveauxServices, lieuHebergement.getServices());
    }

    @Test
    void testToString() {
        String expected = "LieuHebergement{type=HOTEL, nom='Le Grand Palace', chambres=[Chambre{type=SIMPLE, prix=80.0, disponible=true}, Chambre{type=DOUBLE, prix=120.0, disponible=true}], servicesGeneraux=[PISCINE_INTERIEURE, SALLE_DE_CONDITIONNEMENT_PHYSIQUE], region=Region{pays='Canada', province='Québec', ville='Saguenay', quartier='Chicoutimi', rue='123 Rue Principale'}}";
        assertEquals(expected, lieuHebergement.toString());
    }
}
