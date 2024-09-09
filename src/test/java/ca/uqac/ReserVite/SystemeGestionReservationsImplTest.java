package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemeGestionReservationsImplTest {

    private SystemeGestionReservationsImpl systeme;
    private Client client;
    private Chambre chambre;
    private LieuHebergement lieu;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;

    @BeforeEach
    public void setUp() {
        systeme = new SystemeGestionReservationsImpl();
        client = new Client("John Doe", "123 Main St", "john@example.com", "555-1234");
        chambre = new Chambre(TypeChambre.SIMPLE, 100.0, true);
        Region region = new Region("Canada", "Québec", "Montréal", "Quartier A", "Rue B");
        lieu = new LieuHebergement(TypeLieuHebergement.HOTEL, "Hotel California", List.of(Service.PISCINE_INTERIEURE, Service.RESTAURANT), region);
        lieu.ajouterChambre(chambre);
        dateArrivee = LocalDate.now().plusDays(2);
        dateDepart = dateArrivee.plusDays(4);
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

    @Test
    public void testReserverAvecChevauchementDateArrivee() {
        // Première réservation
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);

        // Tentative de deuxième réservation avec une date d'arrivée qui chevauche la première
        LocalDate nouvelleDateArrivee = dateArrivee.minusDays(1);  // Commence un jour avant la première
        LocalDate nouvelleDateDepart = dateDepart;  // Même date de départ
        Reservation reservationChevauchante = systeme.reserver(client, lieu, TypeChambre.SIMPLE, nouvelleDateArrivee, nouvelleDateDepart);

        assertNull(reservationChevauchante, "La réservation ne doit pas être possible avec des dates qui chevauchent une réservation existante.");
    }

    @Test
    public void testReserverAvecChevauchementDateDepart() {
        // Première réservation
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);

        // Tentative de deuxième réservation avec une date de départ qui chevauche la première
        LocalDate nouvelleDateArrivee = dateDepart.minusDays(1);  // Commence avant la fin de la première
        LocalDate nouvelleDateDepart = dateDepart.plusDays(1);  // Se termine après la première
        Reservation reservationChevauchante = systeme.reserver(client, lieu, TypeChambre.SIMPLE, nouvelleDateArrivee, nouvelleDateDepart);

        assertNull(reservationChevauchante, "La réservation ne doit pas être possible avec des dates qui chevauchent une réservation existante.");
    }

    @Test
    public void testReserverAvecChevauchementComplet() {
        // Première réservation
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);

        // Tentative de deuxième réservation qui commence avant et se termine après la première
        LocalDate nouvelleDateArrivee = dateArrivee.minusDays(1);  // Commence un jour avant la première
        LocalDate nouvelleDateDepart = dateDepart.plusDays(1);  // Se termine un jour après la première
        Reservation reservationChevauchante = systeme.reserver(client, lieu, TypeChambre.SIMPLE, nouvelleDateArrivee, nouvelleDateDepart);

        assertNull(reservationChevauchante, "La réservation ne doit pas être possible avec des dates qui chevauchent entièrement une réservation existante.");
    }

    @Test
    public void testReserverApresReservationExistante() {
        // Première réservation
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);

        // Tentative de deuxième réservation avec des dates entièrement après une réservation existante
        LocalDate nouvelleDateArrivee = dateDepart.plusDays(1);  // Commence après la première
        LocalDate nouvelleDateDepart = nouvelleDateArrivee.plusDays(1);  // +1 jour
        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, nouvelleDateArrivee, nouvelleDateDepart);

        assertNotNull(reservation, "La réservation devrait être possible car les dates sont après une réservation existante.");
        assertEquals(nouvelleDateArrivee, reservation.dateArrivee());
        assertEquals(nouvelleDateDepart, reservation.dateDepart());
    }

    // Vérifier que la réservation est possible si les dates sont avant une réservation existante
    @Test
    public void testReserverAvantReservationExistante() {
        // Première réservation
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);

        // Tentative de deuxième réservation avec des dates entièrement avant une réservation existante
        LocalDate nouvelleDateArrivee = dateArrivee.minusDays(2);  // Commence avant la première
        LocalDate nouvelleDateDepart = dateArrivee.minusDays(1);  // Se termine avant la première
        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, nouvelleDateArrivee, nouvelleDateDepart);

        assertNotNull(reservation, "La réservation devrait être possible car les dates sont avant une réservation existante.");
        assertEquals(nouvelleDateArrivee, reservation.dateArrivee());
        assertEquals(nouvelleDateDepart, reservation.dateDepart());
    }

    @Test
    public void testReserverPourMoinsDUneNuit() {
        // Tentative de réservation avec date d'arrivée et de départ le même jour
        LocalDate dateArriveeEtDepart = LocalDate.now(); // Même jour pour arrivée et départ

        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArriveeEtDepart, dateArriveeEtDepart);

        // Vérifier que la réservation est null car elle est pour moins d'une nuit
        assertNull(reservation, "La réservation ne doit pas être possible pour moins d'une nuit.");
    }

    @Test
    public void testReserverDateArriveeApresDateDepart() {
        // Tentative de réservation avec une date d'arrivée après la date de départ
        LocalDate mauvaiseDateArrivee = dateDepart.plusDays(1); // Date d'arrivée après la date de départ

        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, mauvaiseDateArrivee, dateDepart);

        // Vérifier que la réservation est null car la date d'arrivée est après la date de départ
        assertNull(reservation, "La réservation ne doit pas être possible si la date d'arrivée est après la date de départ.");
    }

    @Test
    public void testReservationPourAutreChambreMemeType() {
        // Créer une autre chambre du même type (SIMPLE)
        Chambre autreChambre = new Chambre(TypeChambre.SIMPLE, 120.0, true);
        lieu.ajouterChambre(autreChambre);

        // Faire une réservation pour la première chambre (SIMPLE)
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);

        // Faire une nouvelle réservation pour une autre chambre du même type (SIMPLE) et pour les mêmes dates
        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee, dateDepart);

        assertNotNull(reservation, "La réservation pour l'autre chambre du même type doit être possible.");
        assertEquals(autreChambre, reservation.chambre(), "La réservation doit être pour l'autre chambre SIMPLE.");
    }

    @Test
    public void testReserverDatesContigues() {
        // Première réservation du 10 au 15
        LocalDate dateArrivee1 = LocalDate.now();
        LocalDate dateDepart1 = dateArrivee1.plusDays(5);
        systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee1, dateDepart1);

        // Nouvelle réservation du 15 au 20 (dates contiguës)
        LocalDate dateArrivee2 = dateDepart1; // Commence le jour où l'autre se termine
        LocalDate dateDepart2 = dateArrivee2.plusDays(5);

        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArrivee2, dateDepart2);

        assertNotNull(reservation, "La réservation devrait être possible car les dates sont contiguës sans chevauchement.");
        assertEquals(dateArrivee2, reservation.dateArrivee());
        assertEquals(dateDepart2, reservation.dateDepart());
    }

    // Vérifier que la réservation ne soit pas possible si la date d'arrivée est dans le passé
    @Test
    public void testReserverDateArriveeDansLePasse() {
        // Date d'arrivée dans le passé
        LocalDate dateArriveePasse = LocalDate.now().minusDays(1);
        Reservation reservation = systeme.reserver(client, lieu, TypeChambre.SIMPLE, dateArriveePasse, dateDepart);

        assertNull(reservation, "La réservation ne doit pas être possible si la date d'arrivée est dans le passé.");
    }
}
