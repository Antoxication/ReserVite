package ca.uqac.ReserVite;
import java.time.LocalDate;
import java.util.List;

public interface SystemeGestionReservations {
    Reservation reserver(Client client, LieuHebergement lieu, TypeChambre typeChambre, LocalDate dateArrivee, LocalDate dateDepart);
    void annulerReservation(Reservation reservation);
    List<Chambre> trouverChambresDisponibles(LieuHebergement lieu, TypeChambre typeChambre, LocalDate dateArrivee, LocalDate dateDepart);
    BesoinsClient recueillirBesoinsClient();
    List<LieuHebergement> trouverLieuxDisponibles(TypeLieuHebergement typeLieu, Region region, TypeChambre typeChambre, List<Service> services);
}
