package ro.magazin.server.service;

import ro.magazin.common.dto.StatisticiDTO;
import ro.magazin.server.dao.Database;

public class StatisticiService {

    private final Database database;

    public StatisticiService(Database database) {
        this.database = database;
    }

    // TODO: vanzari pe categorie, top 5 produse, vanzari pe luna (fara comenzile ANULATE)
    public StatisticiDTO getStatistici() {
        throw new UnsupportedOperationException();
    }
}
