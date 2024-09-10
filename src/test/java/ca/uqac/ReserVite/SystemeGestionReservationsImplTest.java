package ca.uqac.ReserVite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemeGestionReservationsImplTest {

    private SystemeGestionReservationsImpl systeme;
    private Region region;
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
        region = new Region("Canada", "QC", "Montréal", "Quartier A", "Rue B");
        lieu = new LieuHebergement("Hotel California", TypeLieuHebergement.HOTEL, region, List.of(Service.PISCINE_INTERIEURE, Service.RESTAURANT), List.of(chambre));
        dateArrivee = LocalDate.now().plusDays(2);
        dateDepart = dateArrivee.plusDays(4);
        systeme.ajouterLieu(lieu);
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

    @Test
    public void testRecueillirBesoinsClient() {
        String simulatedInput = "H\nCanada\nQC\nMontréal\nQuartier A\nRue B\nS\nI,R\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        BesoinsClient besoins = systeme.recueillirBesoinsClient();

        // Vérification des valeurs
        assertEquals(TypeLieuHebergement.HOTEL, besoins.typeLieuHebergement());
        assertEquals("Canada", besoins.region().pays());
        assertEquals("QC", besoins.region().province());
        assertEquals(TypeChambre.SIMPLE, besoins.typeChambre());
        assertTrue(besoins.services().contains(Service.PISCINE_INTERIEURE));
        assertTrue(besoins.services().contains(Service.RESTAURANT));

        System.setIn(System.in); // Réinitialisation de System.in
    }

    @Test
    public void testRecueillirBesoinsClientChambreDouble() {
        String simulatedInput = "\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\nD\n\"\"\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        BesoinsClient besoins = systeme.recueillirBesoinsClient();

        // Vérification des valeurs
        assertEquals(TypeChambre.DOUBLE, besoins.typeChambre());

        System.setIn(System.in); // Réinitialisation de System.in
    }

    @Test
    public void testRecueillirBesoinsClientChambreSuite() {
        String simulatedInput = "\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\nU\n\"\"\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        BesoinsClient besoins = systeme.recueillirBesoinsClient();

        // Vérification des valeurs
        assertEquals(TypeChambre.SUITE, besoins.typeChambre());

        System.setIn(System.in); // Réinitialisation de System.in
    }

    @Test
    public void testRecueillirBesoinsClientMotel() {
        String simulatedInput = "M\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        BesoinsClient besoins = systeme.recueillirBesoinsClient();

        // Vérification des valeurs
        assertEquals(TypeLieuHebergement.MOTEL, besoins.typeLieuHebergement());

        System.setIn(System.in); // Réinitialisation de System.in
    }

    @Test
    public void testRecueillirBesoinsClientCouetteEtCafe() {
        String simulatedInput = "C\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n\"\"\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        BesoinsClient besoins = systeme.recueillirBesoinsClient();

        // Vérification des valeurs
        assertEquals(TypeLieuHebergement.COUETTE_ET_CAFE, besoins.typeLieuHebergement());

        System.setIn(System.in); // Réinitialisation de System.in
    }

    @Test
    public void testTrouverLieuxDisponibles() {

        // Cas où le client est indifférent sur tout
        BesoinsClient besoins = new BesoinsClient(TypeLieuHebergement.INDIFFERENT, new Region("", "", "", "", ""), TypeChambre.INDIFFERENT, List.of(Service.PISCINE_INTERIEURE));
        List<LieuHebergement> lieux = systeme.trouverLieuxDisponibles(besoins.typeLieuHebergement(), besoins.region(), besoins.typeChambre(), besoins.services());

        assertEquals(1, lieux.size());
        assertEquals(lieu, lieux.get(0)); // Lieu correspond aux critères

        // Cas où le client est spécifique sur tout
        besoins = new BesoinsClient(TypeLieuHebergement.HOTEL, region, TypeChambre.SIMPLE, List.of(Service.PISCINE_INTERIEURE));
        lieux = systeme.trouverLieuxDisponibles(besoins.typeLieuHebergement(), besoins.region(), besoins.typeChambre(), besoins.services());

        assertEquals(1, lieux.size());
        assertEquals(lieu, lieux.get(0)); // Lieu correspond aux critères
    }

    @Test
    public void testLieuNonAjouteConditionsNonRemplies() {

        // Besoins du client qui ne correspondent pas aux conditions du lieu
        BesoinsClient besoins = new BesoinsClient(
            TypeLieuHebergement.COUETTE_ET_CAFE,  // Le type de lieu est différent
            new Region("France", "N/A", "Nîmes", "Quartier B", "Rue C"),  // La région est différente
            TypeChambre.SUITE,  // Le type de chambre n'existe pas dans le lieu
            List.of(Service.RESTAURANT)
        );

        List<LieuHebergement> lieuxDisponibles = systeme.trouverLieuxDisponibles(
            besoins.typeLieuHebergement(), 
            besoins.region(), 
            besoins.typeChambre(), 
            besoins.services()
        );

        // Vérifier que le lieu n'est pas ajouté, car aucune condition n'est remplie
        assertTrue(lieuxDisponibles.isEmpty(), "Le lieu ne doit pas être ajouté si aucune condition n'est remplie.");
    }
}
