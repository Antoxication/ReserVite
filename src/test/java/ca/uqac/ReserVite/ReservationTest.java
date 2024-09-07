package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    private Client client;
    private Chambre chambre;
    private Date dateArrivee;
    private Date dateDepart;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        client = new Client("John Doe", "123 rue A", "john@example.com", "1234567890");
        chambre = new Chambre("Simple", 100.0, true);
        dateArrivee = new Date();
        dateDepart = new Date(dateArrivee.getTime() + (1000 * 60 * 60 * 24)); // +1 jour
        reservation = new Reservation(client, chambre, dateArrivee, dateDepart);
    }

    @Test
    public void testReservationCreation() {
        assertEquals(client, reservation.client());
        assertEquals(chambre, reservation.chambre());
        assertEquals(dateArrivee, reservation.dateArrivee());
        assertEquals(dateDepart, reservation.dateDepart());
    }

    @Test
    public void testAnnulerReservation() {
        reservation.annuler();
        assertTrue(chambre.isDisponible());  // Vérifie que la chambre est disponible après annulation
    }

    @Test
    public void testToString() {
        String expected = "Reservation{" +
                "client=" + client +
                ", chambre=" + chambre +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                '}';
        assertEquals(expected, reservation.toString());
    }
}
