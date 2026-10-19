package ro.magazin.client.model;

import ro.magazin.common.dto.Rol;
import ro.magazin.common.dto.UtilizatorDTO;

/**
 * Utilizatorul autentificat in aplicatia client.
 */
public final class SesiuneUtilizator {

    private static final SesiuneUtilizator INSTANCE = new SesiuneUtilizator();

    private UtilizatorDTO utilizator;

    private SesiuneUtilizator() {
    }

    public static SesiuneUtilizator getInstance() {
        return INSTANCE;
    }

    public UtilizatorDTO getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(UtilizatorDTO utilizator) {
        this.utilizator = utilizator;
    }

    public boolean esteAngajat() {
        return utilizator != null && utilizator.getRol() == Rol.ANGAJAT;
    }
}
