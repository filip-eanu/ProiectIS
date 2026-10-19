package ro.magazin.common.dto;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProdusDTO {

    private int id;
    private String denumire;
    private Categorie categorie;
    private String producator;
    private String descriere;
    private BigDecimal pret;
    private int stoc;
    private String imagineBase64;
    private Map<String, String> specificatii = new LinkedHashMap<>();

    public ProdusDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public BigDecimal getPret() {
        return pret;
    }

    public void setPret(BigDecimal pret) {
        this.pret = pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public String getImagineBase64() {
        return imagineBase64;
    }

    public void setImagineBase64(String imagineBase64) {
        this.imagineBase64 = imagineBase64;
    }

    public Map<String, String> getSpecificatii() {
        return specificatii;
    }

    public void setSpecificatii(Map<String, String> specificatii) {
        this.specificatii = specificatii;
    }
}
