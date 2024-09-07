package ca.uqac.ReserVite;

import java.util.ArrayList;
import java.util.List;

public class LieuHebergement {
    private TypeLieuHebergement type;
    private String nom;
    private String adresse;
    private List<Chambre> chambres;
    private List<Service> servicesGeneraux;

    // Constructeur
    public LieuHebergement(TypeLieuHebergement type, String nom, String adresse, List<Service> servicesGeneraux) {
        this.type = type;
        this.nom = nom;
        this.adresse = adresse;
        this.chambres = new ArrayList<>();
        this.servicesGeneraux = servicesGeneraux;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Chambre> getChambres() {
        return chambres;
    }

    public void ajouterChambre(Chambre chambre) {
        this.chambres.add(chambre);
    }

    public List<Service> getServicesGeneraux() {
        return servicesGeneraux;
    }

    public void setServicesGeneraux(List<Service> servicesGeneraux) {
        this.servicesGeneraux = servicesGeneraux;
    }

    // Méthode toString pour une représentation textuelle de l'objet
    @Override
    public String toString() {
        return "LieuHebergement{" +
                "type=" + type +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", chambres=" + chambres +
                ", servicesGeneraux=" + servicesGeneraux +
                '}';
    }
}
