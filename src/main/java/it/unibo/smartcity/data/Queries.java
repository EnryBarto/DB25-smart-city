package it.unibo.smartcity.data;

public final class Queries {

    /*
     * QUERY DI SVITOL <3
     */
    public static final String LIST_ABBONAMENTI =
        """
        SELECT *
        FROM ABBONAMENTI;
        """;

    public static final String LIST_ATTUAZIONI_CORSE =
        """
        SELECT *
        FROM ATTUAZIONI_CORSE;
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

    // OPERAZIONE 1
    public static final String LIST_LINEE_ATTIVE =
    """
        SELECT *
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
            AND L.attiva IS TRUE OR (CURDATE() BETWEEN L.inizio_validita AND L.fine_validita)
        ORDER BY L.codice_linea
    ;""";

    // OPERAZIONE 2
    public static final String LIST_FERMATE_UNA_LINEA =
    """
        SELECT t.codice_linea, t.arrivo_codice_fermata, t.partenza_codice_fermata,
            t.ordine, f.nome, f.indirizzo_via, f.indirizzo_civico, f.indirizzo_comune, f.indirizzo_cap, tr.tempo_percorrenza
        FROM TRAGITTI t
        JOIN TRATTE tr ON t.partenza_codice_fermata = tr.partenza_codice_fermata AND t.arrivo_codice_fermata = tr.arrivo_codice_fermata
        JOIN FERMATE f ON t.arrivo_codice_fermata = f.codice_fermata
        WHERE t.codice_linea = ?
    """;
    // OPERAZIONE 2 BIS
    public static final String LIST_ORARI_UNA_LINEA =
    """
        SELECT ol.codice_orario, ol.codice_linea, ol.orario_partenza, ol.giorno_settimanale, ac.data
        FROM ORARI_LINEE ol
        JOIN ATTUAZIONI_CORSE ac ON ol.codice_orario = ac.codice_orario
        WHERE ol.codice_linea = ?
    """;

    // OPERAZIONE 3
    public static final String LIST_HUB_MOBILITA =
    """
        SELECT h.codice_hub, h.nome nome_hub, h.indirizzo_via, h.indirizzo_civico, h.indirizzo_comune, h.indirizzo_cap, h.longitudine, h.latitudine, h.codice_fermata, f.nome nome_fermata, ch.descrizione tipo_contenuto, c.posti_disponibili
        FROM HUB_MOBILITA h LEFT JOIN FERMATE f on (h.codice_fermata = f.codice_fermata)
        JOIN CONTENUTI c ON (c.codice_hub = h.codice_hub)
        JOIN CONTENUTI_HUB ch ON (c.codice_contenuto = ch.codice_contenuto);
    """;
    //OPERAZIONE 4
    public static final String LIST_ORARIO_LINEE_ASSEGN =
    """
        SELECT ol.codice_linea, ol.orario_partenza, ol.giorno_settimanale, ol.codice_orario, ac.data
        FROM ORARIO_LINEE ol
        JOIN ATTUAZIONI_CORSE ac ON ol.codice_orario = ac.codice_orario
        WHERE ac.username = ?
        ORDER BY ol.codice_linea, ol.orario_partenza, ol.giorno_settimanale;
    """;
    //OPERAZIONE 6
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
    //OPERAZIONE 7
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
        RIGHT JOIN AZIENDE a ON (m.p_iva = a.p_iva)
        JOIN SOSTITUZIONI s ON (m.codice_linea = s.sost_manut_codice_linea AND m.data_inizio = s.sost_manut_data_inizio)
        JOIN LINEE ls ON (ls.codice_linea = s.codice_linea)
        WHERE l.codice_linea = ?;
    """;

    //OPERAZIONE 9
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

    //OPERAZIONE 12
    public static final String CINQUE_MANUT_PIU_GRAVOSE =
    //il controllo dei punteggi si farà a livello applicativo
    """
        SELECT ml.codice_linea, ml.nome, DATEDIFF(ml.data_inizio, ml.data_fine) AS durata_lavoro,
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
            HAVING COUNT(*) = (SELECT COUNT(DISTINCT ac1.codice_corsa)									# conto attuazioni corsa
                                FROM LINEE l1 JOIN ORARI_LINEE ol1 ON (l1.codice_linea = ol1.codice_linea)
                                JOIN ATTUAZIONI_CORSE ac1 ON (ol1.codice_orario = ac1.codice_orario)
                                WHERE l1.codice_linea = l.codice_linea
                                AND ac1.codice_corsa IN (SELECT ac2.codice_corsa							# seleziono i giorni in cui ho quelle condizioni
                                            FROM attuazioni_corse ac2
                                            RIGHT JOIN controlli c ON (c.codice_corsa = ac2.codice_corsa)
                                            RIGHT JOIN multe m ON (m.codice_corsa = ac2.codice_corsa)
                                            GROUP BY ac2.data
                                            HAVING COUNT(m.codice_multa) <= 10 AND COUNT(c.codice_corsa) > 5));
            """;

    //OPERAZIONE 14
    public static final String LINEA_MAGGIOR_TEMP_PERCORR =
    // ho scelto di utilizzare un ORDER BY seguita da una LIMIT
    // per motivi di efficienza.
    """
        SELECT codice_linea, tempo_percorrenza
        FROM LINEE
        ORDER BY tempo_percorrenza DESC
        LIMIT 1
    """;

    // OPERAZIONE 16
    public static final String MEDIA_SOLDI_MULTE =
    """
        SELECT AVG(m.importo)
        FROM MULTE m, PERSONE p
        WHERE p.documento = m.codice_multa
        GROUP BY p.documento;
    """;

    //OPERAZIONE 17
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

    // OPERAZIONE 19 - Da trattare come transaction
    public static final String AGGIUNGI_VARIAZIONE =
        """
            INSERT INTO SOSTITUZIONI (sost_manut_data_inizio, sost_manut_codice_linea, codice_linea)
            VALUES (?, ?, ?);

            UPDATE LINEE
            SET attiva = False
            WHERE codice_linea = ?
            AND EXISTS (SELECT * FROM manutenzioni_linee
                        WHERE CURDATE() BETWEEN inizio_validita AND fine_validita
                        AND codice_linea = ?);
        """;

    // OPERAZIONE 19 -TMP
    public static final String AGGIUNGI_VARIAZIONE_TMP =
        """
            INSERT INTO SOSTITUZIONI (sost_manut_data_inizio, sost_manut_codice_linea, codice_linea)
            VALUES (?, ?, ?);
        """;

    // OPERAZIONE 21
    public static final String AGGIUNGI_LINEA =
    """
        INSERT INTO LINEE (codice_linea, inizio_validita, fine_validita, attiva, codice_tipo_mezzo)
        VALUES(?, ?, ?, ?, ?);

        INSERT INTO TRAGITTI (partenza_codice_fermata, arrivo_codice_fermata, codice_linea, ordine)
        VALUES (?, ?, ?, ?); # And so on

        UPDATE LINEE
        SET tempo_percorrenza = (SELECT *
                                FROM TRATTE trt, TRAGITTI trg, LINEE l
                                WHERE l.codice_linea = trg.codice_linea
                                AND trg.partenza_codice_fermata = trt.partenza_codice_fermata
                                AND trg.arrivo_codice_fermata = trt.arrivo_codice_fermata
                                AND l.codice_linea = ?)
        WHERE codice_linea = ?;
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

    /*
     * ALTRE QUERY
     */

     public static final String LIST_ULTIMA_TRATTA_LINEA =
     """
        SELECT *
        FROM LINEE L
        JOIN TRAGITTI TRA on TRA.codice_linea = L.codice_linea
        JOIN TRATTE TR ON TRA.partenza_codice_fermata = TR.partenza_codice_fermata AND TRA.arrivo_codice_fermata = TR.arrivo_codice_fermata
        WHERE TRA.ordine =	(SELECT MAX(T1.ordine)
							FROM LINEE L1 JOIN TRAGITTI T1 ON L1.codice_linea = T1.codice_linea
							WHERE L1.codice_linea = L.codice_linea)
        ORDER BY L.codice_linea
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
                FROM TRAGITTI WHERE codice_linea = ?) IS NULL
    ; """;

}
