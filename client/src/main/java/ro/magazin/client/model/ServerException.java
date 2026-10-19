package ro.magazin.client.model;

/**
 * Serverul a raspuns cu eroare sau conexiunea s-a pierdut. Mesajul se poate afisa direct utilizatorului.
 */
public class ServerException extends RuntimeException {

    public ServerException(String mesaj) {
        super(mesaj);
    }
}
