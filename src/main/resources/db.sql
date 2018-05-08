-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: test.c3awvbftluot.us-east-2.rds.amazonaws.com    Database: reminder
-- ------------------------------------------------------
-- Server version	5.6.39-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_id` binary(16) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` binary(60) NOT NULL,
  `type` enum('doctor','patient') NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `account_id` binary(16) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `doctor_id_UNIQUE` (`account_id`),
  CONSTRAINT `doctor_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pair`
--

DROP TABLE IF EXISTS `pair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pair` (
  `pair_id` binary(16) NOT NULL,
  `doctor_id` binary(16) NOT NULL,
  `patient_id` binary(16) NOT NULL,
  `unfinished_hight` smallint(5) unsigned NOT NULL DEFAULT '0',
  `unfinished_middle` smallint(5) unsigned NOT NULL DEFAULT '0',
  `unfinished_low` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`pair_id`),
  UNIQUE KEY `pair_UNIQUE` (`doctor_id`,`patient_id`),
  UNIQUE KEY `pair_id_UNIQUE` (`pair_id`),
  KEY `doctor_fk_idx` (`doctor_id`),
  KEY `patient_fk_idx` (`patient_id`),
  CONSTRAINT `doctor_fk` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `account_id` binary(16) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `patient_id_UNIQUE` (`account_id`),
  CONSTRAINT `patient_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reminder`
--

DROP TABLE IF EXISTS `reminder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reminder` (
  `reminder_id` binary(16) NOT NULL,
  `pair_id` binary(16) NOT NULL,
  `description` text NOT NULL,
  `duration` tinyint(3) unsigned NOT NULL,
  `priority` enum('high','middle','low') NOT NULL,
  `finished` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reminder_id`),
  UNIQUE KEY `reminder_UNIQUE` (`reminder_id`),
  KEY `reminder_pair_idx` (`pair_id`),
  CONSTRAINT `reminder_pair` FOREIGN KEY (`pair_id`) REFERENCES `pair` (`pair_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-06 15:00:55
