package ro.magazin.server.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ro.magazin.common.dto.StareComanda;

public class Comanda {

    private int id;
    private int idUtilizator;
    private String numeClient;
    private LocalDateTime dataPlasare;
    private String adresaLivrare;
    private StareComanda stare;
    private BigDecimal total;
    private List<LinieComanda> linii = new ArrayList<>();

    public Comanda() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(int idUtilizator) {
        this.idUtilizator = idUtilizator;
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

    public List<LinieComanda> getLinii() {
        return linii;
    }

    public void setLinii(List<LinieComanda> linii) {
        this.linii = linii;
    }
}
