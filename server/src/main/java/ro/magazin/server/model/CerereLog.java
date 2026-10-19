package ro.magazin.server.model;

import java.time.LocalDateTime;

public class CerereLog {

    private long id;
    private long idSesiune;
    private LocalDateTime moment;
    private String tip;
    private String continutCerere;
    private String statusRaspuns;
    private String continutRaspuns;

    public CerereLog() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSesiune() {
        return idSesiune;
    }

    public void setIdSesiune(long idSesiune) {
        this.idSesiune = idSesiune;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getContinutCerere() {
        return continutCerere;
    }

    public void setContinutCerere(String continutCerere) {
        this.continutCerere = continutCerere;
    }

    public String getStatusRaspuns() {
        return statusRaspuns;
    }

    public void setStatusRaspuns(String statusRaspuns) {
        this.statusRaspuns = statusRaspuns;
    }

    public String getContinutRaspuns() {
        return continutRaspuns;
    }

    public void setContinutRaspuns(String continutRaspuns) {
        this.continutRaspuns = continutRaspuns;
    }
}
