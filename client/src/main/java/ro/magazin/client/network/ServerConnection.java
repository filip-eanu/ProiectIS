package ro.magazin.client.network;

import ro.magazin.common.protocol.JsonUtil;
import ro.magazin.common.protocol.Request;
import ro.magazin.common.protocol.Response;
import ro.magazin.common.protocol.StatusRaspuns;
import ro.magazin.common.protocol.TipCerere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Conexiunea TCP cu serverul (o singura instanta pe aplicatie).
 * Fiecare cerere asteapta raspunsul ei: o linie JSON trimisa, o linie JSON primita.
 */
public final class ServerConnection {

    private static final ServerConnection INSTANCE = new ServerConnection();

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private ServerConnection() {
    }

    public static ServerConnection getInstance() {
        return INSTANCE;
    }

    public synchronized void conecteaza(String host, int port) throws IOException {
        if (esteConectat()) {
            return;
        }
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
    }

    public synchronized Response trimite(Request req) throws IOException {
        if (!esteConectat()) {
            throw new IOException("Nu exista conexiune cu serverul");
        }
        out.println(JsonUtil.toJson(req));
        String linie = in.readLine();
        if (linie == null) {
            inchide();
            throw new IOException("Serverul a inchis conexiunea");
        }
        Response resp = JsonUtil.fromJson(linie, Response.class);
        if (resp.getStatus() == StatusRaspuns.NOTIFICARE) {
            // deconectare de catre administrator sau oprirea serverului
            inchide();
            throw new IOException(resp.getMesaj());
        }
        return resp;
    }

    public synchronized void deconecteaza() {
        if (esteConectat()) {
            out.println(JsonUtil.toJson(new Request(TipCerere.DECONECTARE)));
        }
        inchide();
    }

    public synchronized boolean esteConectat() {
        return socket != null && !socket.isClosed();
    }

    private void inchide() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ignored) {
                // conexiunea este oricum pierduta
            }
        }
        socket = null;
    }
}
