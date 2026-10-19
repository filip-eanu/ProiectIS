package ro.magazin.server.service;

import ro.magazin.common.protocol.Request;
import ro.magazin.common.protocol.Response;
import ro.magazin.server.dao.Database;
import ro.magazin.server.model.CerereLog;
import ro.magazin.server.model.MotivDeconectare;
import ro.magazin.server.model.Sesiune;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Istoricul conexiunilor si al cererilor, folosit de administrator.
 */
public class AuditService {

    private final Database database;

    // TODO: de inlocuit cu id-ul generat de INSERT INTO sesiune (SesiuneDAO)
    private final AtomicLong urmatorulId = new AtomicLong();

    public AuditService(Database database) {
        this.database = database;
    }

    public long deschideSesiune(String adresaIp, int port) {
        return urmatorulId.incrementAndGet();
    }

    // TODO: UPDATE sesiune SET id_utilizator = ?
    public void asociazaUtilizator(long idSesiune, int idUtilizator) {
    }

    // TODO: UPDATE sesiune SET moment_deconectare = NOW(), motiv_deconectare = ?
    public void inchideSesiune(long idSesiune, MotivDeconectare motiv) {
    }

    // TODO: INSERT INTO cerere (raspunsurile foarte lungi, ex. cu imagini, se pot trunchia)
    public void inregistreazaCerere(long idSesiune, Request req, Response resp) {
    }

    public List<Sesiune> getConectari(LocalDateTime de, LocalDateTime pana) {
        throw new UnsupportedOperationException();
    }

    public List<Sesiune> getDeconectari(LocalDateTime de, LocalDateTime pana) {
        throw new UnsupportedOperationException();
    }

    /** @return null daca sesiunea nu exista */
    public List<CerereLog> getCereri(long idSesiune, LocalDateTime de, LocalDateTime pana) {
        throw new UnsupportedOperationException();
    }
}
