CREATE DATABASE  IF NOT EXISTS `university` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `university`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: university
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `college` (
  `IDc` int NOT NULL AUTO_INCREMENT,
  `uName` varchar(100) NOT NULL,
  `uAddress` varchar(200) DEFAULT NULL,
  `uPhone` varchar(20) DEFAULT NULL,
  `uInfo` text,
  PRIMARY KEY (`IDc`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curator`
--

DROP TABLE IF EXISTS `curator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curator` (
  `IDc` int NOT NULL AUTO_INCREMENT,
  `IDco` int NOT NULL,
  `IDg` int DEFAULT NULL,
  `cSurname` varchar(100) DEFAULT NULL,
  `cName` varchar(100) DEFAULT NULL,
  `cPatronymic` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IDc`),
  KEY `IDco` (`IDco`),
  CONSTRAINT `curator_ibfk_1` FOREIGN KEY (`IDco`) REFERENCES `college` (`IDc`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades` (
  `IDgr` int NOT NULL AUTO_INCREMENT,
  `IDt` int NOT NULL,
  `IDp` int NOT NULL,
  `grSubject` varchar(100) DEFAULT NULL,
  `grDescription` varchar(255) DEFAULT NULL,
  `grValue` int DEFAULT NULL,
  `grDate` date DEFAULT NULL,
  `course` int NOT NULL DEFAULT '1',
  `semester` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`IDgr`),
  KEY `IDt` (`IDt`),
  KEY `IDp` (`IDp`),
  CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`IDt`) REFERENCES `teacher` (`IDt`),
  CONSTRAINT `grades_chk_1` CHECK ((`grValue` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group` (
  `IDg` int NOT NULL AUTO_INCREMENT,
  `IDsp` int NOT NULL,
  `IDc` int NOT NULL,
  `IDco` int DEFAULT NULL,
  `gName` varchar(50) DEFAULT NULL,
  `IDcurator` int DEFAULT NULL,
  PRIMARY KEY (`IDg`),
  KEY `IDsp` (`IDsp`),
  KEY `IDc` (`IDc`),
  KEY `group_ibfk_3` (`IDco`),
  CONSTRAINT `group_ibfk_1` FOREIGN KEY (`IDsp`) REFERENCES `speciality` (`IDsp`),
  CONSTRAINT `group_ibfk_2` FOREIGN KEY (`IDc`) REFERENCES `college` (`IDc`),
  CONSTRAINT `group_ibfk_3` FOREIGN KEY (`IDco`) REFERENCES `curator` (`IDc`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `performancesheet`
--

DROP TABLE IF EXISTS `performancesheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performancesheet` (
  `IDp` int NOT NULL AUTO_INCREMENT,
  `IDs` int NOT NULL,
  `pSemester` int DEFAULT NULL,
  `pAverage` decimal(3,2) DEFAULT NULL,
  PRIMARY KEY (`IDp`),
  KEY `IDs` (`IDs`),
  CONSTRAINT `performancesheet_ibfk_1` FOREIGN KEY (`IDs`) REFERENCES `students` (`IDs`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `IDr` int NOT NULL AUTO_INCREMENT,
  `IDg` int NOT NULL,
  `IDt` int NOT NULL,
  `rSubject` varchar(100) DEFAULT NULL,
  `rDate` date DEFAULT NULL,
  `rTime` time DEFAULT NULL,
  `rRoom` varchar(20) DEFAULT NULL,
  `teacherId` int DEFAULT NULL,
  `studentGroupId` int DEFAULT NULL,
  PRIMARY KEY (`IDr`),
  KEY `IDg` (`IDg`),
  KEY `IDt` (`IDt`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`IDg`) REFERENCES `group` (`IDg`),
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`IDt`) REFERENCES `teacher` (`IDt`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `speciality`
--

DROP TABLE IF EXISTS `speciality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `speciality` (
  `IDsp` int NOT NULL AUTO_INCREMENT,
  `IDc` int NOT NULL,
  `sName` varchar(100) NOT NULL,
  PRIMARY KEY (`IDsp`),
  KEY `IDc` (`IDc`),
  CONSTRAINT `speciality_ibfk_1` FOREIGN KEY (`IDc`) REFERENCES `college` (`IDc`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `IDs` int NOT NULL AUTO_INCREMENT,
  `IDg` int NOT NULL,
  `IDsp` int NOT NULL,
  `IDc` int NOT NULL,
  `sSurname` varchar(100) DEFAULT NULL,
  `sName` varchar(100) DEFAULT NULL,
  `sPatronymic` varchar(100) DEFAULT NULL,
  `sPhone` varchar(20) DEFAULT NULL,
  `sBirthday` date DEFAULT NULL,
  `IDsb` int DEFAULT NULL,
  `course` int DEFAULT '1',
  PRIMARY KEY (`IDs`),
  KEY `IDg` (`IDg`),
  KEY `IDsp` (`IDsp`),
  KEY `IDc` (`IDc`),
  KEY `fk_student_subject` (`IDsb`),
  CONSTRAINT `fk_student_subject` FOREIGN KEY (`IDsb`) REFERENCES `subject` (`IDsb`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`IDg`) REFERENCES `group` (`IDg`),
  CONSTRAINT `students_ibfk_2` FOREIGN KEY (`IDsp`) REFERENCES `speciality` (`IDsp`),
  CONSTRAINT `students_ibfk_3` FOREIGN KEY (`IDc`) REFERENCES `college` (`IDc`)
) ENGINE=InnoDB AUTO_INCREMENT=10038 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `IDsb` int NOT NULL AUTO_INCREMENT,
  `sbName` varchar(100) NOT NULL,
  `sbDescription` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IDsb`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `IDt` int NOT NULL AUTO_INCREMENT,
  `IDc` int DEFAULT NULL,
  `tSurname` varchar(100) DEFAULT NULL,
  `tName` varchar(100) DEFAULT NULL,
  `tPatronymic` varchar(100) DEFAULT NULL,
  `tPosition` varchar(100) DEFAULT NULL,
  `tPhone` varchar(20) DEFAULT NULL,
  `tBirthday` date DEFAULT NULL,
  `IDsb` int DEFAULT NULL,
  `curatorGroupId` int DEFAULT NULL,
  `room` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDt`),
  KEY `IDc` (`IDc`),
  KEY `fk_teacher_subject` (`IDsb`),
  KEY `fk_teacher_curatorGroup` (`curatorGroupId`),
  CONSTRAINT `fk_teacher_curatorGroup` FOREIGN KEY (`curatorGroupId`) REFERENCES `group` (`IDg`),
  CONSTRAINT `fk_teacher_subject` FOREIGN KEY (`IDsb`) REFERENCES `subject` (`IDsb`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`IDc`) REFERENCES `college` (`IDc`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher_group`
--

DROP TABLE IF EXISTS `teacher_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_group` (
  `teacher_id` int NOT NULL,
  `group_id` int NOT NULL,
  PRIMARY KEY (`teacher_id`,`group_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `teacher_group_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`IDt`) ON DELETE CASCADE,
  CONSTRAINT `teacher_group_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `group` (`IDg`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher_subject`
--

DROP TABLE IF EXISTS `teacher_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_subject` (
  `teacher_id` int NOT NULL,
  `subject_id` int NOT NULL,
  PRIMARY KEY (`teacher_id`,`subject_id`),
  KEY `subject_id` (`subject_id`),
  CONSTRAINT `teacher_subject_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`IDt`),
  CONSTRAINT `teacher_subject_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`IDsb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'university'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-04 13:52:58
