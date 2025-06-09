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
    public static final String INSERT_USE =
    """
        INSERT INTO UTENTI (nome, cognome, documento, codice_fiscale, telefono, email, username, password)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?);
    """;
    public static final String LIST_MEZZI =
        """
        SELECT *
        FROM MEZZI;
        """;
    public static final String LIST_MANUTENZIONI_LINEE =
        """
        SELECT *
        FROM MANUTENZIONI_LINEE;
        """;
    public static final String LIST_MANUTENZIONI_MEZZI =
        """
        SELECT *
        FROM MANUTENZIONI_MEZZI ;
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
            t.ordine, f.nome, f.via, tr.tempo_percorrenza
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
        SELECT h.codice_hub, h.nome nome_hub, h.indirizzo, h.longitudine, h.latitudine, h.codice_fermata, f.nome nome_fermata, ch.descrizione tipo_contenuto, c.posti_disponibili
        FROM hub_mobilita h LEFT JOIN fermate f on (h.codice_fermata = f.codice_fermata)
        JOIN contenuti c ON (c.codice_hub = h.codice_hub)
        JOIN contenuti_hub ch ON (c.codice_contenuto = ch.codice_contenuto);
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

    // OPERAZIONE 8
    public static final String VARIAZIONE_SERVIZIO_LINEA =
    """
        SELECT l.codice_linea codice_linea_in_manutenzione, m.data_inizio, m.data_fine, m.nome, m.descrizione, a.p_iva, a.email, a.telefono, a.ragione_sociale, ls.codice_linea codice_linea_sostituta
        FROM linee l JOIN manutenzioni_linee m ON (m.codice_linea = l.codice_linea)
        RIGHT JOIN aziende a ON (m.p_iva = a.p_iva)
        JOIN sostituzioni s ON (m.codice_linea = s.sost_manut_codice_linea AND m.data_inizio = s.sost_manut_data_inizio)
        JOIN linee ls ON (ls.codice_linea = s.codice_linea)
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
        FROM linee l, orari_linee ol, attuazioni_corse ac, multe m
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
            FROM linee l JOIN orari_linee ol ON (l.codice_linea = ol.codice_linea)
            JOIN attuazioni_corse ac ON (ol.codice_orario = ac.codice_orario)
            GROUP BY l.codice_linea
            HAVING COUNT(*) = (SELECT COUNT(DISTINCT ac1.codice_corsa)									# conto attuazioni corsa
                                FROM linee l1 JOIN orari_linee ol1 ON (l1.codice_linea = ol1.codice_linea)
                                JOIN attuazioni_corse ac1 ON (ol1.codice_orario = ac1.codice_orario)
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
        FROM multe m, persone p
        WHERE p.documento = m.codice_multa
        GROUP BY p.documento;
    """;

    //OPERAZIONE 17
    public static final String AZIENDE_NO_MANUT_ULTIMO_MESE =
        //ho scelto di utilizzare il NOT IN in quanto ignora i valori
        //a NULL, che possono essere presenti quando selezioniamo le
        //p_iva nelle query innestate.
        """
        SELECT a.p_iva, a.ragione_sociale
        FROM AZIENDE a
        WHERE a.p_iva NOT IN (
            SELECT DISTINCT mm.p_iva
            FROM MANUTENZIONI_MEZZI mm
            WHERE mm.data_inizio >= CURRENT_DATE - INTERVAL 1 MONTH
            )
        AND a.p_iva NOT IN (
            SELECT DISTINCT ml.p_iva
            FROM MANUTENZIONI_LINEE ml
            WHERE ml.data_inizio >= CURRENT_DATE - INTERVAL 1 MONTH
            );
        """;

    // OPERAZIONE 19 - Da trattare come transaction
    public static final String AGGIUNGI_VARIAZIONE =
        """
            INSERT INTO manutenzioni_linee (codice_linea, data_inizio, data_fine, nome, descrizione, p_iva)
            VALUES ('A', '2025-06-06', '2025-06-10', 'Aggiornamento', 'Aggiornamento di qualcosa', NULL);

            INSERT INTO sostituzioni (sost_manut_data_inizio, sost_manut_codice_linea, codice_linea)
            VALUES ('2025-06-06', 'A', 'A-1');

            # to do if data_inizio corresponds with today
            UPDATE linee
            SET attiva = False
            WHERE codice_linea = 'A';
        """;

    // OPERAZIONE 21
    public static final String AGGIUNGI_LINEA =
    """
        INSERT INTO linee (codice_linea, inizio_validita, fine_validita, attiva, codice_tipo_mezzo)
        VALUES('L101', '2025-01-01', '2026-12-31', 1, '1');

        INSERT INTO tragitti (partenza_codice_fermata, arrivo_codice_fermata, codice_linea, ordine)
        VALUES ('F101', 'F202', 'L101', 1); # And so on

        UPDATE linee
        SET tempo_percorrenza = (SELECT *
                                FROM tratte trt, tragitti trg, linee l
                                WHERE l.codice_linea = trg.codice_linea
                                AND trg.partenza_codice_fermata = trt.partenza_codice_fermata
                                AND trg.arrivo_codice_fermata = trt.arrivo_codice_fermata
                                AND l.codice_linea = 'L101')
        WHERE codice_linea = 'L101';
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
}
