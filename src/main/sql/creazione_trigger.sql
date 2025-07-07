DELIMITER //

CREATE TRIGGER dopo_insert_manut_linea
AFTER INSERT ON MANUTENZIONI_LINEE
FOR EACH ROW
BEGIN
    CALL aggiorna_attivazione_linea(NEW.codice_linea);
END//

CREATE TRIGGER dopo_update_manut_linea
AFTER UPDATE ON MANUTENZIONI_LINEE
FOR EACH ROW
BEGIN
    CALL aggiorna_attivazione_linea(NEW.codice_linea);
END//

CREATE TRIGGER dopo_delete_manut_linea
AFTER DELETE ON MANUTENZIONI_LINEE
FOR EACH ROW
BEGIN
    CALL aggiorna_attivazione_linea(OLD.codice_linea);
END//

DELIMITER ;
