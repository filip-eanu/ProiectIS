package ro.magazin.common.dto;

public class ElementCosDTO {

    private ProdusDTO produs;
    private int cantitate;

    public ElementCosDTO() {
    }

    public ProdusDTO getProdus() {
        return produs;
    }

    public void setProdus(ProdusDTO produs) {
        this.produs = produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
