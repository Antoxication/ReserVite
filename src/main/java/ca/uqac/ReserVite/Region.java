package ca.uqac.ReserVite;

public record Region(String pays, String province, String ville, String quartier, String rue) {

    @Override
    public String toString() {
        return "Region{" +
                "pays='" + (pays != "" ? pays : "Indifférent") + '\'' +
                ", province='" + (province != "" ? province : "Indifférent") + '\'' +
                ", ville='" + (ville != "" ? ville : "Indifférent") + '\'' +
                ", quartier='" + (quartier != "" ? quartier : "Indifférent") + '\'' +
                ", rue='" + (rue != "" ? rue : "Indifférent") + '\'' +
                '}';
    }
}
