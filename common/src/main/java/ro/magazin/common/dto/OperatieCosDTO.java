package ro.magazin.common.dto;

public class OperatieCosDTO {

    private int idProdus;
    private int cantitate;

    public OperatieCosDTO() {
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
