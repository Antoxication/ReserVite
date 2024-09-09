package ca.uqac.ReserVite;

import java.time.LocalDate;

public record Reservation(Client client, Chambre chambre, LocalDate dateArrivee, LocalDate dateDepart) {

    // Annuler la réservation
    public void annuler() {
        this.chambre.setDisponible(true);  // Remet la chambre comme disponible
    }

    // Méthode toString pour une représentation textuelle de l'objet
    @Override
    public String toString() {
        return "Reservation{" +
                "client=" + client +
                ", chambre=" + chambre +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                '}';
    }
}
