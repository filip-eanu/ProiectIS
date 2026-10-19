package ro.magazin.server.service;

import ro.magazin.common.dto.FiltruProduse;
import ro.magazin.common.dto.ProdusDTO;
import ro.magazin.server.dao.Database;

import java.util.List;

public class ProdusService {

    private final Database database;

    public ProdusService(Database database) {
        this.database = database;
    }

    // TODO: produsele active, sortate dupa categorie si denumire, cu imaginea in Base64
    public List<ProdusDTO> getProduse() {
        throw new UnsupportedOperationException();
    }

    // TODO: produs + specificatii; ServiceException daca nu exista sau e inactiv
    public ProdusDTO getDetalii(int id) {
        throw new UnsupportedOperationException();
    }

    public List<ProdusDTO> filtreaza(FiltruProduse filtru) {
        throw new UnsupportedOperationException();
    }

    public List<ProdusDTO> cauta(String text) {
        throw new UnsupportedOperationException();
    }

    public ProdusDTO adauga(ProdusDTO dto) {
        throw new UnsupportedOperationException();
    }

    public ProdusDTO modifica(ProdusDTO dto) {
        throw new UnsupportedOperationException();
    }

    // TODO: stergere logica (activ = 0) + eliminare din cosuri
    public void sterge(int id) {
        throw new UnsupportedOperationException();
    }
}
