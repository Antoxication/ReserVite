package ca.uqac.ReserVite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemeGestionReservationsImpl implements SystemeGestionReservations {
    private List<Reservation> reservations;

    public SystemeGestionReservationsImpl() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public Reservation reserver(Client client, LieuHebergement lieu, String typeChambre, Date dateArrivee, Date dateDepart) {
        List<Chambre> chambresDispo = trouverChambresDisponibles(lieu, typeChambre, dateArrivee, dateDepart);
        if (!chambresDispo.isEmpty()) {
            Chambre chambre = chambresDispo.get(0); // Prend la première chambre disponible
            chambre.setDisponible(false); // Marquer la chambre comme indisponible
            Reservation reservation = new Reservation(client, chambre, dateArrivee, dateDepart);
            reservations.add(reservation);
            return reservation;
        }
        return null; // Aucune chambre disponible
    }    

    @Override
    public void annulerReservation(Reservation reservation) {
        reservations.remove(reservation);
        // Remettre la chambre à disponible
        reservation.chambre().setDisponible(true);
    }

    @Override
    public List<Chambre> trouverChambresDisponibles(LieuHebergement lieu, String typeChambre, Date dateArrivee, Date dateDepart) {
        List<Chambre> disponibles = new ArrayList<>();
        for (Chambre chambre : lieu.getChambres()) {
            if (chambre.getType().equals(typeChambre) && chambre.isDisponible()) {
                disponibles.add(chambre);
            }
        }
        return disponibles;
    }
}
