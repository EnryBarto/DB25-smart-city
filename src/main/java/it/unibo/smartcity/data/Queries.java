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
