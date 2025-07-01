use smart_city;
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Acerbi', 'Luciana', 'RN099529AA', 'oGIGdLDZxwxyeGEr');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Albertini', 'Alessandro', 'jB857186AA', 'UpOpKvBFzLRBKIfy');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Luzi', 'Michelotto', 'QB954597AA', 'krOvQhTAHjILUdzI');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Falcone', 'Rossana', 'Cw797006AA', 'CIIDWQMMPRdEvHFz');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Casalodi', 'Ivan', 'ft623654AA', 'WTJzzMVcbJLhtUbD');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Maderno', 'Livia', 'PX271349AA', 'VedABedjpjFggnWQ');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Columbo', 'Rocco', 'NQ203412AA', 'oHWniLtmxQCbBeIY');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Polani', 'Annibale', 'MM790278AA', 'gLUtcwSCoqXrDwbe');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Trincavelli', 'Natalia', 'dQ994531AA', 'lcEFyvNoXqNIEzNf');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('Piccio', 'Lamberto', 'Wr384506AA', 'RhnWEphWSpSmChVk');
INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale) VALUES ('admin', 'admin', 'Amm91332AA', NULL);
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('nico', 'RN099529AA', 'rpalombi@argento.net', '3456503582', '$2a$12$1qwIWDXeO47MR8bglBL2ZuB6z8SEzF08CqkPvZM3F/2M.T.BKhv8K');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user1', 'jB857186AA', 'antonelloungaretti@live.com', '3453524521', '$2a$12$.RuAc9vq9hlEQDQW1Z5GY.wnnahTZSva9ManxToj9uyfKrRY.IBwe');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user2', 'QB954597AA', 'qossani@vodafone.it', '3486012374', '$2a$12$pEsEeAH6l5UpqE1.RXANzOgqVRiH5cC7TEZybfApH4Qb4o8QrBkui');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user3', 'Cw797006AA', 'stradivarisante@mercadante-missoni.net', '3485013376', '$2a$12$l5s05QPs/2L1x56ilccpa.HxweRGoVqQ3YiSwkz385Ajxcb9dBpJC');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user4', 'ft623654AA', 'ottavio03@mascheroni.net', '3452011596', '$2a$12$V/WmX3A8u/0H/OHt2n94M.MjfScLGAKKxOpo/nCVOXZWLFZ0Bhfai');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user5', 'PX271349AA', 'clelialucciano@fastwebnet.it', '3458769532', '$2a$12$4c02U/MO1ecsrRSTyNNks.WNS1p1rZFjRdtdPzmGtYabsgtWF9QqO');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user6', 'NQ203412AA', 'deperonicoletta@outlook.com', '3487119865', '$2a$12$z69RtNKUMRw2con6pP5mMO4cnGef8G.74nE4rZzI1/o.KAPs6Ufau');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user7', 'MM790278AA', 'mazzeoeugenia@tiscali.it', '3452087961', '$2a$12$vzV792pkBogOKhkqLcYOrOza0ct/ZC1iIPmrYqR1PDvQhsdbZ8aoi');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user8', 'dQ994531AA', 'fernanda48@hotmail.it', '3485631752', '$2a$12$l1PQOP8ADFSQ1yzVShfQ6O3C6zLDOy9rojIdbJesq/LRl4iBtGmRW');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('user9', 'Wr384506AA', 'marianaccari@gmail.com', '3456503892', '$2a$12$vaR/Hfa3G01XoTS4iM2eHeUoBos1Xibkf.ys9X0tOqXYSsE7zUWT.');
INSERT INTO UTENTI (username, documento, email, telefono, password) VALUES ('admin', 'Amm91332AA', 'admin@smartcity.it', '123456789', '$2a$10$u43PXOBQkSbOwUtbrNeot./Qk1qAf0EAnNT5KDwYgBEnuxtTk8J5q');
INSERT INTO DIPENDENTI (username, ruolo) VALUES ('nico', 'amministrativo');
INSERT INTO DIPENDENTI (username, ruolo) VALUES ('user1', 'controllore');
INSERT INTO DIPENDENTI (username, ruolo) VALUES ('user2', 'amministrativo');
INSERT INTO DIPENDENTI (username, ruolo) VALUES ('user3', 'controllore');
INSERT INTO DIPENDENTI (username, ruolo) VALUES ('user4', 'amministrativo');
INSERT INTO DIPENDENTI (username, ruolo) VALUES ('admin', 'amministrativo');
INSERT INTO TIPOLOGIE_MEZZI (codice_tipo_mezzo, nome) VALUES (1, 'autobus');
INSERT INTO TIPOLOGIE_MEZZI (codice_tipo_mezzo, nome) VALUES (2, 'treno');
INSERT INTO TIPOLOGIE_MEZZI (codice_tipo_mezzo, nome) VALUES (3, 'tram');
INSERT INTO TIPOLOGIE_MEZZI (codice_tipo_mezzo, nome) VALUES (4, 'pollicino');
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0000', 1);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0001', 4);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0002', 3);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0003', 2);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0004', 2);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0005', 4);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0006', 1);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0007', 3);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0008', 4);
INSERT INTO MEZZI (n_immatricolazione, codice_tipo_mezzo) VALUES ('MEZZO0009', 1);
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Viale Luisa', 'Vicolo Mirko', 'Settimo Edoardo calabro', '36', 30379, '154.216666', '14.414258');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Rotonda Gualtieri', 'Strada Iannuzzi', 'San Ermenegildo', '28', 54118, '24.255935', '31.600090');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Piazza Saffi', 'Borgo Michelangelo', 'Quarto Rembrandt', '14', 22156, '93.924769', '64.296773');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Piazza Turci', 'Viale Carosone', 'Virgilio sardo', '49', 22676, '104.950356', '-44.948290');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Piazza Coluccio', 'Piazza Contrafatto', 'Valerio ligure', '46', 55082, '54.333364', '67.126381');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Contrada Dionigi', 'Incrocio Licia', 'Pirelli sardo', '78', 44671, '-118.435510', '-89.246693');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Canale Lucrezia', 'Contrada Rodolfo', 'Sesto Fabia', '6', 70217, '-39.659408', '60.757050');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Incrocio Broschi', 'Incrocio Fabio', 'Claudia terme', '69', 26361, '49.780065', '85.596144');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Piazza Manunta', 'Incrocio Sante', 'Settimo Stefani', '49', 20328, '-13.652697', '-6.664619');
INSERT INTO FERMATE (nome, indirizzo_via, indirizzo_comune, indirizzo_civico, indirizzo_cap, longitudine, latitudine) VALUES ('Viale Casellati', 'Viale Burcardo', 'Settimo Fiorenzo veneto', '71', 48427, '59.945995', '-42.369764');
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('-104.157981', '85.244772', 'Minus', 'Borgo Borsellino', 'Settimo Cassandra calabro', 92397, '80', 6);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('163.235535', '-48.802245', 'Harum', 'Vicolo Longhena', 'Quarto Salvi sardo', 85674, '25', 2);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('116.405225', '88.195650', 'Dolore', 'Canale Giunti', 'Nolcini nell emilia', 16006, '85', 4);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('-102.962329', '22.079695', 'Dolore', 'Stretto Angiolello', 'San Annunziata', 47930, '11', 4);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('157.670994', '58.839210', 'Eum', 'Vicolo Tiziano', 'San Gastone', 23238, '49', 5);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('158.669748', '-55.519629', 'Incidunt', 'Borgo Mauro', 'Ivo veneto', 69429, '82', 6);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('-80.506922', '69.826461', 'Vel', 'Via Dovara', 'Maria nell emilia', 31319, '48', 6);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('-9.868611', '-66.070690', 'Quisquam', 'Vicolo Teresa', 'Sesto Liberto', 37460, '86', 5);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('73.011574', '-9.643581', 'Explicabo', 'Borgo Alfio', 'Orazio salentino', 99593, '83', 2);
INSERT INTO HUB_MOBILITA (longitudine, latitudine, nome, indirizzo_via, indirizzo_comune, indirizzo_cap, indirizzo_civico, codice_fermata) VALUES ('96.874255', '-7.980940', 'Rerum', 'Strada Rubbia', 'Cianciolo terme', 89840, '82', 3);
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('66108967019', 'Gentileschi, Beccaria e Castellitto SPA', '69', 'Piazza Bellò', 'Borgo Melina a mare', 42087, '4951477040', 'ltreccani@borghese.com');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('73908386852', 'Galeati, Trillini e Iannuzzi SPA', '21', 'Via Ligorio', 'Borgo Arnulfo', 70589, '8343897493', 'clattuada@gigli.com');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('30732476298', 'Serao, Malacarne e Gasperi s.r.l.', '49', 'Contrada Nico', 'Corrado ligure', 45382, '207735989', 'laura61@tele2.it');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('41233010444', 'Trussardi Group', '82', 'Rotonda Gigli', 'Orengo ligure', 83000, '6966652368', 'giustino28@bondumier.com');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('40510816806', 'Botticelli-Salata SPA', '29', 'Piazza Dina', 'Ottone sardo', 99733, '66009998621', 'smigliaccio@outlook.com');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('09961224285', 'Toscani e figli', '42', 'Strada Galuppi', 'San Concetta a mare', 17331, '96468232920', 'barbarasordi@vodafone.it');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('94390260841', 'Guglielmi, Gibilisco e Liguori SPA', '30', 'Canale Castellitto', 'Erika salentino', 14207, '11782378193', 'camilla69@ricci-pareto.it');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('07519070282', 'Disdero-Oliboni s.r.l.', '41', 'Canale Michela', 'Cesarotti umbro', 62581, '0684034738', 'salvatoregianetti@virgilio.it');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('05485580912', 'Cannizzaro, Briccialdi e Ceravolo SPA', '35', 'Viale Benussi', 'San Calcedonio lido', 18675, '027321115', 'flavio91@vodafone.it');
INSERT INTO AZIENDE (p_iva, ragione_sociale, indirizzo_civico, indirizzo_via, indirizzo_comune, indirizzo_cap, telefono, email) VALUES ('80997108159', 'Carli-Riccardi Group', '28', 'Canale Augusto', 'San Umberto umbro', 84341, '6848820571', 'marco85@carosone.com');
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Aliquam', 1, 45.05);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Dolorem', 2, 22.59);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Natus', 3, 36.22);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Harum', 4, 25.83);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Molestias', 5, 46.58);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Tempore', 6, 28.35);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Eum', 7, 20.60);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Facilis', 8, 19.87);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Unde', 9, 32.45);
INSERT INTO TARIFFE_ABBONAMENTI (nome, num_giorni, prezzo) VALUES ('Dolorem', 10, 20.51);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Vero', 1, 3.34);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Maxime', 2, 4.59);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Adipisci', 3, 2.60);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Eaque', 4, 1.88);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Exercitationem', 5, 4.99);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Ratione', 6, 3.04);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Non', 7, 1.36);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Dolores', 8, 1.19);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Nemo', 9, 1.44);
INSERT INTO TARIFFE_BIGLIETTI (nome, durata, prezzo) VALUES ('Vero', 10, 3.51);
INSERT INTO LINEE (codice_linea, tempo_percorrenza, inizio_validita, fine_validita, attiva, codice_tipo_mezzo) VALUES
('L001', 35, '2025-01-01', '2025-12-31', TRUE, 1),
('L002', 50, '2025-02-01', '2025-11-30', TRUE, 2),
('L003', 40, '2025-03-01', NULL, TRUE, 3),
('L004', 60, '2024-05-01', '2025-05-01', FALSE, 4),
('L005', 45, '2025-06-01', '2025-12-01', TRUE, 1),
('L006', 55, '2025-01-15', NULL, TRUE, 2),
('L007', 30, '2024-09-01', '2025-09-01', TRUE, 3),
('L008', 70, '2025-04-01', NULL, TRUE, 4),
('L009', 25, '2025-05-15', '2025-10-15', FALSE, 3),
('L010', 80, '2025-06-01', NULL, TRUE, 1);
INSERT INTO CONTENUTI_HUB (`codice_contenuto`,`descrizione`) VALUES (1,'monopattini');
INSERT INTO CONTENUTI_HUB (`codice_contenuto`,`descrizione`) VALUES (2,'bici');
INSERT INTO CONTENUTI_HUB (`codice_contenuto`,`descrizione`) VALUES (3,'macchine elettriche');
INSERT INTO CONTENUTI_HUB (`codice_contenuto`,`descrizione`) VALUES (4,'scooter elettrici');
