-- Baza de date pentru aplicatia "Magazin de calculatoare" (MySQL 8)
-- Rulare: mysql -u root -p < database/schema.sql

DROP DATABASE IF EXISTS magazin_db;
CREATE DATABASE magazin_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE magazin_db;

CREATE TABLE Utilizator (
    IdUtilizator  INT AUTO_INCREMENT PRIMARY KEY,
    Nume          VARCHAR(100) NOT NULL,
    Email         VARCHAR(100) NOT NULL UNIQUE,
    Telefon       VARCHAR(20)  NOT NULL,
    Parola        VARCHAR(255) NOT NULL,  -- hash PBKDF2, nu parola in clar
    Rol           ENUM('CLIENT', 'ANGAJAT') NOT NULL,
    DataCreare    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Produs (
    IdProdus    INT AUTO_INCREMENT PRIMARY KEY,
    Denumire    VARCHAR(150) NOT NULL,
    Categorie   ENUM('LAPTOP', 'PC', 'PROCESOR', 'PLACA_VIDEO', 'MEMORIE_RAM', 'MONITOR', 'PERIFERICE') NOT NULL,
    Producator  VARCHAR(100) NOT NULL,
    Descriere   TEXT NULL,
    Pret        DECIMAL(10, 2) NOT NULL CHECK (Pret > 0),
    Stoc        INT NOT NULL CHECK (Stoc >= 0),
    Imagine     VARCHAR(255) NULL,
    Activ       BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE Specificatie (
    IdSpecificatie  INT AUTO_INCREMENT PRIMARY KEY,
    IdProdus        INT NOT NULL,
    Nume            VARCHAR(50)  NOT NULL,
    Valoare         VARCHAR(150) NOT NULL,
    FOREIGN KEY (IdProdus) REFERENCES Produs (IdProdus) ON DELETE CASCADE
);

CREATE TABLE ElementCos (
    IdElementCos  INT AUTO_INCREMENT PRIMARY KEY,
    IdUtilizator  INT NOT NULL,
    IdProdus      INT NOT NULL,
    Cantitate     INT NOT NULL CHECK (Cantitate > 0),
    UNIQUE (IdUtilizator, IdProdus),
    FOREIGN KEY (IdUtilizator) REFERENCES Utilizator (IdUtilizator) ON DELETE CASCADE,
    FOREIGN KEY (IdProdus) REFERENCES Produs (IdProdus)
);

CREATE TABLE Comanda (
    IdComanda      INT AUTO_INCREMENT PRIMARY KEY,
    IdUtilizator   INT NOT NULL,
    DataPlasare    DATETIME NOT NULL,
    AdresaLivrare  VARCHAR(255) NOT NULL,
    Stare          ENUM('PLASATA', 'IN_PROCESARE', 'EXPEDIATA', 'LIVRATA', 'ANULATA') NOT NULL DEFAULT 'PLASATA',
    Total          DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (IdUtilizator) REFERENCES Utilizator (IdUtilizator)
);

CREATE TABLE LinieComanda (
    IdLinieComanda  INT AUTO_INCREMENT PRIMARY KEY,
    IdComanda       INT NOT NULL,
    IdProdus        INT NOT NULL,
    Cantitate       INT NOT NULL CHECK (Cantitate > 0),
    PretUnitar      DECIMAL(10, 2) NOT NULL,
    UNIQUE (IdComanda, IdProdus),
    FOREIGN KEY (IdComanda) REFERENCES Comanda (IdComanda) ON DELETE CASCADE,
    FOREIGN KEY (IdProdus) REFERENCES Produs (IdProdus)
);

CREATE TABLE Sesiune (
    IdSesiune          INT AUTO_INCREMENT PRIMARY KEY,
    IdUtilizator       INT NULL,
    AdresaIp           VARCHAR(45) NOT NULL,
    Port               INT NOT NULL,
    MomentConectare    DATETIME NOT NULL,
    MomentDeconectare  DATETIME NULL,
    MotivDeconectare   ENUM('CLIENT', 'ADMIN', 'SERVER_OPRIT', 'EROARE') NULL,
    FOREIGN KEY (IdUtilizator) REFERENCES Utilizator (IdUtilizator) ON DELETE SET NULL
);

CREATE TABLE Cerere (
    IdCerere         INT AUTO_INCREMENT PRIMARY KEY,
    IdSesiune        INT NOT NULL,
    Moment           DATETIME NOT NULL,
    Tip              VARCHAR(50) NOT NULL,
    ContinutCerere   TEXT NULL,
    StatusRaspuns    VARCHAR(10) NOT NULL,
    ContinutRaspuns  TEXT NULL,
    FOREIGN KEY (IdSesiune) REFERENCES Sesiune (IdSesiune) ON DELETE CASCADE
);

CREATE INDEX IdxComandaData ON Comanda (DataPlasare);
CREATE INDEX IdxSesiuneConectare ON Sesiune (MomentConectare);
CREATE INDEX IdxSesiuneDeconectare ON Sesiune (MomentDeconectare);
CREATE INDEX IdxCerereSesiuneMoment ON Cerere (IdSesiune, Moment);

-- ---------------------------------------------------------------------------
-- Date de test
-- angajat@magazin.ro / angajat123
-- client@magazin.ro  / client123
-- ---------------------------------------------------------------------------

INSERT INTO Utilizator (Nume, Email, Telefon, Parola, Rol) VALUES
('Andrei Popescu', 'angajat@magazin.ro', '0740123456',
 '65536:3pcloXv2Ft/589EwRmQPow==:DbmEj3IHxiA5eZ96lgjDkEUtbZAssT4CbuL6kJWhoE4=', 'ANGAJAT'),
('Maria Ionescu', 'client@magazin.ro', '0751987654',
 '65536:1O5Pnx0C6XW4AnWs55oa8A==:w02Ii2DaT8AM9slxx2wZtXFELvuFi0unbhYPm6sK4t4=', 'CLIENT');

INSERT INTO Produs (Denumire, Categorie, Producator, Descriere, Pret, Stoc) VALUES
('Laptop Lenovo IdeaPad Slim 5', 'LAPTOP', 'Lenovo', 'Laptop subtire de 14 inch, potrivit pentru birou si facultate.', 3599.99, 8),
('Laptop ASUS TUF Gaming A15', 'LAPTOP', 'ASUS', 'Laptop de gaming cu ecran de 144 Hz.', 4999.00, 5),
('Sistem PC Gaming Ryzen 7', 'PC', 'Serioux', 'Sistem asamblat pentru gaming si editare video.', 6299.00, 3),
('Procesor AMD Ryzen 5 7600', 'PROCESOR', 'AMD', '6 nuclee, 12 fire de executie, socket AM5.', 949.90, 15),
('Procesor Intel Core i5-14400F', 'PROCESOR', 'Intel', '10 nuclee, socket LGA1700, fara grafica integrata.', 899.00, 12),
('Placa video NVIDIA GeForce RTX 4060', 'PLACA_VIDEO', 'Gigabyte', '8 GB GDDR6, potrivita pentru 1080p.', 1499.00, 7),
('Memorie Kingston FURY Beast 32GB DDR5', 'MEMORIE_RAM', 'Kingston', 'Kit 2 x 16 GB, 5600 MHz.', 489.99, 20),
('Monitor Dell S2721DS 27"', 'MONITOR', 'Dell', 'Panou IPS, rezolutie 2560x1440, 75 Hz.', 1149.00, 6),
('Mouse Logitech G502 HERO', 'PERIFERICE', 'Logitech', 'Mouse de gaming cu senzor de 25600 DPI.', 229.99, 30),
('Tastatura mecanica Redragon Kumara', 'PERIFERICE', 'Redragon', 'Tastatura TKL cu switch-uri Outemu Red.', 179.00, 0);

INSERT INTO Specificatie (IdProdus, Nume, Valoare) VALUES
(1, 'Procesor', 'AMD Ryzen 5 7530U'),
(1, 'Memorie RAM', '16 GB DDR4'),
(1, 'Stocare', '512 GB SSD NVMe'),
(1, 'Placa video', 'AMD Radeon integrata'),
(2, 'Procesor', 'AMD Ryzen 7 7735HS'),
(2, 'Memorie RAM', '16 GB DDR5'),
(2, 'Stocare', '1 TB SSD NVMe'),
(2, 'Placa video', 'NVIDIA GeForce RTX 4050 6 GB'),
(3, 'Procesor', 'AMD Ryzen 7 7700'),
(3, 'Memorie RAM', '32 GB DDR5'),
(3, 'Stocare', '2 TB SSD NVMe'),
(3, 'Placa video', 'NVIDIA GeForce RTX 4070 12 GB'),
(4, 'Frecventa', '3.8 - 5.1 GHz'),
(5, 'Frecventa', '2.5 - 4.7 GHz'),
(6, 'Memorie', '8 GB GDDR6'),
(7, 'Frecventa', '5600 MHz'),
(8, 'Diagonala', '27 inch');
