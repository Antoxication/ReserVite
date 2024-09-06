package ca.uqac.ReserVite;

import java.util.Date;

public class Reservation {
    private Client client;
    private Chambre chambre;
    private Date dateArrivee;
    private Date dateDepart;

    // Constructeur
    public Reservation(Client client, Chambre chambre, Date dateArrivee, Date dateDepart) {
        this.client = client;
        this.chambre = chambre;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
    }

    // Getters et Setters
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

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
