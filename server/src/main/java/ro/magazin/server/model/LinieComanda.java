package ro.magazin.server.model;

import java.math.BigDecimal;

public class LinieComanda {

    private Produs produs;
    private int cantitate;
    private BigDecimal pretUnitar;

    public LinieComanda() {
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
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
