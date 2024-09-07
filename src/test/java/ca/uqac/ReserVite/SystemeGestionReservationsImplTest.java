package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemeGestionReservationsImplTest {

    private SystemeGestionReservationsImpl systeme;
    private Client client;
    private Chambre chambre;
    private LieuHebergement lieu;
    private Date dateArrivee;
    private Date dateDepart;

    @BeforeEach
    void setUp() {
        systeme = new SystemeGestionReservationsImpl();
        client = new Client("John Doe", "123 Main St", "john@example.com", "555-1234");
        chambre = new Chambre("Simple", 100.0, true);
        lieu = new LieuHebergement("Hotel", "Hotel California", "456 Oak St");
        lieu.ajouterChambre(chambre);
        dateArrivee = new Date();
        dateDepart = new Date(dateArrivee.getTime() + (1000 * 60 * 60 * 24)); // +1 jour
    }

    @Test
    void testReserver() {
        Reservation reservation = systeme.reserver(client, lieu, "Simple", dateArrivee, dateDepart);
        assertNotNull(reservation);
        assertEquals(client, reservation.client());
        assertEquals(chambre, reservation.chambre());
        assertFalse(chambre.isDisponible());
    }

    @Test
    void testAnnulerReservation() {
        Reservation reservation = systeme.reserver(client, lieu, "Simple", dateArrivee, dateDepart);
        assertNotNull(reservation);

        systeme.annulerReservation(reservation);
        assertTrue(reservation.chambre().isDisponible());
    }

    @Test
    void testTrouverChambresDisponibles() {
        List<Chambre> chambresDispo = systeme.trouverChambresDisponibles(lieu, "Simple", dateArrivee, dateDepart);
        assertNotNull(chambresDispo);
        assertTrue(chambresDispo.contains(chambre));
    }

    @Test
    void testReserverAucuneChambreDisponible() {
        systeme.reserver(client, lieu, "Simple", dateArrivee, dateDepart);
        List<Chambre> chambresDispo = systeme.trouverChambresDisponibles(lieu, "Simple", dateArrivee, dateDepart);
        assertTrue(chambresDispo.isEmpty());
    }

    @Test
    void testAucuneChambreCorrespondante() {
        List<Chambre> chambresDispo = systeme.trouverChambresDisponibles(lieu, "Double", dateArrivee, dateDepart); // Type "Double" n'existe pas
        assertTrue(chambresDispo.isEmpty());
    }

    @Test
    void testReserverSeuleChambreIndisponible() {
        // Réserver la seule chambre disponible
        systeme.reserver(client, lieu, "Simple", dateArrivee, dateDepart);
        
        // Essayer de réserver à nouveau alors qu'il n'y a plus de chambre disponible
        Reservation reservation = systeme.reserver(client, lieu, "Simple", dateArrivee, dateDepart);
        
        // Vérifier que la réservation est null car aucune chambre n'est disponible
        assertNull(reservation, "Aucune chambre ne devrait être disponible");
    }
}
