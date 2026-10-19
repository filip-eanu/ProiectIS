package ro.magazin.server.network;

import ro.magazin.common.protocol.JsonUtil;
import ro.magazin.common.protocol.Request;
import ro.magazin.common.protocol.Response;
import ro.magazin.common.protocol.TipCerere;
import ro.magazin.server.model.MotivDeconectare;
import ro.magazin.server.model.Utilizator;
import ro.magazin.server.service.AuditService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Deserveste o singura aplicatie client, pe un fir de executie separat.
 * Protocol: o cerere JSON pe linie, urmata de un raspuns JSON pe linie.
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final RequestDispatcher dispatcher;
    private final ClientRegistry registry;
    private final AuditService auditService;

    private final long idSesiune;
    private final LocalDateTime momentConectare;
    private final AtomicBoolean inchis = new AtomicBoolean(false);
    private volatile Utilizator utilizator;

    public ClientHandler(Socket socket, RequestDispatcher dispatcher, ClientRegistry registry,
                         AuditService auditService) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        this.dispatcher = dispatcher;
        this.registry = registry;
        this.auditService = auditService;
        this.momentConectare = LocalDateTime.now();
        this.idSesiune = auditService.deschideSesiune(getAdresaIp(), socket.getPort());
    }

    @Override
    public void run() {
        try {
            String linie;
            while (!inchis.get() && (linie = in.readLine()) != null) {
                Request req;
                try {
                    req = JsonUtil.fromJson(linie, Request.class);
                } catch (IllegalArgumentException e) {
                    trimite(Response.eroare("Cerere invalida"));
                    continue;
                }
                if (req.getTip() == TipCerere.DECONECTARE) {
                    break;
                }
                Response resp = dispatcher.proceseaza(req, this);
                auditService.inregistreazaCerere(idSesiune, req, resp);
                trimite(resp);
            }
            deconecteaza(MotivDeconectare.CLIENT);
        } catch (IOException e) {
            deconecteaza(MotivDeconectare.EROARE);
        }
    }

    public synchronized void trimite(Response r) {
        out.println(JsonUtil.toJson(r));
    }

    /** Poate fi apelata de mai multe ori (client, administrator, oprire server); doar primul apel are efect. */
    public void deconecteaza(MotivDeconectare motiv) {
        if (!inchis.compareAndSet(false, true)) {
            return;
        }
        if (motiv == MotivDeconectare.ADMIN) {
            trimite(Response.notificare("Ati fost deconectat de administrator"));
        } else if (motiv == MotivDeconectare.SERVER_OPRIT) {
            trimite(Response.notificare("Serverul a fost oprit"));
        }
        try {
            socket.close();
        } catch (IOException ignored) {
            // conexiunea este oricum pierduta
        }
        registry.elimina(idSesiune);
        auditService.inchideSesiune(idSesiune, motiv);
    }

    public long getIdSesiune() {
        return idSesiune;
    }

    public String getAdresaIp() {
        return socket.getInetAddress().getHostAddress();
    }

    public String getAdresa() {
        return getAdresaIp() + ":" + socket.getPort();
    }

    public LocalDateTime getMomentConectare() {
        return momentConectare;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
        auditService.asociazaUtilizator(idSesiune, utilizator.getId());
    }
}
