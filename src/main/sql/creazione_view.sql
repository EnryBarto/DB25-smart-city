CREATE VIEW VW_LINEE_ATTIVE_OGGI AS
SELECT L.*, M.nome AS tipo_mezzo, F1.codice_fermata AS part_codice_fermata, F1.nome AS part_nome, F1.indirizzo_via AS part_via, F1.indirizzo_civico AS part_civico, F1.indirizzo_comune AS part_comune, F1.indirizzo_cap AS part_cap, F1.longitudine AS part_long, F1.latitudine AS part_lat, F2.codice_fermata AS arr_codice_fermata, F2.nome AS arr_nome, F2.indirizzo_via AS arr_via, F2.indirizzo_civico AS arr_civico, F2.indirizzo_comune AS arr_comune, F2.indirizzo_cap AS arr_cap, F2.longitudine AS arr_long, F2.latitudine AS arr_lat
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
	AND (L.attiva IS TRUE OR (CURDATE() BETWEEN L.inizio_validita AND L.fine_validita))
ORDER BY L.codice_linea;