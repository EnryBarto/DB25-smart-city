package it.unibo.smartcity.data;

public final class Queries {


    /* +-----------------------------------------------------------------------------------------------------------+
       |                                        INIZIO OPERAZIONI RELAZIONE                                        |
       +-----------------------------------------------------------------------------------------------------------+
    */


    // OPERAZIONE 1
    public static final String LIST_LINEE_ATTIVE =
    """
        SELECT *
        FROM VW_LINEE_ATTIVE_OGGI
    ;""";

    // OPERAZIONE 2
    public static final String LIST_TRATTE_PER_LINEA =
    """
        SELECT t.arrivo_codice_fermata, t.partenza_codice_fermata, t.ordine, tr.tempo_percorrenza, f_par.*, f_arr.*
        FROM TRAGITTI t
        JOIN TRATTE tr ON (tr.arrivo_codice_fermata = t.arrivo_codice_fermata AND tr.partenza_codice_fermata = t.partenza_codice_fermata)
        JOIN FERMATE f_par ON f_par.codice_fermata = tr.partenza_codice_fermata
        JOIN FERMATE f_arr ON f_arr.codice_fermata = tr.arrivo_codice_fermata
		WHERE t.codice_linea = ?
        ORDER BY t.ordine ASC
        ;
    """;

    // OPERAZIONE 2 BIS
    public static final String LIST_ORARI_UNA_LINEA =
    """
        SELECT *
        FROM ORARI_LINEE
        WHERE codice_linea = ?
    """;

    // OPERAZIONE 3
    public static final String LIST_HUB_MOBILITA =
    """
        SELECT h.codice_hub, h.nome nome_hub, h.indirizzo_via, h.indirizzo_civico, h.indirizzo_comune, h.indirizzo_cap, h.longitudine, h.latitudine, h.codice_fermata, f.nome nome_fermata, ch.descrizione tipo_contenuto, c.posti_disponibili
        FROM HUB_MOBILITA h LEFT JOIN FERMATE f on (h.codice_fermata = f.codice_fermata)
        JOIN CONTENUTI c ON (c.codice_hub = h.codice_hub)
        JOIN CONTENUTI_HUB ch ON (c.codice_contenuto = ch.codice_contenuto)
        ORDER BY h.codice_hub;
    """;

    // OPERAZIONE 4
    public static final String LIST_ORARIO_AUTISTA_LINEE_ASSEGN =
    """
        SELECT ol.codice_linea, ol.ora_partenza, ol.giorno_settimanale, ol.codice_orario, ac.data, l.tempo_percorrenza, ac.n_immatricolazione
        FROM ORARI_LINEE ol
        JOIN ATTUAZIONI_CORSE ac ON ol.codice_orario = ac.codice_orario
        JOIN LINEE l ON ol.codice_linea = l.codice_linea
        WHERE ac.username = ?
        ORDER BY ac.data, ol.ora_partenza;
    """;

    // OPERAZIONE 5
    public static final String LIST_ORARIO_CONTROLLORE_LINEE_ASSEGN =
    """
        SELECT A.data, O.codice_orario, O.ora_partenza, O.giorno_settimanale, O.codice_linea, L.tempo_percorrenza, A.n_immatricolazione
        FROM CONTROLLI C
        JOIN ATTUAZIONI_CORSE A ON C.codice_corsa = A.codice_corsa
        JOIN ORARI_LINEE O ON A.codice_orario = O.codice_orario
        JOIN LINEE L ON O.codice_linea = L.codice_linea
        WHERE C.username = ?
        ORDER BY A.data, O.ora_partenza;
    """;

    // OPERAZIONE 6
    public static final String ESTRAZ_LINEE_PIU_CONVALIDE =
    """
        SELECT COUNT(*) AS numero_convalide
        FROM LINEE l
        JOIN ORARI_LINEE ol ON l.codice_linea = ol.codice_linea
        JOIN ATTUAZIONI_CORSE ac ON ol.codice_orario = ac.codice_orario
        JOIN CONVALIDE c ON ac.codice_corsa = c.codice_corsa
        GROUP BY codice_linea
        ORDER BY COUNT(*) DESC
        );
    """;

    // OPERAZIONE 7
    public static final String LIST_MANUT_PER_MEZZO =
    """
        SELECT *
        FROM MANUTENZIONI_MEZZI mm
        WHERE mm.n_immatricolazione = ?
    """;

    // OPERAZIONE 8
    public static final String VARIAZIONE_SERVIZIO_LINEA =
    """
        SELECT l.codice_linea codice_linea_in_manutenzione, m.data_inizio, m.data_fine, m.nome, m.descrizione, a.p_iva, a.email, a.telefono, a.ragione_sociale, ls.codice_linea codice_linea_sostituta
        FROM LINEE l JOIN MANUTENZIONI_LINEE m ON (m.codice_linea = l.codice_linea)
        LEFT JOIN AZIENDE a ON (m.p_iva = a.p_iva)
        LEFT JOIN SOSTITUZIONI s ON (m.codice_linea = s.sost_manut_codice_linea AND m.data_inizio = s.sost_manut_data_inizio)
        LEFT JOIN LINEE ls ON (ls.codice_linea = s.codice_linea)
        WHERE l.codice_linea = ?;
    """;

    // OPERAZIONE 9
    public static final String LIST_INCASSI =
    """
        SELECT sum(tb.prezzo) AS incassi
        FROM ORARI_LINEE ol
        JOIN ATTUAZIONI_CORSE ac ON ol.codice_linea = ac.codice_linea
        JOIN CONVALIDE c ON ac.codice_corsa = c.codice_corsa
        JOIN BIGLIETTI b ON c.codice_biglietto = b.codice_biglietto
        JOIN TARIFFE_BIGLIETTI tb ON b.durata = tb.durata
        WHERE ol.codice_linea = ?
    """;

    // OPERAZIONE 10
    public static final String INCASSI_TIPO_TITOLO_RANGE_DATA =
    """
        SELECT SUM(prezzo) as incasso
        FROM CONVALIDE C
        JOIN BIGLIETTI B ON C.codice_biglietto = B.codice_biglietto
        JOIN TARIFFE_BIGLIETTI T ON B.durata = T.durata
        WHERE B.durata = ? AND (DATE(C.data_ora) BETWEEN ? AND ?);
    """;

    // OPERAZIONE 11
    public static final String LINEE_PIU_MULTE =
    """
        SELECT l.codice_linea, COUNT(*) numero_multe
        FROM LINEE l, ORARI_LINEE ol, ATTUAZIONI_CORSE ac, MULTE m
        WHERE l.codice_linea = ol.codice_linea
        AND ol.codice_orario = ac.codice_orario
        AND ac.codice_corsa = m.codice_corsa
        AND ac.data BETWEEN ? AND ?
        GROUP BY l.codice_linea
        ORDER BY numero_multe DESC;
    """;

    // OPERAZIONE 12
    public static final String CINQUE_MANUT_PIU_GRAVOSE =
    //il controllo dei punteggi si farà a livello applicativo
    """
        SELECT ml.codice_linea, ml.nome, DATEDIFF(ml.data_fine, ml.data_inizio) AS durata_lavoro,
            COUNT(*) AS num_linee_sostitutive
        FROM MANUTENZIONI_LINEE ml
        JOIN SOSTITUZIONI s ON ml.codice_linea = s.sost_manut_codice_linea
        GROUP BY ml.codice_linea, ml.data_inizio, ml.nome
    """;

    // OPERAZIONE 13
    public static final String LINEE_CINQUE_CONTROL_DIECI_MULT =
    """
    SELECT l.codice_linea
    FROM LINEE l JOIN ORARI_LINEE ol ON (l.codice_linea = ol.codice_linea)
    JOIN ATTUAZIONI_CORSE ac ON (ol.codice_orario = ac.codice_orario)
    GROUP BY l.codice_linea
    HAVING COUNT(DISTINCT ac.codice_corsa) = (SELECT COUNT(DISTINCT ac1.codice_corsa)    # conto attuazioni corsa
                FROM LINEE l1 JOIN ORARI_LINEE ol1 ON (l1.codice_linea = ol1.codice_linea)
                JOIN ATTUAZIONI_CORSE ac1 ON (ol1.codice_orario = ac1.codice_orario)
                JOIN controlli c ON (c.codice_corsa = ac1.codice_corsa)
                JOIN multe m ON (m.codice_corsa = ac1.codice_corsa)
                WHERE l1.codice_linea = l.codice_linea
                GROUP BY ac1.data
                HAVING COUNT(DISTINCT m.codice_multa) <= 10 AND COUNT(DISTINCT c.username) > 5);
            """;

    // OPERAZIONE 14
    public static final String LINEA_MAGGIOR_TEMP_PERCORR =
    // ho scelto di utilizzare un ORDER BY seguita da una LIMIT
    // per motivi di efficienza.
    """
        SELECT codice_linea, tempo_percorrenza
        FROM LINEE
        ORDER BY tempo_percorrenza DESC
        LIMIT 1
    """;

    // OPERAZIONE 15
    public static final String LINEA_PIU_HUB_MOBILITA =
    """
        SELECT COUNT(DISTINCT H.codice_hub) AS num_hub, TRA.codice_linea
        FROM HUB_MOBILITA H
        JOIN FERMATE F ON H.codice_fermata = F.codice_fermata
        JOIN TRAGITTI TRA ON (TRA.arrivo_codice_fermata = F.codice_fermata) OR (TRA.partenza_codice_fermata = F.codice_fermata)
        WHERE F.codice_fermata IN (SELECT partenza_codice_fermata
                                    FROM TRAGITTI
                                    WHERE codice_linea = TRA.codice_linea)
            OR F.codice_fermata IN (SELECT arrivo_codice_fermata
                                    FROM TRAGITTI
                                    WHERE codice_linea = TRA.codice_linea)
        GROUP BY TRA.codice_linea
        ORDER BY num_hub DESC
        LIMIT 1;
    """;

    // OPERAZIONE 16
    public static final String MEDIA_SOLDI_MULTE =
    """
        SELECT AVG(COALESCE(m.importo, 0)) AS media_soldi
        FROM PERSONE p
        LEFT JOIN MULTE m ON p.documento = m.documento
    """;

    // OPERAZIONE 17
    public static final String AZIENDE_NO_MANUT_ULTIMO_MESE =
    //ho scelto di utilizzare il NOT EXIST in quanto ignora i valori
    //a NULL, che possono essere presenti nelle colonne p_iva
    //delle query innestate. SELECT 1 serve per evitare che il DB
    //ottimizzi al meglio evitando di andare a prendere tutte le colonne
    //delle manutenzioni (che a noi non servono).
    """
        SELECT a.*
        FROM AZIENDE a
        WHERE NOT EXISTS (
            SELECT 1
            FROM MANUTENZIONI_MEZZI mm
            WHERE mm.p_iva = a.p_iva
            AND mm.data_inizio >= CURRENT_DATE - INTERVAL 1 MONTH
            )
        AND NOT EXISTS (
            SELECT 1
            FROM MANUTENZIONI_LINEE ml
            WHERE ml.p_iva = a.p_iva
            AND ml.data_inizio >= CURRENT_DATE - INTERVAL 1 MONTH
            );
    """;

    // OPERAZIONE 18
    public static final String LIST_FERMATE_HUB_TUTTI_CONTENUTI =
    """
        SELECT DISTINCT *
        FROM FERMATE F
        JOIN HUB_MOBILITA H ON F.codice_fermata = H.codice_fermata
        WHERE H.codice_hub IN (SELECT H.codice_hub
                                FROM HUB_MOBILITA H
                                JOIN CONTENUTI C ON H.codice_hub = C.codice_hub
                                GROUP BY H.codice_hub
                                HAVING COUNT(DISTINCT C.codice_contenuto) = (SELECT COUNT(DISTINCT codice_contenuto) FROM CONTENUTI_HUB)
                                );
    """;

    // OPERAZIONE 19 - Da trattare come transaction
    public static final String AGGIUNGI_VARIAZIONE =
    """
        INSERT INTO SOSTITUZIONI (sost_manut_data_inizio, sost_manut_codice_linea, codice_linea)
        VALUES (?, ?, ?);
    """;

    // OPERAZIONE 20 / 1
    public static final String AGGIUNGI_TRAGITTO =
    """
        INSERT INTO TRAGITTI (partenza_codice_fermata, arrivo_codice_fermata, codice_linea, ordine)
        VALUES (?, ?, ?, ?);
    """;

    // Operazione 20 / 2
    public static final String SELECT_TEMPO_PERCORRENZA =
    """
        SELECT tempo_percorrenza
        FROM LINEE
        WHERE codice_linea = ?;
    """;

    // OPERAZIONE 20 / 3
    public static final String UPDATE_TEMPO_PERCORRENZA =
    """
        UPDATE LINEE
        SET tempo_percorrenza = ?
        WHERE codice_linea = ?;
    """;

    // OPERAZIONE 21
    public static final String AGGIUNGI_LINEA =
    """
        INSERT INTO LINEE (codice_linea, inizio_validita, fine_validita, attiva, codice_tipo_mezzo)
        VALUES(?, ?, ?, ?, ?);
    """;


    /* +-----------------------------------------------------------------------------------------------------------+
       |                                         FINE OPERAZIONI RELAZIONE                                         |
       +-----------------------------------------------------------------------------------------------------------+
    */


    /*
     * ALTRE QUERY
     */

    public static final String LIST_ABBONAMENTI =
        """
        SELECT *
        FROM ABBONAMENTI;
        """;

    public static final String LIST_FERMATE_UNA_LINEA =
    """
        SELECT t.codice_linea, t.arrivo_codice_fermata, t.partenza_codice_fermata,
            t.ordine, f.nome, f.indirizzo_via, f.indirizzo_civico, f.indirizzo_comune, f.indirizzo_cap, tr.tempo_percorrenza
        FROM TRAGITTI t
        JOIN TRATTE tr ON t.partenza_codice_fermata = tr.partenza_codice_fermata AND t.arrivo_codice_fermata = tr.arrivo_codice_fermata
        JOIN FERMATE f ON t.arrivo_codice_fermata = f.codice_fermata
        WHERE t.codice_linea = ?
    """;


    public static final String LIST_ATTUAZIONI_CORSE =
    """
        SELECT *
        FROM ATTUAZIONI_CORSE A
        JOIN ORARI_LINEE O ON A.codice_orario = O.codice_orario
        JOIN LINEE L ON L.codice_linea = O.codice_linea
        ORDER BY A.data, L.codice_linea;
    """;

    public static final String SELECT_UTENTE =
        """
        SELECT *
        FROM UTENTI u
        JOIN PERSONE p on u.documento = p.documento
        WHERE u.username = ?;
        """;
    public static final String INSERT_PERSONA =
    """
        INSERT INTO PERSONE (cognome, nome, documento, codice_fiscale)
        VALUES (?, ?, ?, ?);
    """;

    public static final String LIST_TARIFFE_BIGLIETTI =
    """
        SELECT *
        FROM TARIFFE_BIGLIETTI
    """;

    public static final String INSERT_ABBONAMENTO =
    """
        INSERT INTO ABBONAMENTI (num_giorni, data_inizio, data_acquisto, username)
        VALUES (?, ?, ?, ?);
    """;
    public static final String INSERT_BIGLIETTO =
    """
        INSERT INTO BIGLIETTI (data_acquisto, durata, username)
        VALUES (?, ?, ?)
    """;

    public static final String ESTRAZ_BIGLIETTI_BYUSER_NON_CONVAL =
    """
        SELECT *
        FROM BIGLIETTI B
        LEFT JOIN CONVALIDE C ON B.codice_biglietto = C.codice_biglietto
        WHERE username = ? AND C.codice_corsa IS NULL
    """;

    public static final String INSERT_CONVALIDA =
    """
        INSERT INTO CONVALIDE (codice_biglietto, data_ora, codice_corsa)
        VALUES (?, ?, ?)
    """;

    public static final String INSERT_USER =
    """
        INSERT INTO UTENTI (username, documento, email, telefono, password)
        VALUES (?, ?, ?, ?, ?);
    """;

    public static final String INSERT_MANUT_MEZZI =
    """
        INSERT INTO MANUTENZIONI_MEZZI (n_immatricolazione, data_inizio, data_fine, nome, descrizione, p_iva)
        VALUES (?, ?, ?, ?, ?, ?);
    """;

    public static final String REMOVE_MANUT_MEZZI =
    """
        DELETE FROM MANUTENZIONI_MEZZI
        WHERE n_immatricolazione = ?
        AND data_inizio = ?
    """;

    public static final String LIST_MEZZI =
        """
        SELECT m.n_immatricolazione, tm.nome
        FROM MEZZI m
        JOIN TIPOLOGIE_MEZZI tm ON m.codice_tipo_mezzo = tm.codice_tipo_mezzo
        """;
    public static final String LIST_MANUTENZIONI_LINEE =
    """
        SELECT *
        FROM MANUTENZIONI_LINEE;
    """;

    public static final String INSERT_MANUTENZIONI_LINEE =
    """
        INSERT INTO MANUTENZIONI_LINEE (codice_linea, data_inizio, data_fine, nome, descrizione, p_iva)
        VALUES (?, ?, ?, ?, ?, ?);
    """;

    public static final String REMOVE_MANUT_LINEE =
    """
        DELETE FROM MANUTENZIONI_LINEE
        WHERE codice_linea = ?
        AND data_inizio = ?
    """;

    public static final String LIST_MANUTENZIONI_MEZZI =
        """
        SELECT *
        FROM MANUTENZIONI_MEZZI;
        """;

    public static final String LIST_TARIFFE_ABBONAMENTI =
    """
        SELECT *
        FROM TARIFFE_ABBONAMENTI;
    """;

    public static final String SELECT_PERSONA =
    """
        SELECT *
        FROM PERSONE P
        WHERE P.documento = ?;
    """;

    public static final String LIST_DIPENDENTI =
    """
        SELECT *
        FROM DIPENDENTI D
        JOIN UTENTI U ON D.username = U.username
        JOIN PERSONE P ON P.documento = U.documento
        ORDER BY P.documento
    """;

    public static final String LIST_UTENTI_NON_DIPENDENTI =
    """
        SELECT *
        FROM PERSONE P
        JOIN UTENTI U ON P.documento = U.documento
        LEFT JOIN DIPENDENTI D ON D.username = U.username
        WHERE D.ruolo IS NULL
        ORDER BY P.documento
    """;

    public static final String INSERT_DIPENDENTE =
    """
        INSERT INTO DIPENDENTI (username, ruolo)
        VALUES (?, ?);
    """;

    public static final String REMOVE_DIPENDENTE =
    """
        DELETE FROM DIPENDENTI
        WHERE username = ?
    """;

    public static final String LIST_ULTIMI_TRAGITTI =
     """
        SELECT *
        FROM LINEE L
        JOIN TRAGITTI TRA on TRA.codice_linea = L.codice_linea
        WHERE TRA.ordine =	(SELECT MAX(T1.ordine)
							FROM LINEE L1 JOIN TRAGITTI T1 ON L1.codice_linea = T1.codice_linea
							WHERE L1.codice_linea = L.codice_linea)
        ORDER BY L.codice_linea
    """;

    public static final String SELECT_ULTIMO_TRAGITTO =
     """
        SELECT *
        FROM LINEE L
        JOIN TRAGITTI TRA on TRA.codice_linea = L.codice_linea
        WHERE L.codice_linea = ?
        AND TRA.ordine =	(SELECT MAX(T1.ordine)
							FROM LINEE L1 JOIN TRAGITTI T1 ON L1.codice_linea = T1.codice_linea
							WHERE L1.codice_linea = L.codice_linea)
    """;

    public static final String LIST_TRATTE_POSSIBILI_LINEA =
     """
        SELECT *
        FROM TRATTE
        WHERE partenza_codice_fermata =	(SELECT T.arrivo_codice_fermata
										FROM LINEE L JOIN TRAGITTI T ON L.codice_linea = T.codice_linea
										WHERE L.codice_linea = ?
                                        ORDER BY T.ordine DESC
                                        LIMIT 1)
            OR (SELECT codice_linea
                FROM TRAGITTI WHERE codice_linea = ? LIMIT 1) IS NULL
    ; """;

    public static final String LIST_LINEE_ATTIVE_BY_DATE =
     """
        SELECT *
        FROM LINEE L
        WHERE (L.attiva IS TRUE OR (? BETWEEN L.inizio_validita AND L.fine_validita))
        ORDER BY L.codice_linea
    ;""";

    public static final String LIST_MEZZI_ATTIVI_PER_LINEA =
    """
        SELECT M.*
        FROM  MEZZI M
        LEFT JOIN MANUTENZIONI_MEZZI MM ON M.n_immatricolazione = MM.n_immatricolazione
        WHERE M.codice_tipo_mezzo = (SELECT codice_tipo_mezzo FROM LINEE WHERE codice_linea = ?)
            AND NOT EXISTS (SELECT *
                            FROM MANUTENZIONI_MEZZI MM1
                            WHERE MM1.n_immatricolazione = M.n_immatricolazione
                            AND ? BETWEEN MM1.data_inizio AND MM1.data_fine)
    """;

    public static final String LIST_AUTISTI =
    """
        SELECT *
        FROM DIPENDENTI D
        JOIN UTENTI U ON D.username = U.username
        JOIN PERSONE P ON P.documento = U.documento
        WHERE D.ruolo = "Autista"
        ORDER BY P.cognome
    """;

    public static final String LIST_ORARI_NON_ATTUATI =
    """
        SELECT *
        FROM ORARI_LINEE O
        WHERE codice_linea = ?
            AND giorno_settimanale LIKE ?
            AND NOT EXISTS (SELECT 1
                            FROM ATTUAZIONI_CORSE
                            WHERE codice_linea = ?
                                AND data = ?
                                AND codice_orario = O.codice_orario)
        ORDER BY ora_partenza
    """;

    public static final String LIST_LINEE_NON_ATTIVE =
    """
        SELECT *
        FROM LINEE
        WHERE attiva IS FALSE;
    """;

    public static final String AGGIORNA_LINEA_ATTIVA =
    """
        UPDATE LINEE
        SET attiva = 0
        WHERE codice_linea = ?
        AND EXISTS (SELECT * FROM manutenzioni_linee
                    WHERE CURDATE() BETWEEN data_inizio AND data_fine
                    AND codice_linea = ?);
    """;

    public static final String UPDATE_TEMPO_PERCORRENZA_COMPLETO =
    """
        UPDATE LINEE
        SET tempo_percorrenza = (SELECT SUM(trt.tempo_percorrenza)
                                FROM TRATTE trt, TRAGITTI trg
                                WHERE trg.partenza_codice_fermata = trt.partenza_codice_fermata
                                AND trg.arrivo_codice_fermata = trt.arrivo_codice_fermata
                                AND trg.codice_linea = ?)
        WHERE codice_linea = ?;
    """;
}
