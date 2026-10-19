package ro.magazin.common.dto;

import java.math.BigDecimal;

/**
 * Criteriile necompletate (null) nu se aplica.
 */
public class FiltruProduse {

    private Categorie categorie;
    private String producator;
    private BigDecimal pretMin;
    private BigDecimal pretMax;
    private boolean doarInStoc;

    public FiltruProduse() {
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getProducator() {
        return producator;
    }

    public void setProducator(String producator) {
        this.producator = producator;
    }

    public BigDecimal getPretMin() {
        return pretMin;
    }

    public void setPretMin(BigDecimal pretMin) {
        this.pretMin = pretMin;
    }

    public BigDecimal getPretMax() {
        return pretMax;
    }

    public void setPretMax(BigDecimal pretMax) {
        this.pretMax = pretMax;
    }

    public boolean isDoarInStoc() {
        return doarInStoc;
    }

    public void setDoarInStoc(boolean doarInStoc) {
        this.doarInStoc = doarInStoc;
    }
}
