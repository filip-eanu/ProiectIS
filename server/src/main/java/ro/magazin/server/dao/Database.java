package ro.magazin.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Punctul unic de acces la baza de date. Fiecare apel deschide o conexiune noua,
 * deci se foloseste cu try-with-resources.
 */
public class Database {

    private final String url;
    private final String user;
    private final String parola;

    public Database(String url, String user, String parola) {
        this.url = url;
        this.user = user;
        this.parola = parola;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, parola);
    }
}
