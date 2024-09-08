package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LieuHebergementTest {

    private LieuHebergement lieuHebergement;
    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    public void setUp() {
        // Initialisation du LieuHebergement avec des services généraux et quelques Chambres
    lieuHebergement = new LieuHebergement(
        TypeLieuHebergement.HOTEL,
        "Le Grand Palace",
        "123 Rue du Paradis",
        Arrays.asList(Service.PISCINE_INTERIEURE, Service.SALLE_DE_CONDITIONNEMENT_PHYSIQUE)
    );
        chambre1 = new Chambre(TypeChambre.SIMPLE, 80.0, true, "Nord");
        chambre2 = new Chambre(TypeChambre.DOUBLE, 120.0, true, "Sud");
        lieuHebergement.ajouterChambre(chambre1);
        lieuHebergement.ajouterChambre(chambre2);
    }

    @Test
    public void testGetType() {
        assertEquals(TypeLieuHebergement.HOTEL, lieuHebergement.getType());
    }

    @Test
    public void testSetType() {
        lieuHebergement.setType(TypeLieuHebergement.COUETTE_ET_CAFE);
        assertEquals(TypeLieuHebergement.COUETTE_ET_CAFE, lieuHebergement.getType());
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
    public void testGetChambres() {
        List<Chambre> chambres = lieuHebergement.getChambres();
        assertTrue(chambres.contains(chambre1));
        assertTrue(chambres.contains(chambre2));
    }

    @Test
    public void testGetServicesGeneraux() {
        List<Service> services = lieuHebergement.getServicesGeneraux();
        assertTrue(services.contains(Service.PISCINE_INTERIEURE));
        assertTrue(services.contains(Service.SALLE_DE_CONDITIONNEMENT_PHYSIQUE));
    }

    @Test
    public void testSetServicesGeneraux() {
        // Initialisation d'une nouvelle liste de services
        List<Service> nouveauxServices = Arrays.asList(Service.PISCINE_INTERIEURE, Service.SALLE_DE_CONDITIONNEMENT_PHYSIQUE);

        // Modification des services généraux
        lieuHebergement.setServicesGeneraux(nouveauxServices);

        // Vérification que les services généraux ont bien été modifiés
        assertEquals(nouveauxServices, lieuHebergement.getServicesGeneraux());
    }

    @Test
    public void testToString() {
        String expected = "LieuHebergement{type=HOTEL, nom='Le Grand Palace', adresse='123 Rue du Paradis', chambres=[Chambre{type=SIMPLE, prix=80.0, disponible=true, region='Nord'}, Chambre{type=DOUBLE, prix=120.0, disponible=true, region='Sud'}], servicesGeneraux=[PISCINE_INTERIEURE, SALLE_DE_CONDITIONNEMENT_PHYSIQUE]}";
        assertEquals(expected, lieuHebergement.toString());
    }

}
