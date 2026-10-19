package ro.magazin.common.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComandaDTO {

    private int id;
    private String numeClient;
    private LocalDateTime dataPlasare;
    private String adresaLivrare;
    private StareComanda stare;
    private BigDecimal total;
    private List<LinieComandaDTO> produse = new ArrayList<>();

    public ComandaDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public LocalDateTime getDataPlasare() {
        return dataPlasare;
    }

    public void setDataPlasare(LocalDateTime dataPlasare) {
        this.dataPlasare = dataPlasare;
    }

    public String getAdresaLivrare() {
        return adresaLivrare;
    }

    public void setAdresaLivrare(String adresaLivrare) {
        this.adresaLivrare = adresaLivrare;
    }

    public StareComanda getStare() {
        return stare;
    }

    public void setStare(StareComanda stare) {
        this.stare = stare;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<LinieComandaDTO> getProduse() {
        return produse;
    }

    public void setProduse(List<LinieComandaDTO> produse) {
        this.produse = produse;
    }
}
