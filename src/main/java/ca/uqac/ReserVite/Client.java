package ca.uqac.ReserVite;

public record Client(String nom, String adresse, String courriel, String telephone) {
    
    @Override
    public String toString() {
        return "Client: " + nom + "\n" +
               "Adresse: " + adresse + "\n" +
               "Courriel: " + courriel + "\n" +
               "Téléphone: " + telephone;
    }
}
