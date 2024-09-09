package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private Client client;
    private Chambre chambre;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        client = new Client("John Doe", "123 rue A", "john@example.com", "1234567890");
        chambre = new Chambre(TypeChambre.SIMPLE, 100.0, true);
        dateArrivee = LocalDate.now();
        dateDepart = dateArrivee.plusDays(1);  // +1 jour
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
                ", chambre=Chambre{type=SIMPLE, prix=100.0, disponible=true}" +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                '}';
        assertEquals(expected, reservation.toString());
    }
}
