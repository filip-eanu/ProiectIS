package ro.magazin.server.service;

import ro.magazin.common.dto.UtilizatorDTO;
import ro.magazin.server.dao.Database;
import ro.magazin.server.model.Utilizator;

public class AuthService {

    private final Database database;

    public AuthService(Database database) {
        this.database = database;
    }

    // TODO: verifica unicitatea emailului (UtilizatorDAO.findByEmail), PasswordHasher.hash, insert cu rol CLIENT
    public UtilizatorDTO inregistreaza(UtilizatorDTO dto) {
        throw new UnsupportedOperationException();
    }

    // TODO: UtilizatorDAO.findByEmail + PasswordHasher.verifica; ServiceException("Email sau parola incorecte")
    public Utilizator autentifica(String email, String parola) {
        throw new UnsupportedOperationException();
    }
}
