package ro.magazin.server.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ro.magazin.common.dto.Categorie;

public class Produs {

    private int id;
    private String denumire;
    private Categorie categorie;
    private String producator;
    private String descriere;
    private BigDecimal pret;
    private int stoc;
    private String imagine;
    private boolean activ;
    private List<Specificatie> specificatii = new ArrayList<>();

    public Produs() {
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

    public String getImagine() {
        return imagine;
    }

    public void setImagine(String imagine) {
        this.imagine = imagine;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public List<Specificatie> getSpecificatii() {
        return specificatii;
    }

    public void setSpecificatii(List<Specificatie> specificatii) {
        this.specificatii = specificatii;
    }
}
