package ro.magazin.common.dto;

/**
 * Parola se completeaza doar in cererile de creare/modificare; serverul nu o trimite niciodata inapoi.
 */
public class UtilizatorDTO {

    private int id;
    private String nume;
    private String email;
    private String telefon;
    private String parola;
    private Rol rol;

    public UtilizatorDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
