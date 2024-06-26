-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: sistemas_distribuidos
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `job_dataset`
--

DROP TABLE IF EXISTS `job_dataset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_dataset` (
  `idjob_dataset` int NOT NULL AUTO_INCREMENT,
  `idrecruiter` int NOT NULL,
  `experience` varchar(45) NOT NULL,
  `skill` varchar(45) NOT NULL,
  PRIMARY KEY (`idjob_dataset`,`idrecruiter`),
  KEY `idrecruiter` (`idrecruiter`),
  CONSTRAINT `job_dataset_ibfk_1` FOREIGN KEY (`idrecruiter`) REFERENCES `recruiter` (`idrecruiter`),
  CONSTRAINT `job_dataset_ibfk_2` FOREIGN KEY (`idrecruiter`) REFERENCES `recruiter` (`idrecruiter`),
  CONSTRAINT `job_dataset_ibfk_3` FOREIGN KEY (`idrecruiter`) REFERENCES `recruiter` (`idrecruiter`),
  CONSTRAINT `job_dataset_ibfk_4` FOREIGN KEY (`idrecruiter`) REFERENCES `recruiter` (`idrecruiter`),
  CONSTRAINT `job_dataset_ibfk_5` FOREIGN KEY (`idrecruiter`) REFERENCES `recruiter` (`idrecruiter`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_dataset`
--

LOCK TABLES `job_dataset` WRITE;
/*!40000 ALTER TABLE `job_dataset` DISABLE KEYS */;
INSERT INTO `job_dataset` VALUES (2,1,'10','3');
/*!40000 ALTER TABLE `job_dataset` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-19  1:17:54
