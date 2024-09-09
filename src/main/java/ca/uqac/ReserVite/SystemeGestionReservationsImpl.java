package ca.uqac.ReserVite;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SystemeGestionReservationsImpl implements SystemeGestionReservations {
    private List<Reservation> reservations;

    public SystemeGestionReservationsImpl() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public Reservation reserver(Client client, LieuHebergement lieu, TypeChambre typeChambre, LocalDate dateArrivee, LocalDate dateDepart) {
        // Vérifier que la date d'arrivée est aujourd'hui ou dans le futur
        if (dateArrivee.isBefore(LocalDate.now())) {
            return null; // Réservation impossible dans le passé
        }

        // Vérifier que la réservation est pour au moins une nuit
        if (dateDepart.isBefore(dateArrivee.plusDays(1))) {
            return null; // Réservation pour moins d'une nuit
        }

        // Trouver les chambres disponibles pour cette période
        List<Chambre> chambresDispo = trouverChambresDisponibles(lieu, typeChambre, dateArrivee, dateDepart);

        if (!chambresDispo.isEmpty()) {
            Chambre chambre = chambresDispo.get(0);  // Prend la première chambre disponible
            chambre.setDisponible(false);  // Marquer la chambre comme indisponible
            Reservation reservation = new Reservation(client, chambre, dateArrivee, dateDepart);
            reservations.add(reservation);
            return reservation;
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
}
