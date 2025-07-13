Link al repository del progetto: https://github.com/EnryBarto/DB25-smart-city.git .

Per poter eseguire l'applicativo è necessario disporre di una versione del Java Runtime Environment >= 21.
Il DBMS richiesto è MySQL (noi abbiamo utilizzato MySQL Server versione 8.0.42).

Tutti i file .sql di cui parleremo si trovano al percorso src/main/sql .

Passi per eseguire l'applicativo:
- Creare il DB utilizzando il file creazione_db.sql .
- Se si vuole un'installazione pulita inserire i dati di base utilizzando il file required_data_insertion.sql .
    Se invece si vogliono utilizzare i dati di demo, caricare solo il file demo_data_insertion.sql .
- Avviare l'app tramite il jar smart-city.jar presente nella radice della cartella.
    Poiché è stato utilizzato gradle, è possibile eseguire il progetto anche utilizzando il comando ./gradlew run .
- A ogni avvio verranno richiesti i parametri di connesione al DB (indirizzo e porta del server, nome DB, utente e password).
- Nel caso si fosse optato per l'installazione pulita (quindi utilizzando il file required_data_insertion.sql), è possibile loggarsi con un account Amministrativo utilizzando Utente: admin Password: admin
- Nel caso si fosse optato per la demo (file demo_data_insertion.sql), riportiamo i vari account disponibili:
    +--------------------+--------------+------------------+
    | Livello Utente   | Username       | Password         |
    +--------------------+--------------+------------------+
    | Amministrativo   | admin          | admin            |
    | Utente base      | Utente1        | Utente1          |
    | Utente base      | Utente2        | Utente2          |
    | Autista          | Autista1       | Autista1         |
    | Autista          | Autista2       | Autista2         |
    | Controllore      | Controllore1   | Controllore1     |
    | Controllore      | Controllore2   | Controllore2     |
    | Controllore      | Controllore3   | Controllore3     |
    | Controllore      | Controllore4   | Controllore4     |
    | Controllore      | Controllore5   | Controllore5     |
    | Controllore      | Controllore6   | Controllore6     |
    +--------------------+--------------+------------------+