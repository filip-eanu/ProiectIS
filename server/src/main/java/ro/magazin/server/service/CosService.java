package ro.magazin.server.service;

import ro.magazin.common.dto.ElementCosDTO;
import ro.magazin.server.dao.Database;

import java.util.List;

/**
 * Fiecare operatie intoarce continutul actualizat al cosului.
 */
public class CosService {

    private final Database database;

    public CosService(Database database) {
        this.database = database;
    }

    public List<ElementCosDTO> getCos(int idUtilizator) {
        throw new UnsupportedOperationException();
    }

    // TODO: cantitatea totala din cos nu poate depasi stocul
    public List<ElementCosDTO> adauga(int idUtilizator, int idProdus, int cantitate) {
        throw new UnsupportedOperationException();
    }

    public List<ElementCosDTO> modificaCantitate(int idUtilizator, int idProdus, int cantitate) {
        throw new UnsupportedOperationException();
    }

    public List<ElementCosDTO> elimina(int idUtilizator, int idProdus) {
        throw new UnsupportedOperationException();
    }
}
