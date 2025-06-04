package it.unibo.smartcity.data;

public final class Queries {

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
}
