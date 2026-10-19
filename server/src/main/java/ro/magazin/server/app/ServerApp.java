package ro.magazin.server.app;

import ro.magazin.server.admin.AdminConsole;
import ro.magazin.server.config.ServerConfig;
import ro.magazin.server.dao.Database;
import ro.magazin.server.network.ClientRegistry;
import ro.magazin.server.network.RequestDispatcher;
import ro.magazin.server.network.Server;
import ro.magazin.server.service.AuditService;
import ro.magazin.server.service.AuthService;
import ro.magazin.server.service.ComandaService;
import ro.magazin.server.service.CosService;
import ro.magazin.server.service.ProdusService;
import ro.magazin.server.service.StatisticiService;
import ro.magazin.server.service.UtilizatorService;
import ro.magazin.server.util.CsvWriter;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        ServerConfig config = ServerConfig.incarca("server.properties");
        Database database = new Database(config.getDbUrl(), config.getDbUser(), config.getDbParola());

        AuditService auditService = new AuditService(database);
        RequestDispatcher dispatcher = new RequestDispatcher(
                new AuthService(database),
                new ProdusService(database),
                new CosService(database),
                new ComandaService(database),
                new UtilizatorService(database),
                new StatisticiService(database));

        ClientRegistry registry = new ClientRegistry();
        Server server = new Server(config.getPort(), registry, dispatcher, auditService);
        AdminConsole consola = new AdminConsole(server, registry, auditService, new CsvWriter());

        consola.pornesteServerul();
        consola.run();
    }
}
