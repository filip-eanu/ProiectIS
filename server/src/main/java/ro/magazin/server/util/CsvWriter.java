package ro.magazin.server.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvWriter {

    public void scrie(Path cale, String[] antet, List<String[]> randuri) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(cale, StandardCharsets.UTF_8)) {
            scrieRand(w, antet);
            for (String[] rand : randuri) {
                scrieRand(w, rand);
            }
        }
    }

    private void scrieRand(BufferedWriter w, String[] valori) throws IOException {
        for (int i = 0; i < valori.length; i++) {
            if (i > 0) {
                w.write(',');
            }
            w.write(escape(valori[i]));
        }
        w.newLine();
    }

    private String escape(String v) {
        if (v == null) {
            return "";
        }
        if (v.contains(",") || v.contains("\"") || v.contains("\n") || v.contains("\r")) {
            return "\"" + v.replace("\"", "\"\"") + "\"";
        }
        return v;
    }
}
