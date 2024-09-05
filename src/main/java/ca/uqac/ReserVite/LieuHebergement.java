package ca.uqac.ReserVite;

import java.util.ArrayList;
import java.util.List;

public class LieuHebergement {
    private String type;
    private String nom;
    private String adresse;
    private List<Chambre> chambres;

    // Constructeur
    public LieuHebergement(String type, String nom, String adresse) {
        this.type = type;
        this.nom = nom;
        this.adresse = adresse;
        this.chambres = new ArrayList<>();
    }

    // Getters et Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    // Méthode pour ajouter une chambre à la liste
    public void ajouterChambre(Chambre chambre) {
        this.chambres.add(chambre);
    }

    // Méthode toString pour une représentation textuelle de l'objet
    @Override
    public String toString() {
        return "LieuHebergement{" +
                "type='" + type + '\'' +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", chambres=" + chambres +
                '}';
    }
}
