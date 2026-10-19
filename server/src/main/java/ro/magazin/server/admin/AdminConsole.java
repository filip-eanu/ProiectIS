package ro.magazin.server.admin;

import ro.magazin.server.model.CerereLog;
import ro.magazin.server.model.MotivDeconectare;
import ro.magazin.server.model.Sesiune;
import ro.magazin.server.network.ClientHandler;
import ro.magazin.server.network.ClientRegistry;
import ro.magazin.server.network.Server;
import ro.magazin.server.service.AuditService;
import ro.magazin.server.util.CsvWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interfata administratorului: comenzi citite din consola serverului.
 */
public class AdminConsole implements Runnable {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Server server;
    private final ClientRegistry registry;
    private final AuditService auditService;
    private final CsvWriter csvWriter;

    public AdminConsole(Server server, ClientRegistry registry, AuditService auditService, CsvWriter csvWriter) {
        this.server = server;
        this.registry = registry;
        this.auditService = auditService;
        this.csvWriter = csvWriter;
    }

    @Override
    public void run() {
        System.out.println("Scrieti 'help' pentru lista de comenzi.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String linie = scanner.nextLine().trim();
            if (linie.equals("exit")) {
                break;
            }
            if (!linie.isEmpty()) {
                executa(linie);
            }
        }
        if (server.estePornit()) {
            server.stop();
        }
        System.out.println("La revedere.");
    }

    private void executa(String linie) {
        String[] p = linie.split("\\s+");
        try {
            switch (p[0]) {
                case "help" -> afiseazaAjutor();
                case "start" -> pornesteServerul();
                case "stop" -> stop();
                case "status" -> status();
                case "count" -> count();
                case "clients" -> clients();
                case "kick" -> kick(p);
                case "history" -> history(p);
                default -> System.out.println("Comanda necunoscuta. Scrieti 'help'.");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Functionalitate neimplementata inca.");
        }
    }

    public void pornesteServerul() {
        if (server.estePornit()) {
            System.out.println("Serverul este deja pornit.");
            return;
        }
        try {
            server.start();
            System.out.println("Server pornit pe portul " + server.getPort() + ".");
        } catch (IOException e) {
            System.out.println("Serverul nu a putut porni pe portul " + server.getPort() + ": " + e.getMessage());
        }
    }

    private void stop() {
        if (!server.estePornit()) {
            System.out.println("Serverul este deja oprit.");
            return;
        }
        server.stop();
        System.out.println("Server oprit.");
    }

    private void status() {
        if (!server.estePornit()) {
            System.out.println("Stare: OPRIT");
            return;
        }
        Duration d = Duration.between(server.getMomentPornire(), LocalDateTime.now());
        System.out.printf("Stare: PORNIT | port %d | pornit la %s (%dh %dm) | clienti conectati: %d%n",
                server.getPort(), server.getMomentPornire().format(FORMAT),
                d.toHours(), d.toMinutesPart(), registry.numar());
    }

    private void count() {
        if (!server.estePornit()) {
            System.out.println("Serverul este oprit (0 clienti).");
        } else {
            System.out.println("Clienti conectati: " + registry.numar());
        }
    }

    private void clients() {
        List<ClientHandler> clienti = registry.getToti();
        if (clienti.isEmpty()) {
            System.out.println("Niciun client conectat.");
            return;
        }
        System.out.printf("%-8s %-22s %-30s %s%n", "Sesiune", "Adresa", "Utilizator", "Conectat la");
        for (ClientHandler c : clienti) {
            String utilizator = c.getUtilizator() == null ? "(neautentificat)" : c.getUtilizator().getEmail();
            System.out.printf("%-8d %-22s %-30s %s%n", c.getIdSesiune(), c.getAdresa(), utilizator,
                    c.getMomentConectare().format(FORMAT));
        }
    }

    private void kick(String[] p) {
        if (p.length != 2) {
            System.out.println("Utilizare: kick <id sesiune>");
            return;
        }
        ClientHandler c;
        try {
            c = registry.get(Long.parseLong(p[1]));
        } catch (NumberFormatException e) {
            c = null;
        }
        if (c == null) {
            System.out.println("Sesiune inexistenta.");
            return;
        }
        c.deconecteaza(MotivDeconectare.ADMIN);
        System.out.println("Client deconectat.");
    }

    /**
     * history connect    <de la> <pana la> [fisier]
     * history disconnect <de la> <pana la> [fisier]
     * history requests   <id sesiune> <de la> <pana la> [fisier.csv]
     * Datele au formatul yyyy-MM-dd; intervalul include ambele zile.
     */
    private void history(String[] p) {
        if (p.length < 2) {
            afiseazaAjutor();
            return;
        }
        boolean cereri = p[1].equals("requests");
        int start = cereri ? 3 : 2;
        if (p.length < start + 2 || !(cereri || p[1].equals("connect") || p[1].equals("disconnect"))) {
            afiseazaAjutor();
            return;
        }

        LocalDateTime de;
        LocalDateTime pana;
        try {
            de = LocalDate.parse(p[start]).atStartOfDay();
            pana = LocalDate.parse(p[start + 1]).plusDays(1).atStartOfDay().minusNanos(1);
        } catch (DateTimeParseException e) {
            System.out.println("Data invalida. Formatul este yyyy-MM-dd.");
            return;
        }
        if (de.isAfter(pana)) {
            System.out.println("Interval invalid: data de inceput este dupa data de sfarsit.");
            return;
        }
        String fisier = p.length > start + 2 ? p[start + 2] : null;

        String[] antet;
        List<String[]> randuri = new ArrayList<>();
        if (cereri) {
            long idSesiune;
            try {
                idSesiune = Long.parseLong(p[2]);
            } catch (NumberFormatException e) {
                System.out.println("Id de sesiune invalid.");
                return;
            }
            List<CerereLog> lista = auditService.getCereri(idSesiune, de, pana);
            if (lista == null) {
                System.out.println("Sesiune inexistenta.");
                return;
            }
            antet = new String[]{"moment", "tip", "cerere", "status", "raspuns"};
            for (CerereLog c : lista) {
                randuri.add(new String[]{c.getMoment().format(FORMAT), c.getTip(), c.getContinutCerere(),
                        c.getStatusRaspuns(), c.getContinutRaspuns()});
            }
        } else {
            boolean conectari = p[1].equals("connect");
            List<Sesiune> lista = conectari ? auditService.getConectari(de, pana)
                    : auditService.getDeconectari(de, pana);
            antet = new String[]{"moment", "sesiune", "adresa", "utilizator", "motiv"};
            for (Sesiune s : lista) {
                LocalDateTime moment = conectari ? s.getMomentConectare() : s.getMomentDeconectare();
                randuri.add(new String[]{moment.format(FORMAT), String.valueOf(s.getId()),
                        s.getAdresaIp() + ":" + s.getPort(),
                        s.getIdUtilizator() == null ? "" : String.valueOf(s.getIdUtilizator()),
                        s.getMotiv() == null ? "" : s.getMotiv().name()});
            }
        }

        if (randuri.isEmpty()) {
            System.out.println("Nicio inregistrare in intervalul selectat.");
            return;
        }
        System.out.println(String.join(" | ", antet));
        for (String[] r : randuri) {
            System.out.println(String.join(" | ", r));
        }
        if (fisier != null) {
            try {
                csvWriter.scrie(Path.of(fisier), antet, randuri);
                System.out.println("Istoric salvat in " + fisier);
            } catch (IOException e) {
                System.out.println("Fisierul nu a putut fi scris: " + e.getMessage());
            }
        }
    }

    private void afiseazaAjutor() {
        System.out.println("""
                Comenzi:
                  start | stop | status          porneste / opreste serverul, afiseaza starea
                  count                          numarul de clienti conectati
                  clients                        lista clientilor conectati
                  kick <id sesiune>              deconecteaza un client
                  history connect <de> <pana> [fisier]
                  history disconnect <de> <pana> [fisier]
                  history requests <id sesiune> <de> <pana> [fisier.csv]
                                                 datele in formatul yyyy-MM-dd
                  exit                           opreste serverul si inchide aplicatia""");
    }
}
