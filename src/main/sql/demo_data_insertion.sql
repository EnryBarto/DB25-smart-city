-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smart_city
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `ABBONAMENTI`
--

LOCK TABLES `ABBONAMENTI` WRITE;
/*!40000 ALTER TABLE `ABBONAMENTI` DISABLE KEYS */;
INSERT INTO `ABBONAMENTI` VALUES ('2025-07-07',1,'2025-07-07',183,'Utente1'),('2025-07-14',2,'2025-07-07',183,'Utente1');
/*!40000 ALTER TABLE `ABBONAMENTI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ATTUAZIONI_CORSE`
--

LOCK TABLES `ATTUAZIONI_CORSE` WRITE;
/*!40000 ALTER TABLE `ATTUAZIONI_CORSE` DISABLE KEYS */;
INSERT INTO `ATTUAZIONI_CORSE` VALUES (5,'2025-07-03',4,'METRO000','Autista1'),(6,'2025-07-03',23,'METRO001','Autista1'),(8,'2025-07-03',50,'BUS000','Autista2'),(10,'2025-07-03',57,'BUS002','Autista2'),(11,'2025-07-03',65,'TRENO000','Autista1'),(12,'2025-07-03',72,'TRENO001','Autista1'),(15,'2025-07-03',43,'TRAM001','Autista2');
/*!40000 ALTER TABLE `ATTUAZIONI_CORSE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `AZIENDE`
--

LOCK TABLES `AZIENDE` WRITE;
/*!40000 ALTER TABLE `AZIENDE` DISABLE KEYS */;
INSERT INTO `AZIENDE` VALUES ('30732476298','Serao, Malacarne e Gasperi s.r.l.','49','Contrada Nico','Corrado ligure',45382,'207735989','laura61@tele2.it'),('41233010444','Trussardi Group','82','Rotonda Gigli','Orengo ligure',83000,'6966652368','giustino28@bondumier.com'),('66108967019','Gentileschi, Beccaria e Castellitto SPA','69','Piazza Bellò','Borgo Melina a mare',42087,'4951477040','ltreccani@borghese.com'),('73908386852','Galeati, Trillini e Iannuzzi SPA','21','Via Ligorio','Borgo Arnulfo',70589,'8343897493','clattuada@gigli.com'),('80997108159','Carli-Riccardi Group','28','Canale Augusto','San Umberto umbro',84341,'6848820571','marco85@carosone.com');
/*!40000 ALTER TABLE `AZIENDE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `BIGLIETTI`
--

LOCK TABLES `BIGLIETTI` WRITE;
/*!40000 ALTER TABLE `BIGLIETTI` DISABLE KEYS */;
INSERT INTO `BIGLIETTI` VALUES (10,'2025-07-03',90,'Utente1'),(11,'2025-07-07',1440,'Utente1');
/*!40000 ALTER TABLE `BIGLIETTI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `CAUSALI_MULTE`
--

LOCK TABLES `CAUSALI_MULTE` WRITE;
/*!40000 ALTER TABLE `CAUSALI_MULTE` DISABLE KEYS */;
INSERT INTO `CAUSALI_MULTE` VALUES (1,'Mancanza titolo di viaggio',20.00,500.00),(2,'Deturpazione mezzi di trasporto',50.00,1000.00);
/*!40000 ALTER TABLE `CAUSALI_MULTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `CONTENUTI`
--

LOCK TABLES `CONTENUTI` WRITE;
/*!40000 ALTER TABLE `CONTENUTI` DISABLE KEYS */;
INSERT INTO `CONTENUTI` VALUES (1,1,30,30),(1,2,25,25),(2,2,20,20),(2,3,7,7),(2,4,10,10),(3,1,15,15),(3,2,30,30),(3,3,10,10),(3,4,20,20);
/*!40000 ALTER TABLE `CONTENUTI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `CONTENUTI_HUB`
--

LOCK TABLES `CONTENUTI_HUB` WRITE;
/*!40000 ALTER TABLE `CONTENUTI_HUB` DISABLE KEYS */;
INSERT INTO `CONTENUTI_HUB` VALUES (1,'Monopattini'),(2,'Bici'),(3,'Macchine elettriche'),(4,'Scooter elettrici');
/*!40000 ALTER TABLE `CONTENUTI_HUB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `CONTROLLI`
--

LOCK TABLES `CONTROLLI` WRITE;
/*!40000 ALTER TABLE `CONTROLLI` DISABLE KEYS */;
INSERT INTO `CONTROLLI` VALUES ('Controllore1',5),('Controllore2',5),('Controllore3',5),('Controllore4',5),('Controllore5',5),('Controllore6',5),('Controllore1',6),('Controllore2',8),('Controllore2',10),('Controllore2',11),('Controllore2',12),('Controllore1',15);
/*!40000 ALTER TABLE `CONTROLLI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `CONVALIDE`
--

LOCK TABLES `CONVALIDE` WRITE;
/*!40000 ALTER TABLE `CONVALIDE` DISABLE KEYS */;
INSERT INTO `CONVALIDE` VALUES (10,'2025-07-03 23:39:14',5);
/*!40000 ALTER TABLE `CONVALIDE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `DIPENDENTI`
--

LOCK TABLES `DIPENDENTI` WRITE;
/*!40000 ALTER TABLE `DIPENDENTI` DISABLE KEYS */;
INSERT INTO `DIPENDENTI` VALUES ('admin','amministrativo'),('Autista1','autista'),('Autista2','autista'),('Controllore1','controllore'),('Controllore2','controllore'),('Controllore3','controllore'),('Controllore4','controllore'),('Controllore5','controllore'),('Controllore6','controllore');
/*!40000 ALTER TABLE `DIPENDENTI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `FERMATE`
--

LOCK TABLES `FERMATE` WRITE;
/*!40000 ALTER TABLE `FERMATE` DISABLE KEYS */;
INSERT INTO `FERMATE` VALUES (1,'San Bartolo','Via San Bartolo','Terre Roveresche','1',61038,'12.9095371','43.692148'),(2,'Vergineto','Via Vergineto Alto','Terre Roveresche','1',61038,'12.9109262','43.6946103'),(3,'Villa del Monte (Vasai)','Via Vergineto Campagna','Terre Roveresche','8',61038,'12.9070632','43.6878786'),(4,'Passo Pian di Rose','Via Schieppe Angelica','Terre Roveresche','27',61038,'12.8966309','43.711126'),(5,'Schieppe Rotatoria','Via dell\'Industria','Terre Roveresche','1',61038,'12.8984922','43.7187093'),(6,'Tavernelle (Chiesa)','Via Flaminia','Colli al Metauro','49',61036,'12.88364','43.7325936'),(7,'Pianventena (Centro)','Morciano','San Giovanni in Marignano','2080',47842,'12.6867276','43.9272198'),(8,'S. G. in Marignano 6 (C.Sportivo)','Pianventena','San Giovanni in Marignano','6',47842,'12.7020643','43.936031'),(9,'S. G. in Marignano 4 (Comune)','Roma','San Giovanni in Marignano','4',47842,'12.7133298','43.9390542'),(10,'Morciano (Centro Studi)','Largo Centro Studi','Morciano di Romagna','6',47833,'12.6490851','43.9161903'),(11,'S. Angelo in Vado','Via Nazionale Nord','Sant\'Angelo in Vado','21',61048,'12.412138','43.666995'),(12,'Urbania (Ospedale)','Via Roma','Urbania','59',61049,'12.519487','43.669936'),(13,'Urbania (Centro)','Via Fosso del Maltempo','Urbania','1',61049,'12.522018','43.666164'),(14,'Fermignano (M. L. King)','Via Martin Luther King','Fermignano','74',61033,'12.642649','43.681967'),(15,'Urbino Park S.Lucia','Viale Giuseppe di Vittorio','Urbino','30',61029,'12.635021','43.730304'),(16,'Cesena (Università)','Via dell\'Università','Cesena','50',47522,'12.236238','44.147750');
/*!40000 ALTER TABLE `FERMATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `HUB_MOBILITA`
--

LOCK TABLES `HUB_MOBILITA` WRITE;
/*!40000 ALTER TABLE `HUB_MOBILITA` DISABLE KEYS */;
INSERT INTO `HUB_MOBILITA` VALUES (1,'12.9091025','43.6921713','Hub Acli','Via San Bartolo','Terre Roveresche',61038,'1',1),(2,'12.8836289','43.7326982','Hub Tavernelle','Via Flaminia','Colli al Metauro',61036,'49',6),(3,'12.236650  ','44.147444','Hub Università','Via dell\'Università','Cesena',47522,'50',16);
/*!40000 ALTER TABLE `HUB_MOBILITA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `LINEE`
--

LOCK TABLES `LINEE` WRITE;
/*!40000 ALTER TABLE `LINEE` DISABLE KEYS */;
INSERT INTO `LINEE` VALUES ('15A',43,NULL,NULL,1,1),('15R',43,NULL,NULL,1,1),('26A',16,NULL,NULL,0,4),('26R',18,NULL,NULL,1,4),('26STR/A',11,'2025-07-04','2025-07-19',NULL,1),('3906',91,NULL,NULL,1,2),('3925',91,NULL,NULL,1,2),('C1A',15,NULL,NULL,1,3),('C1R',15,NULL,NULL,1,3);
/*!40000 ALTER TABLE `LINEE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MANUTENZIONI_LINEE`
--

LOCK TABLES `MANUTENZIONI_LINEE` WRITE;
/*!40000 ALTER TABLE `MANUTENZIONI_LINEE` DISABLE KEYS */;
INSERT INTO `MANUTENZIONI_LINEE` VALUES ('26A','2025-07-04','2025-07-19','Manutenzione rotaie','Sostituzione rotaie linea metropolitana','73908386852');
/*!40000 ALTER TABLE `MANUTENZIONI_LINEE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MANUTENZIONI_MEZZI`
--

LOCK TABLES `MANUTENZIONI_MEZZI` WRITE;
/*!40000 ALTER TABLE `MANUTENZIONI_MEZZI` DISABLE KEYS */;
INSERT INTO `MANUTENZIONI_MEZZI` VALUES ('METRO001','2025-07-04','2025-07-10','Manut. metro 1','Manutenzione straordinaria','30732476298'),('BUS002','2025-07-08','2025-07-22','Manut. metro 2','Manutenzione impianto frenante',NULL),('METRO001','2025-07-27','2025-07-31','Manut. metro 1','Manutenzione pantografi',NULL);
/*!40000 ALTER TABLE `MANUTENZIONI_MEZZI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MEZZI`
--

LOCK TABLES `MEZZI` WRITE;
/*!40000 ALTER TABLE `MEZZI` DISABLE KEYS */;
INSERT INTO `MEZZI` VALUES ('BUS000',1),('BUS001',1),('BUS002',1),('TRENO000',2),('TRENO001',2),('TRAM000',3),('TRAM001',3),('METRO000',4),('METRO001',4),('METRO002',4);
/*!40000 ALTER TABLE `MEZZI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MULTE`
--

LOCK TABLES `MULTE` WRITE;
/*!40000 ALTER TABLE `MULTE` DISABLE KEYS */;
INSERT INTO `MULTE` VALUES (14,'2025-07-13 15:12:04',55.60,NULL,1,5,'USER001','Controllore1'),(15,'2025-07-13 15:18:36',78.90,NULL,2,5,'USER002','Controllore2');
/*!40000 ALTER TABLE `MULTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ORARI_LINEE`
--

LOCK TABLES `ORARI_LINEE` WRITE;
/*!40000 ALTER TABLE `ORARI_LINEE` DISABLE KEYS */;
INSERT INTO `ORARI_LINEE` VALUES (50,'06:55','Giovedì','15A'),(47,'06:55','Lunedì','15A'),(48,'06:55','Martedì','15A'),(49,'06:55','Mercoledì','15A'),(52,'06:55','Sabato','15A'),(51,'06:55','Venerdì','15A'),(53,'09:30','Domenica','15A'),(57,'15:15','Giovedì','15R'),(54,'15:15','Lunedì','15R'),(55,'15:15','Martedì','15R'),(56,'15:15','Mercoledì','15R'),(59,'15:15','Sabato','15R'),(58,'15:15','Venerdì','15R'),(61,'17:00','Domenica','15R'),(4,'06:25','Giovedì','26A'),(1,'06:25','Lunedì','26A'),(2,'06:25','Martedì','26A'),(3,'06:25','Mercoledì','26A'),(7,'06:25','Sabato','26A'),(5,'06:25','Venerdì','26A'),(25,'09:30','Domenica','26A'),(23,'13:40','Giovedì','26R'),(8,'13:40','Lunedì','26R'),(15,'13:40','Martedì','26R'),(24,'13:40','Mercoledì','26R'),(21,'13:40','Sabato','26R'),(20,'13:40','Venerdì','26R'),(26,'16:00','Domenica','26R'),(68,'07:20','Domenica','3906'),(65,'07:20','Giovedì','3906'),(62,'07:20','Lunedì','3906'),(63,'07:20','Martedì','3906'),(64,'07:20','Mercoledì','3906'),(67,'07:20','Sabato','3906'),(66,'07:20','Venerdì','3906'),(75,'15:42','Domenica','3925'),(72,'15:42','Giovedì','3925'),(69,'15:42','Lunedì','3925'),(70,'15:42','Martedì','3925'),(71,'15:42','Mercoledì','3925'),(74,'15:42','Sabato','3925'),(73,'15:42','Venerdì','3925'),(43,'08:00','Giovedì','C1A'),(40,'08:00','Lunedì','C1A'),(41,'08:00','Martedì','C1A'),(42,'08:00','Mercoledì','C1A'),(44,'08:00','Venerdì','C1A'),(46,'10:00','Domenica','C1A'),(45,'10:00','Sabato','C1A'),(36,'07:30','Giovedì','C1R'),(33,'07:30','Lunedì','C1R'),(34,'07:30','Martedì','C1R'),(35,'07:30','Mercoledì','C1R'),(37,'07:30','Venerdì','C1R'),(39,'09:30','Domenica','C1R'),(38,'09:30','Sabato','C1R');
/*!40000 ALTER TABLE `ORARI_LINEE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PERSONE`
--

LOCK TABLES `PERSONE` WRITE;
/*!40000 ALTER TABLE `PERSONE` DISABLE KEYS */;
INSERT INTO `PERSONE` VALUES ('admin','admin','Amm91332AA',NULL),('Autista','Primo','AUT001',NULL),('Autista','Secondo','AUT002',NULL),('Controllore','Primo','CONT001',NULL),('Controllore','Secondo','CONT002',NULL),('Controllore','Terzo','CONT003',NULL),('Controllore','Quarto','CONT004',NULL),('Controllore','Quinto','CONT005',NULL),('Controllore','Sesto','CONT006',NULL),('Rossi','Mario','USER001',NULL),('Verdi','Luigi','USER002',NULL);
/*!40000 ALTER TABLE `PERSONE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `SOSTITUZIONI`
--

LOCK TABLES `SOSTITUZIONI` WRITE;
/*!40000 ALTER TABLE `SOSTITUZIONI` DISABLE KEYS */;
INSERT INTO `SOSTITUZIONI` VALUES ('2025-07-04','26A','26STR/A');
/*!40000 ALTER TABLE `SOSTITUZIONI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TARIFFE_ABBONAMENTI`
--

LOCK TABLES `TARIFFE_ABBONAMENTI` WRITE;
/*!40000 ALTER TABLE `TARIFFE_ABBONAMENTI` DISABLE KEYS */;
INSERT INTO `TARIFFE_ABBONAMENTI` VALUES ('Mensile',31,39.50),('Semestrale',183,200.00),('Annuale',365,330.00),('Quinquennale',1827,1320.00);
/*!40000 ALTER TABLE `TARIFFE_ABBONAMENTI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TARIFFE_BIGLIETTI`
--

LOCK TABLES `TARIFFE_BIGLIETTI` WRITE;
/*!40000 ALTER TABLE `TARIFFE_BIGLIETTI` DISABLE KEYS */;
INSERT INTO `TARIFFE_BIGLIETTI` VALUES ('90min',90,2.20),('Giornaliero',1440,7.60),('Tre giorni',4320,15.50);
/*!40000 ALTER TABLE `TARIFFE_BIGLIETTI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TIPOLOGIE_MEZZI`
--

LOCK TABLES `TIPOLOGIE_MEZZI` WRITE;
/*!40000 ALTER TABLE `TIPOLOGIE_MEZZI` DISABLE KEYS */;
INSERT INTO `TIPOLOGIE_MEZZI` VALUES (1,'Autobus'),(2,'Treno'),(3,'Tram'),(4,'Metro');
/*!40000 ALTER TABLE `TIPOLOGIE_MEZZI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TRAGITTI`
--

LOCK TABLES `TRAGITTI` WRITE;
/*!40000 ALTER TABLE `TRAGITTI` DISABLE KEYS */;
INSERT INTO `TRAGITTI` VALUES (11,12,'15A',1),(12,13,'15A',2),(13,14,'15A',3),(14,15,'15A',4),(12,11,'15R',4),(13,12,'15R',3),(14,13,'15R',2),(15,14,'15R',1),(1,2,'26A',2),(2,4,'26A',3),(3,1,'26A',1),(4,5,'26A',4),(5,6,'26A',5),(1,3,'26R',5),(2,1,'26R',4),(4,2,'26R',3),(5,4,'26R',2),(6,5,'26R',1),(2,4,'26STR/A',1),(4,5,'26STR/A',2),(5,6,'26STR/A',3),(2,8,'3906',2),(8,16,'3906',3),(13,2,'3906',1),(2,13,'3925',3),(8,2,'3925',2),(16,8,'3925',1),(7,10,'C1A',3),(8,7,'C1A',2),(9,8,'C1A',1),(7,8,'C1R',2),(8,9,'C1R',3),(10,7,'C1R',1);
/*!40000 ALTER TABLE `TRAGITTI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TRATTE`
--

LOCK TABLES `TRATTE` WRITE;
/*!40000 ALTER TABLE `TRATTE` DISABLE KEYS */;
INSERT INTO `TRATTE` VALUES (2,1,2),(3,1,3),(1,2,3),(4,2,4),(8,2,32),(13,2,25),(1,3,3),(2,4,5),(5,4,1),(4,5,1),(6,5,6),(5,6,6),(8,7,5),(10,7,7),(2,8,32),(7,8,5),(9,8,3),(16,8,34),(8,9,3),(7,10,7),(12,11,11),(11,12,11),(13,12,3),(2,13,25),(12,13,3),(14,13,15),(13,14,15),(15,14,14),(14,15,14),(8,16,34);
/*!40000 ALTER TABLE `TRATTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `UTENTI`
--

LOCK TABLES `UTENTI` WRITE;
/*!40000 ALTER TABLE `UTENTI` DISABLE KEYS */;
INSERT INTO `UTENTI` VALUES ('admin','Amm91332AA','admin@smartcity.it','123456789','$2a$10$u43PXOBQkSbOwUtbrNeot./Qk1qAf0EAnNT5KDwYgBEnuxtTk8J5q'),('Autista1','AUT001','autista1@smartcity.it','321654987','$2a$10$TGAuH/Re1jzHa3NHdwykpu60fMKc2Ghy4Vmbor/Nsh4b/KdZT8i42'),('Autista2','AUT002','autista2@smartcity.it','984984615','$2a$10$BU1gZmInantLswAm20IgWeTuHoRLPDrd6Y5PMPJi21U4WvYJi88i.'),('Controllore1','CONT001','controllore1@smartcity.it','984950656','$2a$10$zUus5ZzuXwp79NUr4KZw4.hejUoQGfpvpZFQUGLlIsFiHAV1Sm0G2'),('Controllore2','CONT002','controllore2@smartcity.it','29842899','$2a$10$VdyFKvVTqXyu7w1LHgmAveP5XNfvrMSGODEW/1rYoPaRDtxLjfBHq'),('Controllore3','CONT003','controllore3@smartcity.it','48924454', '$2a$10$hJ8VcmbjZnqhKPRdXxkDX.9hVbbAKdJJmUc1RtX16YSdtlUyyRaU2'),('Controllore4','CONT004','controllore4@smartcity.it','6465151651','$2a$10$uSypZNolf2oEY9EibIAnYea18/KcJyY1OGsEhtLwhgk5xEgoNJCSu'),('Controllore5','CONT005','controllore5@smartcity.it','9428449984','$2a$10$eGSJ2jGBo6yk25jdLlnoCOKpRRH9Yt56BLO2UZdnl7W1L5Bmssefm'),('Controllore6','CONT006','controllore6@smartcity.it','8984194981','$2a$10$g6BBefAl3r4RVwsEhyaI9ui2y62cQZCdWco1TDpNE.s5wfdSgqSXi'),('Utente1','USER001','mario@smartcity.it','9871654655','$2a$10$GyKtq8CYLJE8ZcQUNqsH3OanopqfsnI.ZK9O3v5WI1fpooMYdsPB.'),('Utente2','USER002','utente2@smartcity.it','658498465','$2a$10$fQllAz5PXB.v9jQc1rhd.eKFxQYDFGCfgPnYOjjGa3wbRKM.V9GtS');
/*!40000 ALTER TABLE `UTENTI` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-13 15:28:30
