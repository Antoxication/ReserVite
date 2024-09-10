package ca.uqac.ReserVite;

import java.util.List;

public record BesoinsClient (TypeLieuHebergement typeLieuHebergement, Region region, TypeChambre typeChambre, List<Service> services){
}
