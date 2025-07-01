CREATE EVENT aggiorna_linee_event
ON SCHEDULE EVERY 1 DAY
DO
  CALL aggiorna_attivazione_linee();
