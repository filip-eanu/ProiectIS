package ro.magazin.client.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public final class Validator {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFON = Pattern.compile("^\\+?[0-9]{10,12}$");

    private Validator() {
    }

    public static boolean emailValid(String s) {
        return s != null && EMAIL.matcher(s.trim()).matches();
    }

    public static boolean telefonValid(String s) {
        return s != null && TELEFON.matcher(s.replace(" ", "")).matches();
    }

    public static boolean parolaValida(String s) {
        return s != null && s.length() >= 6;
    }

    /** Camp gol = criteriu neaplicat, deci valid. */
    public static boolean pretValid(String s) {
        if (s == null || s.isBlank()) {
            return true;
        }
        try {
            return new BigDecimal(s.trim()).signum() >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
