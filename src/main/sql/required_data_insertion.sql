-- Basic data Insert
-- _____________

-- Admin User
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('admin', 'admin', 'Amm91332AA', NULL);
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('admin', 'Amm91332AA', 'admin@smartcity.it', '123456789', '$2a$10$u43PXOBQkSbOwUtbrNeot./Qk1qAf0EAnNT5KDwYgBEnuxtTk8J5q');
INSERT INTO DIPENDENTI (username, ruolo) VALUES ('admin', 'amministrativo');

-- Tipi mezzi
INSERT INTO TIPOLOGIE_MEZZI (codice_tipo_mezzo, nome) VALUES (1, 'Autobus'), (2, 'Treno'), (3, 'Tram'), (4, 'Metro');

-- Mezzi
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('BUS000', 1), ('METRO000', 4), ('TRAM000', 3), ('TRENO000', 2), ('TRENO001', 2), ('METRO001', 4), ('BUS001', 1), ('TRAM001', 3), ('METRO002', 4), ('BUS002', 1);

-- Contenuti hub
INSERT INTO CONTENUTI_HUB (codice_contenuto, descrizione) VALUES (1, 'Monopattini'), (2, 'Bici'), (3, 'Macchine elettriche'), (4, 'Scooter elettrici');

-- Tariffe
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Mensile', 31, 39.50), ('Semestrale', 183, 200.00), ('Annuale', 365, 330.00), ('Quinquennale', 1827, 1320.00);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('90min', 90, 2.20), ('Giornaliero', 1440, 7.60), ('Tre giorni', 4320, 15.50);

-- Aziende
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES
	('66108967019', 'Gentileschi, Beccaria e Castellitto SPA', '69', 'Piazza Bellò', 'Borgo Melina a mare', 42087, '4951477040', 'ltreccani@borghese.com'),
	('73908386852', 'Galeati, Trillini e Iannuzzi SPA', '21', 'Via Ligorio', 'Borgo Arnulfo', 70589, '8343897493', 'clattuada@gigli.com'),
	('30732476298', 'Serao, Malacarne e Gasperi s.r.l.', '49', 'Contrada Nico', 'Corrado ligure', 45382, '207735989', 'laura61@tele2.it'),
	('41233010444', 'Trussardi Group', '82', 'Rotonda Gigli', 'Orengo ligure', 83000, '6966652368', 'giustino28@bondumier.com'),
	('80997108159', 'Carli-Riccardi Group', '28', 'Canale Augusto', 'San Umberto umbro', 84341, '6848820571', 'marco85@carosone.com');

-- Causali Multe
INSERT INTO CAUSALI_MULTE(codice_causale, reato, prezzo_base, prezzo_massimo) VALUES
     (1, "Mancanza titolo di viaggio", 20, 500),
     (2, "Deturpazione mezzi di trasporto", 50, 1000);