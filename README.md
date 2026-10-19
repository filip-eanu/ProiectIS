# Magazin de calculatoare

Proiect Inginerie Software: aplicatie client/server pentru un magazin de calculatoare.

| Modul | Continut |
|---|---|
| `server/` | aplicatia server (consola): socket TCP, servicii, acces la BD, comenzi administrator |
| `client/` | aplicatia client (JavaFX, MVC) |
| `common/` | protocolul de comunicare (`Request`, `Response`) si DTO-urile folosite de ambele aplicatii |
| `database/` | scriptul bazei de date (`schema.sql`) |
| `docs/diagrame/` | diagramele UML |

## Cerinte

- JDK 21 sau mai nou
- Maven 3.9+
- MySQL 8

## Rulare

```bash
# 1. baza de date
mysql -u root -p < database/schema.sql

# 2. build
mvn install

# 3. serverul (configurare in server.properties: port, conexiune BD)
java -jar server/target/server.jar

# 4. clientul (se pot porni mai multe instante)
mvn -pl client javafx:run
```

Conturi de test: `angajat@magazin.ro` / `angajat123`, `client@magazin.ro` / `client123`.

## Comenzi administrator (consola serverului)

```
start | stop | status
count
clients
kick <id sesiune>
history connect <de> <pana> [fisier]
history disconnect <de> <pana> [fisier]
history requests <id sesiune> <de> <pana> [fisier.csv]
exit
```

Datele se scriu in formatul `yyyy-MM-dd`.

## Protocol

Clientul si serverul schimba mesaje JSON, cate unul pe linie:

```json
{"tip": "AUTENTIFICARE", "payload": {"email": "client@magazin.ro", "parola": "client123"}}
{"status": "OK", "mesaj": null, "payload": {"id": 2, "nume": "Maria Ionescu", "rol": "CLIENT"}}
```

Tipurile de cereri sunt in `common/.../protocol/TipCerere.java`, iar traseul fiecarei cereri pe server in `RequestDispatcher`.
