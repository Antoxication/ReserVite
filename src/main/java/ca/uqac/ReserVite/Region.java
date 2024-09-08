package ca.uqac.ReserVite;

public record Region(String pays, String province, String ville, String quartier, String rue) {

    @Override
    public String toString() {
        return "Region{" +
                "pays='" + (pays != null ? pays : "Indifférent") + '\'' +
                ", province='" + (province != null ? province : "Indifférent") + '\'' +
                ", ville='" + (ville != null ? ville : "Indifférent") + '\'' +
                ", quartier='" + (quartier != null ? quartier : "Indifférent") + '\'' +
                ", rue='" + (rue != null ? rue : "Indifférent") + '\'' +
                '}';
    }
}
