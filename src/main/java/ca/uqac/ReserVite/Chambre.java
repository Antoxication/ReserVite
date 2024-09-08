package ca.uqac.ReserVite;

public class Chambre {
    private TypeChambre type; // Enum pour le type de chambre
    private double prix;
    private boolean disponible;
    private String region;

    // Constructeur
    public Chambre(TypeChambre type, double prix, boolean disponible, String region) {
        this.type = type;
        this.prix = prix;
        this.disponible = disponible;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // Méthode toString pour une représentation textuelle de l'objet
    @Override
    public String toString() {
        return "Chambre{" +
                "type=" + type +
                ", prix=" + prix +
                ", disponible=" + disponible +
                ", region='" + region + '\'' +
                '}';
    }
}
