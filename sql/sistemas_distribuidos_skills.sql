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
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `idcandidate` int NOT NULL,
  `experience` varchar(45) NOT NULL,
  `idskills_dataset` int NOT NULL,
  PRIMARY KEY (`idcandidate`,`idskills_dataset`),
  KEY `idskills_dataset` (`idskills_dataset`),
  CONSTRAINT `skills_ibfk_1` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `skills_ibfk_10` FOREIGN KEY (`idskills_dataset`) REFERENCES `skills_dataset` (`idskills_dataset`),
  CONSTRAINT `skills_ibfk_3` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `skills_ibfk_4` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `skills_ibfk_5` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `skills_ibfk_6` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `skills_ibfk_7` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `skills_ibfk_8` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `skills_ibfk_9` FOREIGN KEY (`idcandidate`) REFERENCES `candidate` (`idcandidate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (4,'10',3);
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
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
