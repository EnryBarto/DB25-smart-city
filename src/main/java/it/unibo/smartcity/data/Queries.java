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
    //OPERAZIONE 4
    public static final String LIST_ORARIO_LINEE_ASSEGN =
    """
        SELECT ol.codice_linea, ol.orario_partenza, ol.giorno_settimanale
        FROM ORARIO_LINEE ol
        JOIN ATTUAZIONI_CORSE ac ON ol.codice_orario = ac.codice_orario
        WHERE ac.username = ?
        ORDER BY ol.codice_linea, ol.orario_partenza, ol.giorno_settimanale;
    """;
    //OPERAZIONE 6
    public static final String ESTRAZ_LINEE_PIU_CONVALIDE =
    """
        SELECT codice_linea, COUNT()
        FROM LINEE l
        JOIN ORARI_LINEE ol ON l.codice_linea = ol.codice_linea
        JOIN ATTUAZIONI_CORSE ac ON ol.codice_orario = ac.codice_orario
        JOIN CONVALIDE c ON ac.codice_corsa = c.codice_corsa
        GROUP BY codice_linea
        ORDER BY COUNT(*) DESC
        );
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

    /*
     * ALTRE QUERY
     */
}
