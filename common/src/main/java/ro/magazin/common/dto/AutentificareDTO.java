package ro.magazin.common.dto;

public class AutentificareDTO {

    private String email;
    private String parola;

    public AutentificareDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
