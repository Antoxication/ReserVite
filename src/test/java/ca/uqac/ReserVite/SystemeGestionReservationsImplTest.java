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
    public void setUp() {
        systeme = new SystemeGestionReservationsImpl();
        client = new Client("John Doe", "123 Main St", "john@example.com", "555-1234");
        chambre = new Chambre(TypeChambre.SIMPLE, 100.0, true, "Nord");
        lieu = new LieuHebergement(TypeLieuHebergement.HOTEL, "Hotel California", "456 Oak St", List.of(Service.PISCINE_INTERIEURE, Service.RESTAURANT));
        lieu.ajouterChambre(chambre);
        dateArrivee = new Date();
        dateDepart = new Date(dateArrivee.getTime() + (1000 * 60 * 60 * 24));  // +1 jour
    }

    @Test
    public void testReserver() {
        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);
        assertNotNull(reservation);
        assertEquals(client, reservation.client());
        assertEquals(chambre, reservation.chambre());
        assertFalse(chambre.isDisponible());
    }

    @Test
    public void testAnnulerReservation() {
        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);
        assertNotNull(reservation);

        systeme.annulerReservation(reservation);
        assertTrue(reservation.chambre().isDisponible());
    }

    @Test
    public void testTrouverChambresDisponibles() {
        List<Chambre> chambresDispo = systeme.trouverChambresDisponibles(lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);
        assertNotNull(chambresDispo);
        assertTrue(chambresDispo.contains(chambre));
    }

    @Test
    public void testReserverAucuneChambreDisponible() {
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);
        List<Chambre> chambresDispo = systeme.trouverChambresDisponibles(lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);
        assertTrue(chambresDispo.isEmpty());
    }

    @Test
    void testAucuneChambreCorrespondante() {
        List<Chambre> chambresDispo = systeme.trouverChambresDisponibles(lieu, TypeChambre.DOUBLE, dateArrivee, dateDepart); // Type DOUBLE n'existe pas
        assertTrue(chambresDispo.isEmpty());
    }

    @Test
    void testReserverSeuleChambreIndisponible() {
        // Réserver la seule chambre disponible
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);
        
        // Essayer de réserver à nouveau alors qu'il n'y a plus de chambre disponible
        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);
        
        // Vérifier que la réservation est null car aucune chambre n'est disponible
        assertNull(reservation, "Aucune chambre ne devrait être disponible");
    }
}
