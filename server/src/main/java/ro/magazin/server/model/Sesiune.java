package ro.magazin.server.model;

import java.time.LocalDateTime;

public class Sesiune {

    private long id;
    private String adresaIp;
    private int port;
    private Integer idUtilizator;
    private LocalDateTime momentConectare;
    private LocalDateTime momentDeconectare;
    private MotivDeconectare motiv;

    public Sesiune() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdresaIp() {
        return adresaIp;
    }

    public void setAdresaIp(String adresaIp) {
        this.adresaIp = adresaIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Integer getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Integer idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public LocalDateTime getMomentConectare() {
        return momentConectare;
    }

    public void setMomentConectare(LocalDateTime momentConectare) {
        this.momentConectare = momentConectare;
    }

    public LocalDateTime getMomentDeconectare() {
        return momentDeconectare;
    }

    public void setMomentDeconectare(LocalDateTime momentDeconectare) {
        this.momentDeconectare = momentDeconectare;
    }

    public MotivDeconectare getMotiv() {
        return motiv;
    }

    public void setMotiv(MotivDeconectare motiv) {
        this.motiv = motiv;
    }
}
