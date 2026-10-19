package ro.magazin.server.service;

import ro.magazin.common.dto.Rol;
import ro.magazin.common.dto.UtilizatorDTO;
import ro.magazin.server.dao.Database;

import java.util.List;

public class UtilizatorService {

    private final Database database;

    public UtilizatorService(Database database) {
        this.database = database;
    }

    public List<UtilizatorDTO> getToti() {
        throw new UnsupportedOperationException();
    }

    public List<UtilizatorDTO> filtreazaDupaRol(Rol rol) {
        throw new UnsupportedOperationException();
    }

    public UtilizatorDTO adaugaAngajat(UtilizatorDTO dto) {
        throw new UnsupportedOperationException();
    }

    // TODO: parola se schimba doar daca e completata
    public UtilizatorDTO modificaAngajat(UtilizatorDTO dto) {
        throw new UnsupportedOperationException();
    }

    // TODO: un angajat nu isi poate sterge propriul cont (id == idCurent)
    public void stergeAngajat(int id, int idCurent) {
        throw new UnsupportedOperationException();
    }
}
