package ro.magazin.common.dto;

public class SchimbareStareDTO {

    private int idComanda;
    private StareComanda stare;

    public SchimbareStareDTO() {
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public StareComanda getStare() {
        return stare;
    }

    public void setStare(StareComanda stare) {
        this.stare = stare;
    }
}
