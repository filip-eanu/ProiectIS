package ro.magazin.common.dto;

import java.math.BigDecimal;

public class LinieComandaDTO {

    private int idProdus;
    private String denumire;
    private int cantitate;
    private BigDecimal pretUnitar;

    public LinieComandaDTO() {
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public BigDecimal getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(BigDecimal pretUnitar) {
        this.pretUnitar = pretUnitar;
    }
}
