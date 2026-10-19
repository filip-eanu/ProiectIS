package ro.magazin.server.network;

import ro.magazin.server.model.MotivDeconectare;
import ro.magazin.server.service.AuditService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final ClientRegistry registry;
    private final RequestDispatcher dispatcher;
    private final AuditService auditService;

    private ServerSocket serverSocket;
    private ExecutorService executor;
    private volatile boolean pornit;
    private LocalDateTime momentPornire;

    public Server(int port, ClientRegistry registry, RequestDispatcher dispatcher, AuditService auditService) {
        this.port = port;
        this.registry = registry;
        this.dispatcher = dispatcher;
        this.auditService = auditService;
    }

    public synchronized void start() throws IOException {
        if (pornit) {
            throw new IllegalStateException("Serverul este deja pornit");
        }
        serverSocket = new ServerSocket(port);
        executor = Executors.newCachedThreadPool();
        pornit = true;
        momentPornire = LocalDateTime.now();

        Thread fir = new Thread(this::acceptaConexiuni, "accept-conexiuni");
        fir.setDaemon(true);
        fir.start();
    }

    public synchronized void stop() {
        if (!pornit) {
            throw new IllegalStateException("Serverul este deja oprit");
        }
        pornit = false;
        try {
            serverSocket.close();
        } catch (IOException ignored) {
            // socket-ul se inchide oricum
        }
        registry.deconecteazaToti(MotivDeconectare.SERVER_OPRIT);
        executor.shutdown();
    }

    private void acceptaConexiuni() {
        while (pornit) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, dispatcher, registry, auditService);
                registry.adauga(handler);
                executor.submit(handler);
            } catch (IOException e) {
                if (pornit) {
                    System.err.println("Eroare la acceptarea unei conexiuni: " + e.getMessage());
                }
            }
        }
    }

    public boolean estePornit() {
        return pornit;
    }

    public int getPort() {
        return port;
    }

    public LocalDateTime getMomentPornire() {
        return momentPornire;
    }
}
