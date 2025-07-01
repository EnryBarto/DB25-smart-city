DELIMITER //

CREATE PROCEDURE aggiorna_attivazione_linea(IN cod_linea VARCHAR(30))
BEGIN
	-- Aggiornamento dello stato della linea
	UPDATE linee L
	SET attiva = NOT EXISTS (SELECT 1
							FROM manutenzioni_linee
							WHERE codice_linea = cod_linea AND CURDATE() BETWEEN data_inizio AND data_fine)
	WHERE codice_linea = cod_linea;

END //

CREATE PROCEDURE aggiorna_attivazione_linee()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur_cod_linea VARCHAR(30);

    DECLARE cur CURSOR FOR
        SELECT codice_linea FROM linee WHERE attiva IS NOT NULL;

    -- Gestione della fine del cursore
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Apertura del cursore
    OPEN cur;

    -- Ciclo sui risultati
    read_loop: LOOP
        FETCH cur INTO cur_cod_linea;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Aggiornamento dello stato della linea in esame
        CALL aggiorna_attivazione_linea(cur_cod_linea);

    END LOOP;

    CLOSE cur;
END //

DELIMITER ;