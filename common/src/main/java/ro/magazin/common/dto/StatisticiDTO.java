package ro.magazin.common.dto;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticiDTO {

    private Map<Categorie, Integer> vanzariPeCategorie = new LinkedHashMap<>();
    private Map<String, Integer> topProduse = new LinkedHashMap<>();
    private Map<String, BigDecimal> vanzariPeLuna = new LinkedHashMap<>();

    public StatisticiDTO() {
    }

    public Map<Categorie, Integer> getVanzariPeCategorie() {
        return vanzariPeCategorie;
    }

    public void setVanzariPeCategorie(Map<Categorie, Integer> vanzariPeCategorie) {
        this.vanzariPeCategorie = vanzariPeCategorie;
    }

    public Map<String, Integer> getTopProduse() {
        return topProduse;
    }

    public void setTopProduse(Map<String, Integer> topProduse) {
        this.topProduse = topProduse;
    }

    public Map<String, BigDecimal> getVanzariPeLuna() {
        return vanzariPeLuna;
    }

    public void setVanzariPeLuna(Map<String, BigDecimal> vanzariPeLuna) {
        this.vanzariPeLuna = vanzariPeLuna;
    }
}
