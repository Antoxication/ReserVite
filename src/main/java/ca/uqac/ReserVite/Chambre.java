package ca.uqac.ReserVite;

public class Chambre {
    private TypeChambre type; // Enum pour le type de chambre
    private double prix;
    private boolean disponible;

    // Constructeur
    public Chambre(TypeChambre type, double prix, boolean disponible) {
        this.type = type;
        this.prix = prix;
        this.disponible = disponible;
    }

    // Getters et Setters
    public TypeChambre getType() {
        return type;
    }

    public void setType(TypeChambre type) {
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
                "type=" + type +
                ", prix=" + prix +
                ", disponible=" + disponible +
                '}';
    }
}
