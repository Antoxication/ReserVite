package ca.uqac.ReserVite;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BesoinsClientTest {

    @Test
    public void testBesoinsClientCreation() {
        // Créer des besoins client
        TypeLieuHebergement typeLieu = TypeLieuHebergement.HOTEL;
        Region region = new Region("Canada", "Québec", "Montréal", "Centre-ville", "Rue B");
        TypeChambre typeChambre = TypeChambre.SIMPLE;
        List<Service> services = List.of(Service.PISCINE_INTERIEURE, Service.RESTAURANT);

        // Créer un objet BesoinsClient
        BesoinsClient besoinsClient = new BesoinsClient(typeLieu, region, typeChambre, services);

        // Vérifier que les attributs sont bien définis
        assertEquals(typeLieu, besoinsClient.typeLieuHebergement());
        assertEquals(region, besoinsClient.region());  // On vérifie simplement que l'objet region est bien passé
        assertEquals(typeChambre, besoinsClient.typeChambre());
        assertEquals(services, besoinsClient.services());
    }

    @Test
    public void testBesoinsClientEmptyServices() {
        // Créer des besoins client avec une liste de services vide
        TypeLieuHebergement typeLieu = TypeLieuHebergement.HOTEL;
        Region region = new Region("Canada", "Québec", "Montréal", "Centre-ville", "Rue B");
        TypeChambre typeChambre = TypeChambre.SIMPLE;

        // Créer un objet BesoinsClient sans services
        BesoinsClient besoinsClient = new BesoinsClient(typeLieu, region, typeChambre, List.of());

        // Vérifier que la liste de services est vide
        assertTrue(besoinsClient.services().isEmpty());
    }

    @Test
    public void testBesoinsClientToString() {
        // Créer des besoins client
        TypeLieuHebergement typeLieu = TypeLieuHebergement.HOTEL;
        Region region = new Region("Canada", "Québec", "Montréal", "Centre-ville", "Rue B");
        TypeChambre typeChambre = TypeChambre.SIMPLE;
        List<Service> services = List.of(Service.PISCINE_INTERIEURE, Service.RESTAURANT);

        // Créer un objet BesoinsClient
        BesoinsClient besoinsClient = new BesoinsClient(typeLieu, region, typeChambre, services);

        // Vérifier que la méthode toString génère bien une chaîne correcte
        String expected = "BesoinsClient[typeLieuHebergement=HOTEL, region=Region{pays='Canada', province='Québec', ville='Montréal', quartier='Centre-ville', rue='Rue B'}, typeChambre=SIMPLE, services=[PISCINE_INTERIEURE, RESTAURANT]]";
        assertEquals(expected, besoinsClient.toString());
    }
}
