package ca.uqac.ReserVite;

import java.util.Date;
import java.util.List;

public interface SystemeGestionReservations {
    Reservation reserver(Client client, LieuHebergement lieu, TypeChambre typeChambre, Date dateArrivee, Date dateDepart);
    void annulerReservation(Reservation reservation);
    List<Chambre> trouverChambresDisponibles(LieuHebergement lieu, TypeChambre typeChambre, Date dateArrivee, Date dateDepart);
}
