CREATE DATABASE  IF NOT EXISTS `202project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `202project`;
-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: 202project
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1823482,'Keyao.Huang'),(1823811,'Kai.Chen');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `apNo` int(11) NOT NULL AUTO_INCREMENT,
  `aptime` varchar(64) NOT NULL,
  `place` varchar(64) NOT NULL,
  `Reservation` int(11) NOT NULL,
  `CreatorID` int(11) NOT NULL,
  PRIMARY KEY (`apNo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,'12:00-12:30','bs/3/392',2,1823811),(2,'1:00-2:00','fb/1/115',6,1823482);
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legal_student_set`
--

DROP TABLE IF EXISTS `legal_student_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `legal_student_set` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legal_student_set`
--

LOCK TABLES `legal_student_set` WRITE;
/*!40000 ALTER TABLE `legal_student_set` DISABLE KEYS */;
INSERT INTO `legal_student_set` VALUES (1812345,'Bendan.Li'),(1823000,'Ling.Li'),(1823001,'Yi.Li'),(1823002,'Er.Li'),(1823003,'San.Li'),(1823004,'Si.Li'),(1823005,'Wu.Li'),(1823006,'Liu.Li'),(1823007,'Qi.Li'),(1823008,'Ba.Li'),(1823009,'Jiu.Li'),(1823010,'Shi.Li'),(1823482,'Keyao.Huang'),(1823486,'Tao.Ruan'),(1823811,'Kai.Chen');
/*!40000 ALTER TABLE `legal_student_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_table`
--

DROP TABLE IF EXISTS `plan_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_table` (
  `plan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_table`
--

LOCK TABLES `plan_table` WRITE;
/*!40000 ALTER TABLE `plan_table` DISABLE KEYS */;
INSERT INTO `plan_table` VALUES ('Wireframe prepare for new design'),('UI perfection testing for Mega Section'),('Finish PBI 3'),('Prepare for the presentation');
/*!40000 ALTER TABLE `plan_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `pwd` varchar(64) NOT NULL,
  `gender` varchar(8) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1823482,'Keyao.Huang','1823482',NULL,NULL,NULL,NULL,'badguy2021'),(1823811,'Kai.Chen','1823811','Male','18089999999','2441712556@qq.com','t013a197fb7ab08f77e.jpg.crdownload','badguy2021');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-30 21:56:54
