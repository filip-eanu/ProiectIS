-- Baza de date pentru aplicatia "Magazin de calculatoare" (MySQL 8)
-- Rulare: mysql -u root -p < database/schema.sql

DROP DATABASE IF EXISTS magazin_db;
CREATE DATABASE magazin_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE magazin_db;

CREATE TABLE utilizator (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nume         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    telefon      VARCHAR(20)  NOT NULL,
    parola_hash  VARCHAR(255) NOT NULL,
    rol          ENUM('CLIENT', 'ANGAJAT') NOT NULL,
    data_creare  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE produs (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    denumire    VARCHAR(150) NOT NULL,
    categorie   ENUM('LAPTOP', 'PC', 'PROCESOR', 'PLACA_VIDEO', 'MEMORIE_RAM', 'MONITOR', 'PERIFERICE') NOT NULL,
    producator  VARCHAR(100) NOT NULL,
    descriere   TEXT,
    pret        DECIMAL(10, 2) NOT NULL CHECK (pret > 0),
    stoc        INT NOT NULL CHECK (stoc >= 0),
    imagine     VARCHAR(255),
    activ       BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE specificatie (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    id_produs  INT NOT NULL,
    nume       VARCHAR(50)  NOT NULL,
    valoare    VARCHAR(150) NOT NULL,
    FOREIGN KEY (id_produs) REFERENCES produs (id) ON DELETE CASCADE
);

CREATE TABLE element_cos (
    id_utilizator  INT NOT NULL,
    id_produs      INT NOT NULL,
    cantitate      INT NOT NULL CHECK (cantitate > 0),
    PRIMARY KEY (id_utilizator, id_produs),
    FOREIGN KEY (id_utilizator) REFERENCES utilizator (id) ON DELETE CASCADE,
    FOREIGN KEY (id_produs) REFERENCES produs (id)
);

CREATE TABLE comanda (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    id_utilizator   INT NOT NULL,
    data_plasare    DATETIME NOT NULL,
    adresa_livrare  VARCHAR(255) NOT NULL,
    stare           ENUM('PLASATA', 'IN_PROCESARE', 'EXPEDIATA', 'LIVRATA', 'ANULATA') NOT NULL DEFAULT 'PLASATA',
    total           DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_utilizator) REFERENCES utilizator (id)
);

CREATE TABLE linie_comanda (
    id_comanda   INT NOT NULL,
    id_produs    INT NOT NULL,
    cantitate    INT NOT NULL CHECK (cantitate > 0),
    pret_unitar  DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_comanda, id_produs),
    FOREIGN KEY (id_comanda) REFERENCES comanda (id) ON DELETE CASCADE,
    FOREIGN KEY (id_produs) REFERENCES produs (id)
);

CREATE TABLE sesiune (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    adresa_ip           VARCHAR(45) NOT NULL,
    port                INT NOT NULL,
    id_utilizator       INT NULL,
    moment_conectare    DATETIME NOT NULL,
    moment_deconectare  DATETIME NULL,
    motiv_deconectare   ENUM('CLIENT', 'ADMIN', 'SERVER_OPRIT', 'EROARE') NULL,
    FOREIGN KEY (id_utilizator) REFERENCES utilizator (id) ON DELETE SET NULL
);

CREATE TABLE cerere (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    id_sesiune        INT NOT NULL,
    moment            DATETIME NOT NULL,
    tip               VARCHAR(50) NOT NULL,
    continut_cerere   TEXT,
    status_raspuns    VARCHAR(10) NOT NULL,
    continut_raspuns  TEXT,
    FOREIGN KEY (id_sesiune) REFERENCES sesiune (id) ON DELETE CASCADE
);

CREATE INDEX idx_comanda_data ON comanda (data_plasare);
CREATE INDEX idx_sesiune_conectare ON sesiune (moment_conectare);
CREATE INDEX idx_sesiune_deconectare ON sesiune (moment_deconectare);
CREATE INDEX idx_cerere_sesiune_moment ON cerere (id_sesiune, moment);

-- ---------------------------------------------------------------------------
-- Date de test
-- angajat@magazin.ro / angajat123
-- client@magazin.ro  / client123
-- ---------------------------------------------------------------------------

INSERT INTO utilizator (nume, email, telefon, parola_hash, rol) VALUES
('Andrei Popescu', 'angajat@magazin.ro', '0740123456',
 '65536:3pcloXv2Ft/589EwRmQPow==:DbmEj3IHxiA5eZ96lgjDkEUtbZAssT4CbuL6kJWhoE4=', 'ANGAJAT'),
('Maria Ionescu', 'client@magazin.ro', '0751987654',
 '65536:1O5Pnx0C6XW4AnWs55oa8A==:w02Ii2DaT8AM9slxx2wZtXFELvuFi0unbhYPm6sK4t4=', 'CLIENT');

INSERT INTO produs (denumire, categorie, producator, descriere, pret, stoc) VALUES
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

INSERT INTO specificatie (id_produs, nume, valoare) VALUES
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
