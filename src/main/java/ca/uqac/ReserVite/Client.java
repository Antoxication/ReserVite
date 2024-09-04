package ca.uqac.ReserVite;

public class Client {
    private String nom;
    private String adresse;
    private String courriel;
    private String telephone;

    // Constructeur
    public Client(String nom, String adresse, String courriel, String telephone) {
        this.nom = nom;
        this.adresse = adresse;
        this.courriel = courriel;
        this.telephone = telephone;
    }

    // Getters et Setters
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

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Méthode toString pour une représentation textuelle de l'objet
    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", courriel='" + courriel + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
