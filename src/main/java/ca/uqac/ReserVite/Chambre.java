package ca.uqac.ReserVite;

public class Chambre {
    private String type;
    private double prix;
    private boolean disponible;

    // Constructeur
    public Chambre(String type, double prix, boolean disponible) {
        this.type = type;
        this.prix = prix;
        this.disponible = disponible;
    }

    // Getters et Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Méthode toString pour une représentation textuelle de l'objet
    @Override
    public String toString() {
        return "Chambre{" +
                "type='" + type + '\'' +
                ", prix=" + prix +
                ", disponible=" + disponible +
                '}';
    }
}
