package ro.magazin.server.model;

public class ElementCos {

    private Produs produs;
    private int cantitate;

    public ElementCos() {
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
}
