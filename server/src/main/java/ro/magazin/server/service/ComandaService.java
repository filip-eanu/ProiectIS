package ro.magazin.server.service;

import ro.magazin.common.dto.ComandaDTO;
import ro.magazin.common.dto.StareComanda;
import ro.magazin.server.dao.Database;

import java.util.List;

public class ComandaService {

    private final Database database;

    public ComandaService(Database database) {
        this.database = database;
    }

    // TODO: intr-o singura tranzactie: verifica stocul, creeaza comanda + liniile, scade stocul, goleste cosul
    public ComandaDTO plaseaza(int idUtilizator, String adresaLivrare) {
        throw new UnsupportedOperationException();
    }

    public List<ComandaDTO> getComenziClient(int idUtilizator) {
        throw new UnsupportedOperationException();
    }

    public List<ComandaDTO> getToateComenzile() {
        throw new UnsupportedOperationException();
    }

    public List<ComandaDTO> filtreazaDupaStare(StareComanda stare) {
        throw new UnsupportedOperationException();
    }

    // TODO: comenzile LIVRATA/ANULATA nu se mai modifica; la ANULATA stocul se reface
    public ComandaDTO schimbaStare(int idComanda, StareComanda stareNoua) {
        throw new UnsupportedOperationException();
    }
}
