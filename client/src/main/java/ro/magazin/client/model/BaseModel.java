package ro.magazin.client.model;

import ro.magazin.client.app.ClientApp;
import ro.magazin.client.network.ServerConnection;
import ro.magazin.common.protocol.Request;
import ro.magazin.common.protocol.Response;
import ro.magazin.common.protocol.TipCerere;

import java.io.IOException;
import java.util.List;

/**
 * Baza pentru modelele care comunica cu serverul.
 */
public abstract class BaseModel {

    protected final ServerConnection conexiune = ServerConnection.getInstance();

    protected <T> T trimite(TipCerere tip, Object payload, Class<T> tipRaspuns) {
        return cerere(tip, payload).getPayloadAs(tipRaspuns);
    }

    protected <T> List<T> trimiteLista(TipCerere tip, Object payload, Class<T> tipElement) {
        return cerere(tip, payload).getPayloadAsList(tipElement);
    }

    private Response cerere(TipCerere tip, Object payload) {
        try {
            if (!conexiune.esteConectat()) {
                conexiune.conecteaza(ClientApp.HOST, ClientApp.PORT);
            }
            Response r = conexiune.trimite(new Request(tip, payload));
            if (!r.esteOk()) {
                throw new ServerException(r.getMesaj());
            }
            return r;
        } catch (IOException e) {
            throw new ServerException("Conexiunea cu serverul a fost pierduta: " + e.getMessage());
        }
    }
}
