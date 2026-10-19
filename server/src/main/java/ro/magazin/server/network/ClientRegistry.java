package ro.magazin.server.network;

import ro.magazin.server.model.MotivDeconectare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Evidenta clientilor conectati in acest moment.
 */
public class ClientRegistry {

    private final Map<Long, ClientHandler> clienti = new ConcurrentHashMap<>();

    public void adauga(ClientHandler c) {
        clienti.put(c.getIdSesiune(), c);
    }

    public void elimina(long idSesiune) {
        clienti.remove(idSesiune);
    }

    public ClientHandler get(long idSesiune) {
        return clienti.get(idSesiune);
    }

    public List<ClientHandler> getToti() {
        List<ClientHandler> lista = new ArrayList<>(clienti.values());
        lista.sort(Comparator.comparingLong(ClientHandler::getIdSesiune));
        return lista;
    }

    public int numar() {
        return clienti.size();
    }

    public void deconecteazaToti(MotivDeconectare motiv) {
        for (ClientHandler c : getToti()) {
            c.deconecteaza(motiv);
        }
    }
}
