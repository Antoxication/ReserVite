package ca.uqac.ReserVite;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemeGestionReservationsImpl implements SystemeGestionReservations {
    private List<Reservation> reservations;
    private List<LieuHebergement> listeDesLieux;

    public SystemeGestionReservationsImpl() {
        this.reservations = new ArrayList<>();
        this.listeDesLieux = new ArrayList<>();
    }

    @Override
    public Reservation reserver(Client client, LieuHebergement lieu, TypeChambre typeChambre, 
                                LocalDate dateArrivee, LocalDate dateDepart) {

        // Vérifier que la date d'arrivée est aujourd'hui ou dans le futur
        if (dateArrivee.isBefore(LocalDate.now())) {
            return null; // Réservation impossible dans le passé
        }

        // Vérifier que la réservation est pour au moins une nuit
        if (dateDepart.isBefore(dateArrivee.plusDays(1))) {
            return null; // Réservation pour moins d'une nuit
        }

        // Trouver les chambres disponibles pour cette période et dans ce lieu
        List<Chambre> chambresDispo = trouverChambresDisponibles(lieu, typeChambre, dateArrivee, dateDepart);

        if (!chambresDispo.isEmpty()) {
            // Trouver la chambre avec le prix le plus bas
            Chambre chambreMoinsChere = chambresDispo.stream()
                    .min((c1, c2) -> Double.compare(c1.getPrix(), c2.getPrix()))
                    .orElse(null);

            if (chambreMoinsChere != null) {
                chambreMoinsChere.setDisponible(false); // Marquer la chambre comme indisponible
                Reservation reservation = new Reservation(client, chambreMoinsChere, dateArrivee, dateDepart);
                reservations.add(reservation);

                // Proposer le prix au client
                System.out.println("Madame Apafi NiDévoyager vous propose une réservation à " + chambreMoinsChere.getPrix() + " $ par nuit.");
                return reservation;
            }
        }
        return null; // Aucune chambre disponible
    }

    @Override
    public void annulerReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.chambre().setDisponible(true); // Remettre la chambre à disponible
    }

    @Override
    public List<Chambre> trouverChambresDisponibles(LieuHebergement lieu, TypeChambre typeChambre, LocalDate dateArrivee, LocalDate dateDepart) {
        List<Chambre> disponibles = new ArrayList<>();
        
        for (Chambre chambre : lieu.getChambres()) {
            if (chambre.getType().equals(typeChambre)) {
                if (chambre.isDisponible()) {
                    disponibles.add(chambre);
                } else {
                    // Vérifier si la chambre est disponible pendant toute la période demandée
                    boolean estDisponible = false;
                    for (Reservation reservation : reservations) {
                        if (dateDepart.compareTo(reservation.dateArrivee()) <= 0 || dateArrivee.compareTo(reservation.dateDepart()) >= 0) {
                            estDisponible = true;
                            break;
                        }
                    }
                    // Ajouter la chambre à la liste si elle est disponible pour toute la période
                    if (estDisponible) {
                        disponibles.add(chambre);
                    }
                }
            }
        }
        return disponibles;
    }

    public BesoinsClient recueillirBesoinsClient() {
        try (Scanner scanner = new Scanner(System.in)) {

            // Demander le type de lieu d'hébergement
            System.out.println("Quel type d'hébergement souhaitez-vous réserver ?");
            System.out.println("(H)ôtel, (M)otel, (C)ouette et café : ");
            String typeLieuInput = scanner.nextLine();
            char choixLieu = typeLieuInput.isEmpty() ? null : typeLieuInput.charAt(0);
            TypeLieuHebergement typeLieu = switch (choixLieu) {
                case 'H' -> TypeLieuHebergement.HOTEL;
                case 'M' -> TypeLieuHebergement.MOTEL;
                case 'C' -> TypeLieuHebergement.COUETTE_ET_CAFE;
                default -> TypeLieuHebergement.INDIFFERENT;
            };

            // Demander les informations de la région
            System.out.println("Dans quelle région souhaitez-vous réserver ?");
            System.out.println("Pays : ");
            String pays = scanner.nextLine();
            System.out.println("Province : ");
            String province = scanner.nextLine();
            System.out.println("Ville : ");
            String ville = scanner.nextLine();
            System.out.println("Quartier : ");
            String quartier = scanner.nextLine();
            System.out.println("Rue : ");
            String rue = scanner.nextLine();
            Region region = new Region(pays, province, ville, quartier, rue);

            // Demander le type de chambre
            System.out.println("Quel type de chambre souhaitez-vous ?");
            System.out.println("(S)imple, (D)ouble, S(U)ite : ");
            String typeChambreInput = scanner.nextLine();
            char choixChambre = typeChambreInput.isEmpty() ? null : typeChambreInput.charAt(0);
            TypeChambre typeChambre = switch (choixChambre) {
                case 'S' -> TypeChambre.SIMPLE;
                case 'D' -> TypeChambre.DOUBLE;
                case 'U' -> TypeChambre.SUITE;
                default -> TypeChambre.INDIFFERENT;
            };

            // Demander les services spécifiques
            System.out.println("Quels services spécifiques souhaitez-vous (séparés par des virgules) ?");
            System.out.println("Piscine (I)ntérieure, (C)uisinette, Salle de conditionnement (P)hysique, (S)tationnement, Accès (H)andicapé, (D)épanneur, (R)estaurant : ");
            String[] servicesInput = scanner.nextLine().split(",");
            List<Service> services = new ArrayList<>();
            for (String service : servicesInput) {
                switch (service) {
                    case "I" -> services.add(Service.PISCINE_INTERIEURE);
                    case "C" -> services.add(Service.CUISINETTE);
                    case "P" -> services.add(Service.SALLE_DE_CONDITIONNEMENT_PHYSIQUE);
                    case "S" -> services.add(Service.STATIONNEMENT);
                    case "H" -> services.add(Service.ACCES_HANDICAPE);
                    case "D" -> services.add(Service.DEPANNEUR);
                    case "R" -> services.add(Service.RESTAURANT);
                    default -> { /* Ignorer les services inconnus */
                    }
                }
            }
            // Retourner un objet BesoinsClient avec les données recueillies
            return new BesoinsClient(typeLieu, region, typeChambre, services);
        }
    }

    public void ajouterLieu(LieuHebergement lieu) {
        listeDesLieux.add(lieu);
    }

    public List<LieuHebergement> trouverLieuxDisponibles(TypeLieuHebergement typeLieu, Region region, TypeChambre typeChambre, List<Service> services) {
        List<LieuHebergement> lieuxDisponibles = new ArrayList<>();

        for (LieuHebergement lieu : listeDesLieux) {
            // Vérifier si le type de lieu correspond ou si le client est indifférent
            boolean typeLieuValide = typeLieu == TypeLieuHebergement.INDIFFERENT || lieu.getType().equals(typeLieu);

            // Vérifier si la région correspond ou si le client est indifférent
            boolean regionValide = (region.pays().isEmpty() || lieu.getRegion().pays().equals(region.pays())) &&
                                   (region.province().isEmpty() || lieu.getRegion().province().equals(region.province())) &&
                                   (region.ville().isEmpty() || lieu.getRegion().ville().equals(region.ville())) &&
                                   (region.quartier().isEmpty() || lieu.getRegion().quartier().equals(region.quartier())) &&
                                   (region.rue().isEmpty() || lieu.getRegion().rue().equals(region.rue()));

            // Vérifier si le lieu possède le type de chambre ou si le client est indifférent
            boolean typeChambreValide = typeChambre == TypeChambre.INDIFFERENT || lieu.getChambres().stream()
                    .anyMatch(chambre -> chambre.getType().equals(typeChambre));

            // Vérifier si les services demandés sont tous présents dans le lieu
            boolean servicesValides = lieu.getServices().containsAll(services);

            // Ajouter le lieu à la liste des lieux disponibles si toutes les conditions sont remplies
            if (typeLieuValide && regionValide && typeChambreValide && servicesValides) {
                lieuxDisponibles.add(lieu);
            }
        }
        return lieuxDisponibles;
    }
}
