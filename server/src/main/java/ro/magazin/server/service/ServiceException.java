package ro.magazin.server.service;

/**
 * Eroare de logica (date invalide, email folosit, stoc insuficient...).
 * Mesajul ajunge nemodificat la utilizator.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String mesaj) {
        super(mesaj);
    }

    public ServiceException(String mesaj, Throwable cauza) {
        super(mesaj, cauza);
    }
}
