package ro.magazin.server.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ServerConfig {

    private final int port;
    private final String dbUrl;
    private final String dbUser;
    private final String dbParola;
    private final String directorImagini;

    private ServerConfig(Properties p) {
        this.port = Integer.parseInt(p.getProperty("port", "5000"));
        this.dbUrl = p.getProperty("db.url");
        this.dbUser = p.getProperty("db.user");
        this.dbParola = p.getProperty("db.parola", "");
        this.directorImagini = p.getProperty("director.imagini", "imagini");
    }

    /**
     * Citeste fisierul din directorul curent; daca nu exista, foloseste configuratia din resurse.
     */
    public static ServerConfig incarca(String fisier) throws IOException {
        Properties p = new Properties();
        try (InputStream implicit = ServerConfig.class.getResourceAsStream("/server.properties")) {
            if (implicit != null) {
                p.load(implicit);
            }
        }
        Path extern = Path.of(fisier);
        if (Files.exists(extern)) {
            try (InputStream in = Files.newInputStream(extern)) {
                p.load(in);
            }
        }
        return new ServerConfig(p);
    }

    public int getPort() {
        return port;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbParola() {
        return dbParola;
    }

    public String getDirectorImagini() {
        return directorImagini;
    }
}
