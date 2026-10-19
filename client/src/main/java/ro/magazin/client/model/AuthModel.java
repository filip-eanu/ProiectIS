package ro.magazin.client.model;

import ro.magazin.common.dto.AutentificareDTO;
import ro.magazin.common.dto.UtilizatorDTO;
import ro.magazin.common.protocol.TipCerere;

public class AuthModel extends BaseModel {

    public void inregistreaza(UtilizatorDTO u) {
        trimite(TipCerere.INREGISTRARE, u, UtilizatorDTO.class);
    }

    public UtilizatorDTO autentifica(String email, String parola) {
        AutentificareDTO a = new AutentificareDTO();
        a.setEmail(email);
        a.setParola(parola);
        return trimite(TipCerere.AUTENTIFICARE, a, UtilizatorDTO.class);
    }
}
