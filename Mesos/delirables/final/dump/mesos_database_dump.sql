-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: mesos
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `gamesdb`
--

DROP TABLE IF EXISTS `gamesdb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gamesdb` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `score` int NOT NULL,
  `game_date` datetime NOT NULL,
  `num_players` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gamesdb`
--

LOCK TABLES `gamesdb` WRITE;
/*!40000 ALTER TABLE `gamesdb` DISABLE KEYS */;
INSERT INTO `gamesdb` VALUES (1,'giuseppe',100,'2026-05-26 00:00:00',4),(2,'mattia',110,'2026-05-26 00:00:00',4),(3,'denise',150,'2026-05-26 00:00:00',4),(4,'xiayi',90,'2026-05-26 00:00:00',4),(5,'aldo',90,'2026-05-26 00:00:00',4),(6,'gloria',200,'2026-05-26 00:00:00',4),(7,'gloria',200,'2026-05-26 00:00:00',4),(8,'viola',900,'2026-05-26 00:00:00',2),(9,'giuseppe',89,'2026-06-10 00:00:00',4),(10,'mattia',99,'2026-06-10 00:00:00',4),(11,'denise',83,'2026-06-10 00:00:00',4),(12,'xiayi',120,'2026-06-10 00:00:00',4),(13,'giuseppe',102,'2026-06-10 00:00:00',5),(14,'mattia',78,'2026-06-10 00:00:00',5),(15,'denise',117,'2026-06-10 00:00:00',5),(16,'xiayi',39,'2026-06-10 00:00:00',5),(17,'giuseppe',60,'2026-06-10 00:00:00',2),(18,'mattia',115,'2026-06-10 00:00:00',2),(19,'denise',63,'2026-06-10 00:00:00',2),(20,'xiayi',109,'2026-06-10 00:00:00',2),(21,'giuseppe',51,'2026-06-10 00:00:00',3),(22,'mattia',114,'2026-06-10 00:00:00',3),(23,'denise',50,'2026-06-10 00:00:00',3),(24,'xiayi',74,'2026-06-10 00:00:00',3),(25,'gloria',77,'2026-06-10 00:00:00',2),(26,'gloria',72,'2026-06-10 00:00:00',3),(27,'gloria',46,'2026-06-10 00:00:00',4),(28,'gloria',79,'2026-06-10 00:00:00',5),(29,'andrea',99,'2026-06-10 00:00:00',2),(30,'andrea',94,'2026-06-10 00:00:00',3),(31,'andrea',60,'2026-06-10 00:00:00',4),(32,'andrea',119,'2026-06-10 00:00:00',5),(33,'elena',21,'2026-06-10 00:00:00',2),(34,'elena',104,'2026-06-10 00:00:00',3),(35,'elena',71,'2026-06-10 00:00:00',4),(36,'elena',58,'2026-06-10 00:00:00',5),(37,'viola',23,'2026-06-10 00:00:00',2),(38,'viola',23,'2026-06-10 00:00:00',3),(39,'viola',29,'2026-06-10 00:00:00',4),(40,'viola',141,'2026-06-10 00:00:00',5),(41,'aldo',55,'2026-06-10 00:00:00',2),(42,'aldo',109,'2026-06-10 00:00:00',3),(43,'aldo',129,'2026-06-10 00:00:00',4),(44,'aldo',40,'2026-06-10 00:00:00',5),(45,'maicol',40,'2026-06-10 00:00:00',2),(46,'maicol',148,'2026-06-10 00:00:00',3),(47,'maicol',23,'2026-06-10 00:00:00',4),(48,'maicol',138,'2026-06-10 00:00:00',5),(49,'giuseppe',75,'2026-06-10 00:00:00',4),(50,'mattia',79,'2026-06-10 00:00:00',4),(51,'denise',60,'2026-06-10 00:00:00',4),(52,'xiayi',47,'2026-06-10 00:00:00',4),(53,'giuseppe',91,'2026-06-10 00:00:00',5),(54,'mattia',147,'2026-06-10 00:00:00',5),(55,'denise',42,'2026-06-10 00:00:00',5),(56,'xiayi',81,'2026-06-10 00:00:00',5),(57,'giuseppe',65,'2026-06-10 00:00:00',2),(58,'mattia',146,'2026-06-10 00:00:00',2),(59,'denise',46,'2026-06-10 00:00:00',2),(60,'xiayi',51,'2026-06-10 00:00:00',2),(61,'giuseppe',134,'2026-06-10 00:00:00',3),(62,'mattia',40,'2026-06-10 00:00:00',3),(63,'denise',89,'2026-06-10 00:00:00',3),(64,'xiayi',78,'2026-06-10 00:00:00',3),(65,'gloria',79,'2026-06-10 00:00:00',2),(66,'gloria',128,'2026-06-10 00:00:00',3),(67,'gloria',43,'2026-06-10 00:00:00',4),(68,'gloria',28,'2026-06-10 00:00:00',5),(69,'andrea',102,'2026-06-10 00:00:00',2),(70,'andrea',108,'2026-06-10 00:00:00',3),(71,'andrea',139,'2026-06-10 00:00:00',4),(72,'andrea',91,'2026-06-10 00:00:00',5),(73,'elena',61,'2026-06-10 00:00:00',2),(74,'elena',74,'2026-06-10 00:00:00',3),(75,'elena',134,'2026-06-10 00:00:00',4),(76,'elena',149,'2026-06-10 00:00:00',5),(77,'viola',100,'2026-06-10 00:00:00',2),(78,'viola',47,'2026-06-10 00:00:00',3),(79,'viola',137,'2026-06-10 00:00:00',4),(80,'viola',55,'2026-06-10 00:00:00',5),(81,'aldo',139,'2026-06-10 00:00:00',2),(82,'aldo',90,'2026-06-10 00:00:00',3),(83,'aldo',111,'2026-06-10 00:00:00',4),(84,'aldo',58,'2026-06-10 00:00:00',5),(85,'maicol',83,'2026-06-10 00:00:00',2),(86,'maicol',131,'2026-06-10 00:00:00',3),(87,'maicol',92,'2026-06-10 00:00:00',4),(88,'maicol',130,'2026-06-10 00:00:00',5),(89,'io',20,'2026-06-10 00:00:00',5),(90,'colui',20,'2026-06-10 00:00:00',5);
/*!40000 ALTER TABLE `gamesdb` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-10 10:54:12
