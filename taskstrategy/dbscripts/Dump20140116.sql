CREATE DATABASE  IF NOT EXISTS `task_strategy` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `task_strategy`;
-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: 162.243.65.221    Database: task_strategy
-- ------------------------------------------------------
-- Server version	5.5.34-0ubuntu0.12.10.1

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
-- Table structure for table `PasswordReset`
--

DROP TABLE IF EXISTS `PasswordReset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PasswordReset` (
  `EMAIL` varchar(45) NOT NULL,
  `RESET_ID` varchar(45) NOT NULL,
  `CREATE_DATE` datetime NOT NULL,
  PRIMARY KEY (`EMAIL`,`RESET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `name` varchar(45) NOT NULL,
  `userId` varchar(45) NOT NULL,
  `favorite` bit(1) NOT NULL,
  `displayColor` varchar(45) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`name`,`userId`),
  KEY `FK_User_Id_idx` (`userId`),
  KEY `INDX_FAV` (`favorite`),
  CONSTRAINT `FK_User_Id_TaskTag` FOREIGN KEY (`userId`) REFERENCES `users` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task` (
  `id` varchar(45) NOT NULL,
  `userId` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` text,
  `priorityId` varchar(45) NOT NULL,
  `dueDate` timestamp NULL DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastModifiedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `complete` tinyint(1) DEFAULT '0',
  `passCount` int(11) DEFAULT NULL,
  `parentTaskId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Task_Priority_idx` (`priorityId`),
  KEY `FK_User_Id_idx` (`userId`),
  KEY `INDX_DUE_DATE` (`dueDate`),
  KEY `INDX_NAME` (`name`),
  CONSTRAINT `FK_Task_Priority` FOREIGN KEY (`priorityId`) REFERENCES `TaskPriority` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_User_Id` FOREIGN KEY (`userId`) REFERENCES `users` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TaskPriority`
--

DROP TABLE IF EXISTS `TaskPriority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaskPriority` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TaskTag`
--

DROP TABLE IF EXISTS `TaskTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaskTag` (
  `taskId` varchar(50) NOT NULL,
  `name` varchar(45) NOT NULL,
  `userId` varchar(45) NOT NULL,
  KEY `fk_TaskTag_1_idx` (`name`),
  KEY `fk_TaskTag_3_idx` (`taskId`),
  KEY `FK_Tag_UserId` (`userId`),
  CONSTRAINT `FK_Tag_Name` FOREIGN KEY (`name`) REFERENCES `Tag` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Tag_UserId` FOREIGN KEY (`userId`) REFERENCES `users` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TaskTag_TaskId` FOREIGN KEY (`taskId`) REFERENCES `Task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `USER_ID` varchar(45) NOT NULL,
  `USERNAME` varchar(45) NOT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `ENABLED` tinyint(1) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UNK_USERNAME` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-16 22:55:02
