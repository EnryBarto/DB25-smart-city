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

    public static final String LIST_UTENTI =
        """
        SELECT u.username, u.documento, u.email, u.telefono
        FROM UTENTI u
        """;
}
