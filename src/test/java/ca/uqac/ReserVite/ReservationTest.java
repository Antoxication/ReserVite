package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private Client client;
    private Chambre chambre;
    private Reservation reservation;
    private Date dateArrivee;
    private Date dateDepart;

    @BeforeEach
    void setUp() {
        client = new Client("John Doe", "123 Main St", "john@example.com", "555-1234");
        chambre = new Chambre("Simple", 100.0, true);
        dateArrivee = new Date(); // Mettre la date actuelle pour simplifier
        dateDepart = new Date(dateArrivee.getTime() + (1000 * 60 * 60 * 24)); // +1 jour
        reservation = new Reservation(client, chambre, dateArrivee, dateDepart);
    }

    @Test
    void testGetClient() {
        assertEquals(client, reservation.getClient());
    }

    @Test
    void testSetClient() {
        Client newClient = new Client("Jane Smith", "456 Oak St", "jane@example.com", "555-5678");
        reservation.setClient(newClient);
        assertEquals(newClient, reservation.getClient());
    }

    @Test
    void testGetChambre() {
        assertEquals(chambre, reservation.getChambre());
    }

    @Test
    void testSetChambre() {
        Chambre newChambre = new Chambre("Double", 150.0, true);
        reservation.setChambre(newChambre);
        assertEquals(newChambre, reservation.getChambre());
    }

    @Test
    void testGetDateArrivee() {
        assertEquals(dateArrivee, reservation.getDateArrivee());
    }

    @Test
    void testSetDateArrivee() {
        Date newDateArrivee = new Date(dateArrivee.getTime() + (1000 * 60 * 60 * 48)); // +2 jours
        reservation.setDateArrivee(newDateArrivee);
        assertEquals(newDateArrivee, reservation.getDateArrivee());
    }

    @Test
    void testGetDateDepart() {
        assertEquals(dateDepart, reservation.getDateDepart());
    }

    @Test
    void testSetDateDepart() {
        Date newDateDepart = new Date(dateDepart.getTime() + (1000 * 60 * 60 * 48)); // +2 jours
        reservation.setDateDepart(newDateDepart);
        assertEquals(newDateDepart, reservation.getDateDepart());
    }

    @Test
    void testAnnuler() {
        reservation.annuler();
        assertTrue(reservation.getChambre().isDisponible());
    }

    @Test
    void testToString() {
        String expected = "Reservation{client=" + client.toString() + ", chambre=" + chambre.toString() +
                ", dateArrivee=" + dateArrivee + ", dateDepart=" + dateDepart + "}";
        assertEquals(expected, reservation.toString());
    }
}
