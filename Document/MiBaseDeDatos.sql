-- MariaDB dump 10.19-11.3.1-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: lascuadrillas
-- ------------------------------------------------------
-- Server version	11.3.1-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `costalero`
--

DROP TABLE IF EXISTS `costalero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `costalero` (
  `id` int(7) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL,
  `height` int(3) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costalero`
--

LOCK TABLES `costalero` WRITE;
/*!40000 ALTER TABLE `costalero` DISABLE KEYS */;
INSERT INTO `costalero` VALUES
(7,'JUAN',200,123),
(8,'pedro',180,20),
(10,'juanito',180,20),
(11,'TestNick',180,25);
/*!40000 ALTER TABLE `costalero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuadrilla`
--

DROP TABLE IF EXISTS `cuadrilla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuadrilla` (
  `id` int(7) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `overseer` varchar(20) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuadrilla`
--

LOCK TABLES `cuadrilla` WRITE;
/*!40000 ALTER TABLE `cuadrilla` DISABLE KEYS */;
INSERT INTO `cuadrilla` VALUES
(19,'hjk','rgd','cuadrilla de jovenes'),
(22,'las juve','alfonso','los jovenes'),
(23,'la jugentu','alfonso','fdfdg'),
(29,'Los sss','Frante','Cuadrilla de los verded'),
(30,'alfonsojaen','alfonso','pedro'),
(31,'alfonsotiyo','jaen','tejederas');
/*!40000 ALTER TABLE `cuadrilla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `esta`
--

DROP TABLE IF EXISTS `esta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `esta` (
  `cuadrillaId` int(7) unsigned NOT NULL,
  `pasoId` int(7) unsigned NOT NULL,
  PRIMARY KEY (`cuadrillaId`,`pasoId`),
  KEY `pasoId` (`pasoId`),
  CONSTRAINT `esta_ibfk_1` FOREIGN KEY (`cuadrillaId`) REFERENCES `cuadrilla` (`id`),
  CONSTRAINT `esta_ibfk_2` FOREIGN KEY (`pasoId`) REFERENCES `paso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `esta`
--

LOCK TABLES `esta` WRITE;
/*!40000 ALTER TABLE `esta` DISABLE KEYS */;
INSERT INTO `esta` VALUES
(19,1),
(23,1),
(30,1),
(23,2),
(30,3);
/*!40000 ALTER TABLE `esta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paso`
--

DROP TABLE IF EXISTS `paso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paso` (
  `id` int(7) unsigned NOT NULL AUTO_INCREMENT,
  `brotherhood` varchar(25) DEFAULT NULL,
  `capacity` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `brotherhood` (`brotherhood`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paso`
--

LOCK TABLES `paso` WRITE;
/*!40000 ALTER TABLE `paso` DISABLE KEYS */;
INSERT INTO `paso` VALUES
(1,'Nazareno',45),
(2,'Soledad',35),
(3,'LCOOS',70),
(6,'sdvsd',34);
/*!40000 ALTER TABLE `paso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pertenece`
--

DROP TABLE IF EXISTS `pertenece`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pertenece` (
  `cuadrillaId` int(7) unsigned NOT NULL,
  `costaleroId` int(7) unsigned NOT NULL,
  PRIMARY KEY (`cuadrillaId`,`costaleroId`),
  KEY `costaleroId` (`costaleroId`),
  CONSTRAINT `pertenece_ibfk_1` FOREIGN KEY (`cuadrillaId`) REFERENCES `cuadrilla` (`id`),
  CONSTRAINT `pertenece_ibfk_2` FOREIGN KEY (`costaleroId`) REFERENCES `costalero` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pertenece`
--

LOCK TABLES `pertenece` WRITE;
/*!40000 ALTER TABLE `pertenece` DISABLE KEYS */;
INSERT INTO `pertenece` VALUES
(19,7),
(22,7),
(23,7),
(29,7),
(19,8),
(31,8),
(19,11),
(22,11);
/*!40000 ALTER TABLE `pertenece` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(35) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
('alfonso33','081ed44ae80a3b6cb53056711b89f73672e099b372ace4a431ac7d8c4a358b42','alfonso','aj@gmail.com'),
('alfonsoj','c7e52d8590eaed2bc3558eacd21b5ed7d1ac0770507440f8bc2748308090bc77','alfonso','jaen@gmail.com'),
('alfonsojaen','081ed44ae80a3b6cb53056711b89f73672e099b372ace4a431ac7d8c4a358b42','alfonso','alfonso@gmail.com'),
('alvader05','081ed44ae80a3b6cb53056711b89f73672e099b372ace4a431ac7d8c4a358b42','alfonso','alfonsojaen@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-17  9:52:33
