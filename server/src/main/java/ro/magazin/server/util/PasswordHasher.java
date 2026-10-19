package ro.magazin.server.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PBKDF2 cu salt aleator. Formatul salvat in BD: iteratii:salt:hash (Base64).
 */
public final class PasswordHasher {

    private static final int ITERATII = 65536;
    private static final int LUNGIME_CHEIE = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordHasher() {
    }

    public static String hash(String parola) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        byte[] h = pbkdf2(parola, salt, ITERATII);
        Base64.Encoder b64 = Base64.getEncoder();
        return ITERATII + ":" + b64.encodeToString(salt) + ":" + b64.encodeToString(h);
    }

    public static boolean verifica(String parola, String hashSalvat) {
        String[] parti = hashSalvat.split(":");
        if (parti.length != 3) {
            return false;
        }
        int iteratii = Integer.parseInt(parti[0]);
        byte[] salt = Base64.getDecoder().decode(parti[1]);
        byte[] asteptat = Base64.getDecoder().decode(parti[2]);
        return MessageDigest.isEqual(asteptat, pbkdf2(parola, salt, iteratii));
    }

    private static byte[] pbkdf2(String parola, byte[] salt, int iteratii) {
        try {
            PBEKeySpec spec = new PBEKeySpec(parola.toCharArray(), salt, iteratii, LUNGIME_CHEIE);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    }
}
