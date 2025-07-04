-- *********************************************
-- * SQL MySQL generation
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2
-- * Generator date: Sep 14 2021
-- * Generation date: Fri May 30 12:06:12 2025
-- * LUN file: Schemi.lun
-- * Schema: smart_city/logico
-- *********************************************


-- Database Section
-- ________________

create database if not exists smart_city;
use smart_city;


-- Tables Section
-- _____________

create table ABBONAMENTI (
     data_inizio date not null,
     codice_abbonamento int not null,
     data_acquisto date not null,
     num_giorni int not null,
     username varchar(30) not null,
     constraint ID_ABBONAMENTO primary key (codice_abbonamento));

create table ATTUAZIONI_CORSE (
     codice_corsa bigint auto_increment not null,
     data date not null,
     codice_orario bigint not null,
     n_immatricolazione varchar(20) not null,
     username varchar(30) not null,
     constraint SID_ATTUAZIONE_CORSA unique (codice_orario, data),
     constraint ID_ATTUAZIONE_CORSA primary key (codice_corsa));

create table AZIENDE (
     p_iva char(11) not null,
     ragione_sociale varchar(40) not null,
     indirizzo_civico varchar(10) not null,
     indirizzo_via varchar(50) not null,
     indirizzo_comune varchar(50) not null,
     indirizzo_cap int not null,
     telefono varchar(14) not null,
     email varchar(40) not null,
     constraint ID_AZIENDA primary key (p_iva));

create table BIGLIETTI (
     codice_biglietto int auto_increment not null,
     data_acquisto date not null,
     durata int not null,
     username varchar(30),
     constraint ID_BIGLIETTO primary key (codice_biglietto));

create table CAUSALI_MULTE (
     codice_causale int auto_increment not null,
     reato varchar(255) not null,
     prezzo_base decimal(8,2) not null,
     prezzo_massimo decimal(8,2) not null,
     check (prezzo_base > 0 and prezzo_massimo > prezzo_base),
     constraint SID_CAUSALE_MULTA unique (reato),
     constraint ID_CAUSALE_MULTA primary key (codice_causale));

create table CONTENUTI (
     codice_hub int not null,
     codice_contenuto int not null,
     posti_max int not null,
     posti_disponibili int not null,
     check (posti_max > 0 and posti_disponibili <= posti_max and posti_disponibili >= 0),
     constraint ID_CONTENUTO primary key (codice_hub, codice_contenuto));

create table CONTENUTI_HUB (
     codice_contenuto int auto_increment not null,
     descrizione varchar(50) not null,
     constraint ID_CONTENUTO_HUB primary key (codice_contenuto));

create table CONTROLLI (
     username varchar(30) not null,
     codice_corsa bigint not null,
     constraint ID_CONTROLLO primary key (username, codice_corsa));

create table CONVALIDE (
     codice_biglietto int not null,
     data_ora datetime not null,
     codice_corsa bigint not null,
     constraint FKcon_BIG_ID primary key (codice_biglietto));

create table DIPENDENTI (
     username varchar(30) not null,
     ruolo enum("amministrativo", "autista", "controllore") not null,
     constraint FKdipendenza_ID primary key (username));

create table FERMATE (
     codice_fermata int auto_increment not null,
     nome varchar(150) not null,
     indirizzo_via varchar(50) not null,
     indirizzo_comune varchar(50) not null,
     indirizzo_civico varchar(10) not null,
     indirizzo_cap int not null,
     longitudine varchar(15) not null,
     latitudine varchar(15) not null,
     constraint ID_FERMATA primary key (codice_fermata),
     constraint SID_FERMATA unique (longitudine, latitudine));

create table HUB_MOBILITA (
     codice_hub int auto_increment not null,
     longitudine varchar(15) not null,
     latitudine varchar(15) not null,
     nome varchar(100) not null,
     indirizzo_via varchar(50) not null,
     indirizzo_comune varchar(50) not null,
     indirizzo_cap int not null,
     indirizzo_civico varchar(10) not null,
     codice_fermata int,
     constraint ID_HUB_MOBILITA_ID primary key (codice_hub),
     constraint SID_HUB_MOBILITA unique (longitudine, latitudine));

create table LINEE (
     codice_linea varchar(30) not null,
     tempo_percorrenza int not null,
     inizio_validita date,
     fine_validita date,
     attiva boolean,
     codice_tipo_mezzo int not null,
     check (inizio_validita <= fine_validita),
     constraint ID_LINEA_ID primary key (codice_linea));

create table MANUTENZIONI_LINEE (
     codice_linea varchar(30) not null,
     data_inizio date not null,
     data_fine date not null,
     nome varchar(50) not null,
     descrizione varchar(255) not null,
     p_iva char(11),
     check (data_inizio <= data_fine),
     constraint ID_MANUTENZIONE_LINEA primary key (data_inizio, codice_linea));

create table MANUTENZIONI_MEZZI (
     n_immatricolazione varchar(20) not null,
     data_inizio date not null,
     data_fine date not null,
     nome varchar(50) not null,
     descrizione varchar(255) not null,
     p_iva char(11),
     check (data_inizio <= data_fine),
     constraint ID_MANUTENZIONE_MEZZO primary key (data_inizio, n_immatricolazione));

create table MEZZI (
     n_immatricolazione varchar(20) not null,
     codice_tipo_mezzo int not null,
     constraint ID_MEZZO primary key (n_immatricolazione));

create table MULTE (
     codice_multa int auto_increment not null,
     data_ora_emissione datetime not null,
     importo decimal(8,2) not null,
     data_pagamento date,
     codice_causale int not null,
     codice_corsa bigint not null,
     documento varchar(20) not null,
     username varchar(30) not null,
     check (importo > 0),
     constraint ID_MULTA primary key (codice_multa));

create table ORARI_LINEE (
     codice_orario bigint auto_increment not null,
     ora_partenza varchar(5) not null,
     giorno_settimanale varchar(10) not null,
     codice_linea varchar(30) not null,
     constraint SID_ORARIO_LINEA unique (codice_linea, ora_partenza, giorno_settimanale),
     constraint ID_ORARIO_LINEA primary key (codice_orario));

create table PERSONE (
     cognome varchar(50) not null,
     nome varchar(50) not null,
     documento varchar(20) not null,
     codice_fiscale char(16),
     constraint ID_PERSONA primary key (documento));

create table SOSTITUZIONI (
     sost_manut_data_inizio date not null,
     sost_manut_codice_linea varchar(30) not null,
     codice_linea varchar(30) not null,
     constraint FKsostituto_ID primary key (sost_manut_data_inizio, sost_manut_codice_linea, codice_linea));

create table TARIFFE_ABBONAMENTI (
     nome varchar(30) not null,
     num_giorni int not null,
     prezzo decimal(6,2) not null,
     check (prezzo > 0 and num_giorni > 0),
     constraint ID_TARIFFA_ABBONAMENTO primary key (num_giorni));

create table TARIFFE_BIGLIETTI (
     nome varchar(30) not null,
     durata int not null,
     prezzo decimal(6,2) not null,
     check (durata > 0 and prezzo > 0),
     constraint ID_TARIFFA_BIGLIETTO primary key (durata));

create table TIPOLOGIE_MEZZI (
     codice_tipo_mezzo int auto_increment not null,
     nome varchar(40) not null,
     constraint ID_TIPOLOGIA_MEZZO primary key (codice_tipo_mezzo));

create table TRAGITTI (
     partenza_codice_fermata int not null,
     arrivo_codice_fermata int not null,
     codice_linea varchar(30) not null,
     ordine int not null,
     check (ordine > 0),
     constraint ID_TRAGITTO_ID primary key (codice_linea, partenza_codice_fermata, arrivo_codice_fermata));

create table TRATTE (
     arrivo_codice_fermata int not null,
     partenza_codice_fermata int not null,
     tempo_percorrenza int not null,
     check (tempo_percorrenza >= 0),
     constraint ID_TRATTA primary key (partenza_codice_fermata, arrivo_codice_fermata));

create table UTENTI (
     username varchar(30) not null,
     documento varchar(20) not null,
     email varchar(50) not null,
     telefono varchar(14) not null,
     password varchar(255) not null,
     constraint ID_UTENTE primary key (username),
     constraint FKutenza_ID unique (documento));


-- Constraints Section
-- ___________________

alter table ABBONAMENTI add constraint FKfascia_abbonamento
     foreign key (num_giorni)
     references TARIFFE_ABBONAMENTI (num_giorni);

alter table ABBONAMENTI add constraint FKacquisto
     foreign key (username)
     references UTENTI (username);

alter table ATTUAZIONI_CORSE add constraint FKimpiego
     foreign key (n_immatricolazione)
     references MEZZI (n_immatricolazione);

alter table ATTUAZIONI_CORSE add constraint FKeffettuazione
     foreign key (codice_orario)
     references ORARI_LINEE (codice_orario);

alter table ATTUAZIONI_CORSE add constraint FKconducente
     foreign key (username)
     references DIPENDENTI (username);

alter table BIGLIETTI add constraint FKfascia_biglietto
     foreign key (durata)
     references TARIFFE_BIGLIETTI (durata);

alter table BIGLIETTI add constraint FKacquisto_
     foreign key (username)
     references UTENTI (username);

alter table CONTENUTI add constraint FKsta
     foreign key (codice_contenuto)
     references CONTENUTI_HUB (codice_contenuto);

alter table CONTENUTI add constraint FKcontiene
     foreign key (codice_hub)
     references HUB_MOBILITA (codice_hub);

alter table CONTROLLI add constraint FKriceve
     foreign key (codice_corsa)
     references ATTUAZIONI_CORSE (codice_corsa);

alter table CONTROLLI add constraint FKeffettua
     foreign key (username)
     references DIPENDENTI (username);

alter table CONVALIDE add constraint FKcon_BIG_FK
     foreign key (codice_biglietto)
     references BIGLIETTI (codice_biglietto);

alter table CONVALIDE add constraint FKcon_ATT
     foreign key (codice_corsa)
     references ATTUAZIONI_CORSE (codice_corsa);

alter table DIPENDENTI add constraint FKdipendenza_FK
     foreign key (username)
     references UTENTI (username);

-- Not implemented
-- alter table HUB_MOBILITA add constraint ID_HUB_MOBILITA_CHK
--     check(exists(select * from CONTENUTI
--                  where CONTENUTI.codice_hub = codice_hub));

alter table HUB_MOBILITA add constraint FKpresenza
     foreign key (codice_fermata)
     references FERMATE (codice_fermata);

-- Not implemented
-- alter table LINEE add constraint ID_LINEA_CHK
--     check(exists(select * from TRAGITTI
--                  where TRAGITTI.codice_linea = codice_linea));

alter table LINEE add constraint FKtipo_linea
     foreign key (codice_tipo_mezzo)
     references TIPOLOGIE_MEZZI (codice_tipo_mezzo);

alter table MANUTENZIONI_LINEE add constraint FKmodifica
     foreign key (codice_linea)
     references LINEE (codice_linea);

alter table MANUTENZIONI_LINEE add constraint FKincarico_
     foreign key (p_iva)
     references AZIENDE (p_iva);

alter table MANUTENZIONI_MEZZI add constraint FKnecessita
     foreign key (n_immatricolazione)
     references MEZZI (n_immatricolazione);

alter table MANUTENZIONI_MEZZI add constraint FKincarico
     foreign key (p_iva)
     references AZIENDE (p_iva);

alter table MEZZI add constraint FKassunzione
     foreign key (codice_tipo_mezzo)
     references TIPOLOGIE_MEZZI (codice_tipo_mezzo);

alter table MULTE add constraint FKtipologia
     foreign key (codice_causale)
     references CAUSALI_MULTE (codice_causale);

alter table MULTE add constraint FKriferimento
     foreign key (codice_corsa)
     references ATTUAZIONI_CORSE (codice_corsa);

alter table MULTE add constraint FKintestazione
     foreign key (documento)
     references PERSONE (documento);

alter table MULTE add constraint FKemissione
     foreign key (username)
     references DIPENDENTI (username);

alter table ORARI_LINEE add constraint FKspecificazione
     foreign key (codice_linea)
     references LINEE (codice_linea);

alter table SOSTITUZIONI add constraint FKsostituisce
     foreign key (codice_linea)
     references LINEE (codice_linea);

alter table SOSTITUZIONI add constraint FKsostituto_FK
     foreign key (sost_manut_data_inizio, sost_manut_codice_linea)
     references MANUTENZIONI_LINEE (data_inizio, codice_linea);

alter table TRAGITTI add constraint FKcomprende
     foreign key (codice_linea)
     references LINEE (codice_linea);

alter table TRAGITTI add constraint FKcompresa
     foreign key (partenza_codice_fermata, arrivo_codice_fermata)
     references TRATTE (partenza_codice_fermata, arrivo_codice_fermata);

alter table TRATTE add constraint FKpartenza
     foreign key (partenza_codice_fermata)
     references FERMATE (codice_fermata);

alter table TRATTE add constraint FKarrivo
     foreign key (arrivo_codice_fermata)
     references FERMATE (codice_fermata);

alter table UTENTI add constraint FKutenza_FK
     foreign key (documento)
     references PERSONE (documento);


-- Index Section
-- _____________

create unique index ID_TRAGITTO_IND
     on TRAGITTI (codice_linea, partenza_codice_fermata, arrivo_codice_fermata);


-- View Section
-- _____________

CREATE VIEW VW_LINEE_ATTIVE_OGGI AS
SELECT L.*, M.nome AS tipo_mezzo, F1.codice_fermata AS part_codice_fermata, F1.nome AS part_nome, F1.indirizzo_via AS part_via, F1.indirizzo_civico AS part_civico, F1.indirizzo_comune AS part_comune, F1.indirizzo_cap AS part_cap, F1.longitudine AS part_long, F1.latitudine AS part_lat, F2.codice_fermata AS arr_codice_fermata, F2.nome AS arr_nome, F2.indirizzo_via AS arr_via, F2.indirizzo_civico AS arr_civico, F2.indirizzo_comune AS arr_comune, F2.indirizzo_cap AS arr_cap, F2.longitudine AS arr_long, F2.latitudine AS arr_lat
FROM LINEE L
JOIN TIPOLOGIE_MEZZI M ON L.codice_tipo_mezzo = M.codice_tipo_mezzo
JOIN TRAGITTI TRA1 on TRA1.codice_linea = L.codice_linea
JOIN TRATTE TR1 ON TRA1.partenza_codice_fermata = TR1.partenza_codice_fermata AND TRA1.arrivo_codice_fermata = TR1.arrivo_codice_fermata
JOIN FERMATE F1 ON F1.codice_fermata = TR1.partenza_codice_fermata
JOIN TRAGITTI TRA2 on TRA2.codice_linea = L.codice_linea
JOIN TRATTE TR2 ON TRA2.partenza_codice_fermata = TR2.partenza_codice_fermata AND TRA2.arrivo_codice_fermata = TR2.arrivo_codice_fermata
JOIN FERMATE F2 ON F2.codice_fermata = TR2.arrivo_codice_fermata
WHERE TRA1.ordine = 1 AND TRA2.ordine =	(SELECT MAX(T1.ordine)
										FROM LINEE L1 JOIN TRAGITTI T1 ON L1.codice_linea = T1.codice_linea
										WHERE L1.codice_linea = L.codice_linea)
	AND (L.attiva IS TRUE OR (CURDATE() BETWEEN L.inizio_validita AND L.fine_validita))
ORDER BY L.codice_linea;


-- Stored Procedure Section
-- _____________

DELIMITER //

CREATE PROCEDURE aggiorna_attivazione_linea(IN cod_linea VARCHAR(30))
BEGIN
	-- Aggiornamento dello stato della linea
	UPDATE linee L
	SET attiva = NOT EXISTS (SELECT 1
							FROM manutenzioni_linee
							WHERE codice_linea = cod_linea AND CURDATE() BETWEEN data_inizio AND data_fine)
	WHERE codice_linea = cod_linea;

END //

CREATE PROCEDURE aggiorna_attivazione_linee()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur_cod_linea VARCHAR(30);

    DECLARE cur CURSOR FOR
        SELECT codice_linea FROM linee WHERE attiva IS NOT NULL;

    -- Gestione della fine del cursore
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Apertura del cursore
    OPEN cur;

    -- Ciclo sui risultati
    read_loop: LOOP
        FETCH cur INTO cur_cod_linea;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Aggiornamento dello stato della linea in esame
        CALL aggiorna_attivazione_linea(cur_cod_linea);

    END LOOP;

    CLOSE cur;
END //

DELIMITER ;


-- Trigger Section
-- _____________

DELIMITER //

CREATE TRIGGER dopo_insert_manut_linea
AFTER INSERT ON manutenzioni_linee
FOR EACH ROW
BEGIN
    CALL aggiorna_attivazione_linea(NEW.codice_linea);
END//

CREATE TRIGGER dopo_update_manut_linea
AFTER UPDATE ON manutenzioni_linee
FOR EACH ROW
BEGIN
    CALL aggiorna_attivazione_linea(NEW.codice_linea);
END//

CREATE TRIGGER dopo_delete_manut_linea
AFTER DELETE ON manutenzioni_linee
FOR EACH ROW
BEGIN
    CALL aggiorna_attivazione_linea(OLD.codice_linea);
END//

DELIMITER ;


-- Event Section
-- _____________

CREATE EVENT aggiorna_linee_event
ON SCHEDULE EVERY 1 DAY
DO
  CALL aggiorna_attivazione_linee();
