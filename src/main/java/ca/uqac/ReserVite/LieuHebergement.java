package ca.uqac.ReserVite;

import java.util.ArrayList;
import java.util.List;

public class LieuHebergement {
    private TypeLieuHebergement type;
    private String nom;
    private List<Chambre> chambres;
    private List<Service> services;
    private Region region;

    // Constructeur
    public LieuHebergement(TypeLieuHebergement type, String nom, List<Service> services, Region region) {
        this.type = type;
        this.nom = nom;
        this.chambres = new ArrayList<>();
        this.services = services;
        this.region = region;
    }

    // Getters et Setters
    public TypeLieuHebergement getType() {
        return type;
    }

    public void setType(TypeLieuHebergement type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Chambre> getChambres() {
        return chambres;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    // Méthode pour ajouter une chambre à la liste
    public void ajouterChambre(Chambre chambre) {
        this.chambres.add(chambre);
    }

    // Méthode toString pour une représentation textuelle de l'objet
    @Override
    public String toString() {
        return "LieuHebergement{" +
                "type=" + type +
                ", nom='" + nom + '\'' +
                ", chambres=" + chambres +
                ", servicesGeneraux=" + services +
                ", region=" + region +
                '}';
    }
}
