CREATE DATABASE  IF NOT EXISTS `inventory` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `inventory`;
-- MySQL dump 10.13  Distrib 5.5.59, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: inventory
-- ------------------------------------------------------
-- Server version	5.5.59-0ubuntu0.14.04.1

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
-- Table structure for table `Authorities`
--

DROP TABLE IF EXISTS `Authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Authorities`
--

LOCK TABLES `Authorities` WRITE;
/*!40000 ALTER TABLE `Authorities` DISABLE KEYS */;
INSERT INTO `Authorities` VALUES (1,'ROLE_INDENT_FILLER'),(2,'ROLE_RECEIPT_CONSUMABLE'),(3,'ROLE_RECEIPT_NON_CONSUMABLE'),(4,'ROLE_ISSUE'),(5,'ROLE_STOCK_MANAGER'),(6,'ROLE_ADMIN'),(7,'ROLE_MMG'),(8,'ROLE_EMPLOYEE'),(9,'ROLE_FINANCE');
/*!40000 ALTER TABLE `Authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DtIndent`
--

DROP TABLE IF EXISTS `DtIndent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DtIndent` (
  `dtIndentId` int(11) NOT NULL AUTO_INCREMENT,
  `Remarks` varchar(255) DEFAULT NULL,
  `classificationNameManual` varchar(255) DEFAULT NULL,
  `descriptionOfMaterial` varchar(255) DEFAULT NULL,
  `expectedMonthYearOfDelivery` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `partType` varchar(255) DEFAULT NULL,
  `pricePerUnit` float DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `sNo` int(11) DEFAULT NULL,
  `unitForPrice` varchar(255) DEFAULT NULL,
  `classification` int(11) DEFAULT NULL,
  `hdIndent` int(11) DEFAULT NULL,
  `itemGroup` int(11) DEFAULT NULL,
  `indentId` int(11) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  `purchaseId` int(11) DEFAULT NULL,
  `suggestedVendors` varchar(255) DEFAULT NULL,
  `modeOfDispatch` varchar(255) DEFAULT NULL,
  `acceptedFlag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`dtIndentId`),
  KEY `FKr908vbbn3tk6lalt8ugub0i8l` (`indentId`),
  KEY `FKkwuo730y6bbu4qcrhb580d4yo` (`classification`),
  KEY `FK5d2yv9jdie7an7oqrjg3xl2th` (`hdIndent`),
  KEY `FKgbrta4fujy9lrdq0sc2ksr3km` (`itemId`),
  KEY `FK9yqgdl4brm4wluqfie66e84v8` (`itemGroup`),
  KEY `FKnhfm9xrwj9d8k3vopc1pxcm9s` (`purchaseId`)
) ENGINE=MyISAM AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DtIndent`
--

LOCK TABLES `DtIndent` WRITE;
/*!40000 ALTER TABLE `DtIndent` DISABLE KEYS */;
INSERT INTO `DtIndent` VALUES (11,'ok',NULL,'A4','2019-04-15','JK',NULL,50,1,NULL,NULL,2,1,9,NULL,2,2,'JK','Any',''),(10,'ok',NULL,'dell','2019-04-22','dell',NULL,20000,1,NULL,NULL,3,1,3,NULL,3,2,'dell','AIR',''),(12,'ok',NULL,'aa','2019-04-11','bb',NULL,10,2,NULL,NULL,1,2,1,NULL,1,1,'cc','Any',''),(13,'ok',NULL,'z','2019-04-11','y',NULL,56,5,NULL,NULL,1,3,1,NULL,1,1,'x','Any',''),(18,'',NULL,'sasasdvsdvdsvsdv','2019-04-24','',NULL,2222220,2,NULL,NULL,3,4,3,NULL,3,1,'','Any',''),(20,'',NULL,'fdsgds','2019-04-24','',NULL,40,2,NULL,NULL,2,5,9,NULL,2,1,'','Any',''),(21,'',NULL,'saccc','2019-04-24','',NULL,46,2,NULL,NULL,2,5,13,NULL,1,1,'','Any',''),(22,'',NULL,'scasc','2019-04-24','',NULL,NULL,2,NULL,NULL,3,5,3,NULL,3,1,'','0',''),(24,'',NULL,'fdsgds','','',NULL,NULL,2,NULL,NULL,2,6,9,NULL,2,1,'','Any',''),(25,'',NULL,'dd','','',NULL,NULL,2,NULL,NULL,1,7,1,NULL,1,1,'','0',''),(27,'',NULL,'232323','','',NULL,NULL,232,NULL,NULL,1,8,1,NULL,1,1,'','Any',''),(28,'',NULL,'wweqr','','',NULL,NULL,323,NULL,NULL,1,8,1,NULL,1,1,'','0',''),(29,'',NULL,'3232','','',NULL,NULL,3,NULL,NULL,1,8,1,NULL,1,1,'','0','\0'),(30,'',NULL,'3232','','',NULL,NULL,323,NULL,NULL,1,8,1,NULL,1,1,'','0',''),(31,'',NULL,'qwqwq','','',NULL,NULL,212,NULL,NULL,2,9,9,NULL,2,1,'','0',''),(35,'',NULL,'dsfd','','',NULL,NULL,43,NULL,NULL,1,10,1,NULL,1,1,'','Any',''),(34,'',NULL,'fsfds','','',NULL,NULL,43,NULL,NULL,1,10,1,NULL,1,1,'','Any','\0'),(37,'',NULL,'aaaaa','','',NULL,NULL,4,NULL,NULL,1,11,1,NULL,1,1,'','Any',''),(38,'',NULL,'bbbb','','',NULL,NULL,5,NULL,NULL,2,11,1,NULL,1,1,'','0','\0'),(39,'',NULL,'cccc','','',NULL,NULL,7,NULL,NULL,3,11,1,NULL,1,1,'','0','\0'),(51,'',NULL,'323','','',NULL,NULL,23,NULL,NULL,1,12,1,NULL,1,1,'','Any',''),(50,'',NULL,'323','','',NULL,NULL,44,NULL,NULL,1,12,1,NULL,1,1,'','Any',''),(49,'',NULL,'23','','',NULL,NULL,434,NULL,NULL,1,12,1,NULL,1,1,'','Any',''),(48,'',NULL,'323','','',NULL,NULL,33,NULL,NULL,1,12,1,NULL,1,1,'','Any',''),(47,'',NULL,'23323','','',NULL,NULL,4,NULL,NULL,1,12,1,NULL,1,1,'','Any',''),(46,'',NULL,'2323','','',NULL,NULL,3,NULL,NULL,1,12,1,NULL,1,1,'','Any','');
/*!40000 ALTER TABLE `DtIndent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeAuthorityLevel`
--

DROP TABLE IF EXISTS `EmployeeAuthorityLevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeAuthorityLevel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `authorityLevel` int(11) DEFAULT NULL,
  `authorizedEmployee` tinyblob,
  `dtEntryDate` datetime DEFAULT NULL,
  `dtModificationDate` datetime DEFAULT NULL,
  `employee` tinyblob,
  `authorizedEmployeeId` int(11) DEFAULT NULL,
  `employeeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4w9gk1lwl0247j4ag8ky73lnp` (`authorizedEmployeeId`),
  KEY `FK3r88i6u12en03onr243hyd6bs` (`employeeId`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeAuthorityLevel`
--

LOCK TABLES `EmployeeAuthorityLevel` WRITE;
/*!40000 ALTER TABLE `EmployeeAuthorityLevel` DISABLE KEYS */;
INSERT INTO `EmployeeAuthorityLevel` VALUES (1,'Y',1,NULL,'2019-03-29 11:45:20',NULL,NULL,3,10),(2,'Y',2,NULL,'2019-03-29 11:45:20',NULL,NULL,13,10),(3,'Y',3,NULL,'2019-03-29 11:45:30',NULL,NULL,11,10),(4,'Y',1,NULL,'2019-03-29 14:49:54',NULL,NULL,2,3),(5,'Y',2,NULL,'2019-03-29 14:49:54',NULL,NULL,1,3),(6,'Y',1,NULL,'2019-04-01 15:03:39',NULL,NULL,1,2),(7,'Y',1,NULL,'2019-04-11 15:59:56',NULL,NULL,24,25),(8,'Y',2,NULL,'2019-04-11 15:59:56',NULL,NULL,2,25),(9,'Y',3,NULL,'2019-04-11 15:59:56',NULL,NULL,1,25);
/*!40000 ALTER TABLE `EmployeeAuthorityLevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HdIndent`
--

DROP TABLE IF EXISTS `HdIndent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HdIndent` (
  `indentId` int(11) NOT NULL AUTO_INCREMENT,
  `budgetYear` varchar(255) DEFAULT NULL,
  `indentDate` date DEFAULT NULL,
  `indentorAuthenticationDate` date DEFAULT NULL,
  `indentorAuthenticationFlag` char(1) DEFAULT NULL,
  `prNo` varchar(255) DEFAULT NULL,
  `previousReferenceOfPurchaseIfAny` varchar(255) DEFAULT NULL,
  `projectCode` varchar(255) DEFAULT NULL,
  `sourceData` varchar(255) DEFAULT NULL,
  `suggestedVendors` varchar(255) DEFAULT NULL,
  `approvingAuthority` int(11) DEFAULT NULL,
  `indentor` int(11) DEFAULT NULL,
  `sectionHead` int(11) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `financeRemarks` varchar(255) DEFAULT NULL,
  `financeStatus` varchar(255) DEFAULT NULL,
  `specialApprovalRemark` varchar(255) DEFAULT NULL,
  `submittedStatus` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`indentId`),
  KEY `FKsvehxpu3dne1ggmrbr2b0q6gp` (`approvingAuthority`),
  KEY `FKpvn7vcc3p6cupe5rmit38mmod` (`sectionHead`),
  KEY `FK2wdjtoaeb93wqjm5lxdurqj2u` (`indentor`),
  KEY `FKiw4b6ekq1yutnrd8o0cjxoufx` (`projectId`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HdIndent`
--

LOCK TABLES `HdIndent` WRITE;
/*!40000 ALTER TABLE `HdIndent` DISABLE KEYS */;
INSERT INTO `HdIndent` VALUES (1,'2018-2019','2019-04-10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,3,'Approved','no fund','Not acceptable','imp','Submitted'),(2,'2018-2019','2019-04-10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,1,'Approved','accept','Acceptable',NULL,'Submitted'),(3,'2019-2020','2019-04-10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,1,'Approved','a','Acceptable',NULL,'Submitted'),(4,'2018-2019','2024-11-30',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,1,'Rejected',NULL,NULL,NULL,'Submitted'),(5,'2018-2019','2019-04-10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,2,'Approved','OK','Acceptable',NULL,'Submitted'),(6,'2018-2019','2019-04-11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,1,'Not submitted',NULL,NULL,NULL,'Not submitted'),(7,'2018-2019','2019-04-10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,1,'In process for finance approval',NULL,NULL,NULL,'Submitted'),(8,'2018-2019','2019-04-19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,2,'Approved','fsdgdfgf','Not acceptable','fdsgfd','Submitted'),(9,'2018-2019','2019-04-19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,1,'Rejected','fin remark','Not acceptable','I allow','Submitted'),(10,'2018-2019','2019-04-19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,2,'Approved','dsd','Acceptable',NULL,'Submitted'),(11,'2018-2019','2019-04-11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,1,'Approved','sdfsdf','Acceptable',NULL,'Submitted'),(12,'2018-2019','2019-04-19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,25,NULL,2,'In process',NULL,NULL,NULL,'Submitted');
/*!40000 ALTER TABLE `HdIndent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IndentStatus`
--

DROP TABLE IF EXISTS `IndentStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IndentStatus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `dtEntryDate` datetime DEFAULT NULL,
  `dtModificationDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `authorizedEmployeeId` int(11) DEFAULT NULL,
  `indentId` int(11) DEFAULT NULL,
  `Remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKov9yhg4y3bjeykuu6jpbyrfs7` (`authorizedEmployeeId`),
  KEY `FK5ijbx3pl8tflojaldbmo00sn7` (`indentId`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IndentStatus`
--

LOCK TABLES `IndentStatus` WRITE;
/*!40000 ALTER TABLE `IndentStatus` DISABLE KEYS */;
INSERT INTO `IndentStatus` VALUES (1,'Y','2019-04-10 12:06:36',NULL,'Approved',2,1,NULL),(2,'Y','2019-04-10 12:12:57',NULL,'Approved',1,1,NULL),(3,'Y','2019-04-10 12:49:12',NULL,'Approved',1,2,NULL),(4,'Y','2019-04-10 13:52:26',NULL,'Approved',1,3,NULL),(5,'Y','2019-04-10 14:12:35',NULL,'Rejected',2,4,NULL),(6,'Y','2019-04-10 14:18:13',NULL,'Approved',2,5,NULL),(7,'Y','2019-04-10 14:19:53',NULL,'Approved',1,5,NULL),(8,'Y','2019-04-11 11:34:02',NULL,'Approved',2,7,NULL),(9,'Y','2019-04-11 11:36:35',NULL,'Approved',2,8,NULL),(10,'Y','2019-04-11 12:34:00',NULL,'Approved',1,8,'dasdasd'),(11,'Y','2019-04-11 12:45:34',NULL,'Approved',2,9,'dasdasddddsada'),(12,'Y','2019-04-11 12:56:45',NULL,'Rejected',1,9,'I Dont Allow'),(13,'Y','2019-04-11 14:43:52',NULL,'Approved',2,10,'mera man nahi hai'),(14,'Y','2019-04-11 14:50:49',NULL,'Approved',1,10,'le kha le tu'),(15,'Y','2019-04-11 15:19:29',NULL,'Approved',2,11,'no fund'),(16,'Y','2019-04-11 15:21:12',NULL,'Approved',1,11,'gfrg');
/*!40000 ALTER TABLE `IndentStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Issue`
--

DROP TABLE IF EXISTS `Issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Issue` (
  `issueId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `approvedBy` varchar(255) DEFAULT NULL,
  `checkedBy` varchar(255) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  `projectCode` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `receivedBy` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `verified` varchar(255) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `approvedStatus` char(1) DEFAULT NULL,
  `checkedStatus` char(1) DEFAULT NULL,
  PRIMARY KEY (`issueId`),
  KEY `FKj11dtvlign4l8segweongnsnr` (`itemId`),
  KEY `FKns9owhvk7vhjumrs62xhkdnbq` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Issue`
--

LOCK TABLES `Issue` WRITE;
/*!40000 ALTER TABLE `Issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `Issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ItemMaster`
--

DROP TABLE IF EXISTS `ItemMaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ItemMaster` (
  `itemId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOFModification` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `classificationId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `stock_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemId`),
  KEY `FKjlck264rrylm4ppffforptavc` (`classificationId`),
  KEY `FKp4b46nxx8gmo5o4gqla8c4gu6` (`groupId`),
  KEY `FKb2hw4lsmixfycbtdwauy8xy3p` (`stock_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ItemMaster`
--

LOCK TABLES `ItemMaster` WRITE;
/*!40000 ALTER TABLE `ItemMaster` DISABLE KEYS */;
INSERT INTO `ItemMaster` VALUES (1,'Y','2019-03-22 14:12:23',NULL,'','other',NULL,NULL,NULL,1,NULL),(2,'Y','2019-03-27 15:26:58',NULL,'Printing paper','A4 Size Paper pack',NULL,NULL,NULL,9,NULL),(3,'Y','2019-03-27 15:27:38',NULL,'Dell i7 16 gb Ram ','Dell Desktop',NULL,NULL,NULL,3,NULL);
/*!40000 ALTER TABLE `ItemMaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ItemMaster_Issue`
--

DROP TABLE IF EXISTS `ItemMaster_Issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ItemMaster_Issue` (
  `ItemMaster_itemId` int(11) NOT NULL,
  `issueList_issueId` int(11) NOT NULL,
  UNIQUE KEY `UK_f6olsm7rj8kod9nx7uux47tsy` (`issueList_issueId`),
  KEY `FKbe5d8im90kox0mfdsdeetvp6r` (`ItemMaster_itemId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ItemMaster_Issue`
--

LOCK TABLES `ItemMaster_Issue` WRITE;
/*!40000 ALTER TABLE `ItemMaster_Issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `ItemMaster_Issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ItemMaster_Receipt`
--

DROP TABLE IF EXISTS `ItemMaster_Receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ItemMaster_Receipt` (
  `ItemMaster_itemId` int(11) NOT NULL,
  `receiptList_id` int(11) NOT NULL,
  UNIQUE KEY `UK_lb7lxfcpk92n0k8qf5lslsl70` (`receiptList_id`),
  KEY `FK31gccfhdq80c1rngt6gxe02hy` (`ItemMaster_itemId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ItemMaster_Receipt`
--

LOCK TABLES `ItemMaster_Receipt` WRITE;
/*!40000 ALTER TABLE `ItemMaster_Receipt` DISABLE KEYS */;
/*!40000 ALTER TABLE `ItemMaster_Receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Menu`
--

DROP TABLE IF EXISTS `Menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Menu` (
  `menuId` int(11) NOT NULL AUTO_INCREMENT,
  `MenuGroup` int(11) DEFAULT NULL,
  `authorityId` int(11) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `menuName` varchar(255) DEFAULT NULL,
  `orederNo` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`menuId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Menu`
--

LOCK TABLES `Menu` WRITE;
/*!40000 ALTER TABLE `Menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `Menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MenuGroup`
--

DROP TABLE IF EXISTS `MenuGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MenuGroup` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `enabled` int(11) DEFAULT NULL,
  `menuGroupName` varchar(255) DEFAULT NULL,
  `orederNo` int(11) DEFAULT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MenuGroup`
--

LOCK TABLES `MenuGroup` WRITE;
/*!40000 ALTER TABLE `MenuGroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `MenuGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MstGroup`
--

DROP TABLE IF EXISTS `MstGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MstGroup` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `groupName` varchar(255) DEFAULT NULL,
  `valueType` varchar(255) DEFAULT NULL,
  `classificationId` int(11) DEFAULT NULL,
  `dtEntryDate` datetime DEFAULT NULL,
  `dtModificationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`groupId`),
  KEY `FK2m2fj45jj6ecc1hren9tn7al9` (`classificationId`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MstGroup`
--

LOCK TABLES `MstGroup` WRITE;
/*!40000 ALTER TABLE `MstGroup` DISABLE KEYS */;
INSERT INTO `MstGroup` VALUES (1,'Y','Other','O',1,NULL,NULL),(2,'Y','Plant and Machinery','N',3,NULL,NULL),(3,'Y','Elect. Equipment','N',3,NULL,NULL),(4,'Y','Tools Testing Equipment','N',3,NULL,NULL),(5,'Y','Office Equipment','N',3,NULL,NULL),(6,'Y','Furniture,fixtures','N',3,NULL,NULL),(7,'Y','Misc. Items','N',3,NULL,NULL),(8,'Y','Raw Materials','C',2,NULL,NULL),(9,'Y','Stationery Items','C',2,NULL,NULL),(10,'Y','Components','C',2,NULL,NULL),(11,'Y','Maintenance','C',2,'2011-11-11 11:11:00',NULL),(12,'Y','Small Tools','C',2,NULL,NULL),(13,'Y','Misc. Items','C',2,NULL,NULL);
/*!40000 ALTER TABLE `MstGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MstRole`
--

DROP TABLE IF EXISTS `MstRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MstRole` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(255) NOT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MstRole`
--

LOCK TABLES `MstRole` WRITE;
/*!40000 ALTER TABLE `MstRole` DISABLE KEYS */;
INSERT INTO `MstRole` VALUES (1,'Admin'),(2,'MMG'),(3,'Employee'),(4,'Finance');
/*!40000 ALTER TABLE `MstRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MstUser`
--

DROP TABLE IF EXISTS `MstUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MstUser` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `addressCity` varchar(255) DEFAULT NULL,
  `addressCountry` varchar(255) DEFAULT NULL,
  `addressLine1` varchar(255) DEFAULT NULL,
  `addressLine2` varchar(255) DEFAULT NULL,
  `addressState` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `dateOfModification` date DEFAULT NULL,
  `dateofEntry` date DEFAULT NULL,
  `departmentId` int(11) DEFAULT NULL,
  `emailVerifiedFlag` char(1) DEFAULT NULL,
  `modifiedBy` int(11) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `registeredBy` int(11) DEFAULT NULL,
  `siteId` int(11) DEFAULT NULL,
  `userContactNo` varchar(10) NOT NULL,
  `userEmail` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `userType` int(11) NOT NULL,
  `userEmployeeId` varchar(255) DEFAULT NULL,
  `authority_userId` int(11) DEFAULT NULL,
  `hod_userId` int(11) DEFAULT NULL,
  `designationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  KEY `FKhysa2o1yv9us6ma1qtykl51c6` (`authority_userId`),
  KEY `FKs2jkhaxav7xis1ptl4708oqro` (`hod_userId`),
  KEY `FKkecmyj6wgj5woxojy13ildw4l` (`departmentId`),
  KEY `FKddx6nasjqri9aucp963e36bxn` (`designationId`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MstUser`
--

LOCK TABLES `MstUser` WRITE;
/*!40000 ALTER TABLE `MstUser` DISABLE KEYS */;
INSERT INTO `MstUser` VALUES (1,'Y',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'$2a$10$gjztUApSJXpB1mKNo3C9euN9trRw29TEAVDeTbEk.lIClVCQHs0sC',NULL,NULL,'','','Admin User',1,'E0023',NULL,3,NULL),(2,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-03',NULL,1,'N',2,'$2a$10$gjztUApSJXpB1mKNo3C9euN9trRw29TEAVDeTbEk.lIClVCQHs0sC',NULL,NULL,'9012345678','quijoteuvaachf@gmail.com','IM User',2,'E99',NULL,13,1),(3,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-03',NULL,1,'N',3,'$2a$10$gjztUApSJXpB1mKNo3C9euN9trRw29TEAVDeTbEk.lIClVCQHs0sC',NULL,NULL,'1214521212','weewe@gmail.comeew','Employee',3,'E003',NULL,3,1),(23,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-02','2019-04-02',NULL,NULL,23,'$2a$10$ioARb2Lk1eN56pn3hpCapeVCB1lbFdw8ApAuHlYa27z.5nfrRPu6i',1,NULL,'9870750833','goel.shivani91@gmail.com','MMG user',2,'E212',NULL,NULL,NULL),(24,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-02','2019-04-02',NULL,NULL,24,'$2a$10$0GUc8jCc0.Nqfi/K5vJoHO0a73OH2Uqdc1nkxZ0HQhXAoTx0Mh3/2',1,NULL,'9999999988','anujashridhddar@gmail.com','Mae hu finance',4,'E213',NULL,NULL,NULL),(25,'Y',NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-11','2019-04-11',1,NULL,25,'$2a$10$CA4aitqNy2XOJy9wlruyQ.Ltvo1npZr8byjp5bDwCxhvFrBq9kN2q',1,NULL,'3232323867','anuj@hijk.jj','FInance Ka Assistant',3,'25',NULL,NULL,1);
/*!40000 ALTER TABLE `MstUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Receipt`
--

DROP TABLE IF EXISTS `Receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Receipt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `billDate` datetime DEFAULT NULL,
  `billNo` int(11) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfEntryModification` datetime DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `receivedFrom` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkqwt26set56lcjg429pvqk1ty` (`itemId`),
  KEY `FKggt9rjolqxjf5iejnqfjs6igt` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Receipt`
--

LOCK TABLES `Receipt` WRITE;
/*!40000 ALTER TABLE `Receipt` DISABLE KEYS */;
/*!40000 ALTER TABLE `Receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReceiptConsumable`
--

DROP TABLE IF EXISTS `ReceiptConsumable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReceiptConsumable` (
  `receiptId` int(11) NOT NULL AUTO_INCREMENT,
  `acceptedQuantity` double DEFAULT NULL,
  `activeFlag` char(1) DEFAULT NULL,
  `approvedBy` varchar(255) DEFAULT NULL,
  `carrier` varchar(255) DEFAULT NULL,
  `challanDate` datetime DEFAULT NULL,
  `challanNo` int(11) DEFAULT NULL,
  `checkedBy` varchar(255) DEFAULT NULL,
  `clearance` varchar(255) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  `invoiceNumber` int(11) DEFAULT NULL,
  `mrrNumber` int(11) DEFAULT NULL,
  `pODate` datetime DEFAULT NULL,
  `pONumber` int(11) DEFAULT NULL,
  `projectCode` varchar(255) DEFAULT NULL,
  `purchaseType` varchar(255) DEFAULT NULL,
  `receiptType` varchar(255) DEFAULT NULL,
  `receivedBy` varchar(255) DEFAULT NULL,
  `receivedQuantity` double DEFAULT NULL,
  `rejectedQuantity` double DEFAULT NULL,
  `supplierName` varchar(255) DEFAULT NULL,
  `verified` varchar(255) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`receiptId`),
  KEY `FKjmmrrgr2duirvpjowkvc0ni71` (`itemId`),
  KEY `FK6qwyeev1ycm1pm726gsivst06` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReceiptConsumable`
--

LOCK TABLES `ReceiptConsumable` WRITE;
/*!40000 ALTER TABLE `ReceiptConsumable` DISABLE KEYS */;
/*!40000 ALTER TABLE `ReceiptConsumable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RoleAuthorities`
--

DROP TABLE IF EXISTS `RoleAuthorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RoleAuthorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `authorityId` int(11) DEFAULT NULL,
  `createdBy` int(11) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `modifiedBy` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoleAuthorities`
--

LOCK TABLES `RoleAuthorities` WRITE;
/*!40000 ALTER TABLE `RoleAuthorities` DISABLE KEYS */;
INSERT INTO `RoleAuthorities` VALUES (1,'2011-11-11 11:11:00','2011-11-11 11:11:00',1,0,'',0,3),(2,'2011-11-11 11:11:00','2011-11-11 11:11:00',1,0,'',0,1),(3,'2011-11-11 11:11:00','2011-11-11 11:11:00',2,0,'',0,2),(4,'2011-11-11 11:11:00','2011-11-11 11:11:00',2,0,'',0,1),(5,'2011-11-11 11:11:00','2011-11-11 11:11:00',3,0,'',0,2),(6,'2011-11-11 11:11:00','2011-11-11 11:11:00',3,0,'',0,1),(7,'2011-11-11 11:11:00','2011-11-11 11:11:00',4,NULL,'',NULL,2),(8,NULL,NULL,4,NULL,'',NULL,1),(9,NULL,NULL,5,NULL,'',NULL,2),(10,NULL,NULL,5,NULL,'',0,1),(11,NULL,NULL,6,NULL,NULL,NULL,1),(12,NULL,NULL,7,NULL,NULL,NULL,2),(13,NULL,NULL,8,NULL,NULL,NULL,3),(14,NULL,NULL,9,NULL,NULL,NULL,4);
/*!40000 ALTER TABLE `RoleAuthorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Stock`
--

DROP TABLE IF EXISTS `Stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `availableQty` double DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  `issuedQty` int(11) DEFAULT NULL,
  `others` varchar(255) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsv536wqiosw5b3locodhuapy8` (`itemId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stock`
--

LOCK TABLES `Stock` WRITE;
/*!40000 ALTER TABLE `Stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `Stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tocken`
--

DROP TABLE IF EXISTS `Tocken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tocken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disabledDate` datetime DEFAULT NULL,
  `generatedDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `tocken` varchar(255) DEFAULT NULL,
  `typeOfTocken` varchar(255) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `validFlag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tocken`
--

LOCK TABLES `Tocken` WRITE;
/*!40000 ALTER TABLE `Tocken` DISABLE KEYS */;
INSERT INTO `Tocken` VALUES (1,NULL,'2019-04-11 16:00:23',NULL,'V','TzeH6K','Password Reset Tocken',25,'Y');
/*!40000 ALTER TABLE `Tocken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserRole`
--

DROP TABLE IF EXISTS `UserRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enabled` char(1) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqtx3fiqcolbouartrbhamc7gr` (`userId`),
  KEY `FKsbt5snw2nep91w2g5lxjn4yqb` (`roleId`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRole`
--

LOCK TABLES `UserRole` WRITE;
/*!40000 ALTER TABLE `UserRole` DISABLE KEYS */;
INSERT INTO `UserRole` VALUES (1,'1',1,1),(2,'1',2,2),(3,'1',3,3),(43,'1',3,23),(44,'1',2,23),(45,'1',3,24),(46,'1',4,24),(47,'1',3,2),(48,'1',3,1),(49,'1',3,25);
/*!40000 ALTER TABLE `UserRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classification`
--

DROP TABLE IF EXISTS `classification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `classification` (
  `classificationId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `classification` varchar(255) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  PRIMARY KEY (`classificationId`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classification`
--

LOCK TABLES `classification` WRITE;
/*!40000 ALTER TABLE `classification` DISABLE KEYS */;
INSERT INTO `classification` VALUES (1,'Y','other',NULL,NULL),(2,'Y','Consumable','2019-03-15 15:07:28',NULL),(3,'Y','Non-Consumable','2019-03-15 15:07:36','2019-03-18 12:13:58');
/*!40000 ALTER TABLE `classification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `departmentId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  `departmentName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`departmentId`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Y',NULL,NULL,'Human Resource');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `designation`
--

DROP TABLE IF EXISTS `designation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `designation` (
  `designationId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  `designationName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`designationId`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designation`
--

LOCK TABLES `designation` WRITE;
/*!40000 ALTER TABLE `designation` DISABLE KEYS */;
INSERT INTO `designation` VALUES (1,'Y',NULL,NULL,'SLA');
/*!40000 ALTER TABLE `designation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `projectId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `projectCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`projectId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Y',NULL,'2019-03-19 11:29:36','Leprosy eradication Program','PRLP89'),(2,'Y',NULL,NULL,'MCTS','MC456'),(3,'Y',NULL,NULL,'TKDL','TK123');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchaseType`
--

DROP TABLE IF EXISTS `purchaseType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchaseType` (
  `purchaseId` int(11) NOT NULL AUTO_INCREMENT,
  `activeFlag` char(1) DEFAULT NULL,
  `dateOfEntry` datetime DEFAULT NULL,
  `dateOfModification` datetime DEFAULT NULL,
  `purchaseName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`purchaseId`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaseType`
--

LOCK TABLES `purchaseType` WRITE;
/*!40000 ALTER TABLE `purchaseType` DISABLE KEYS */;
INSERT INTO `purchaseType` VALUES (1,'Y',NULL,'2019-03-25 14:26:56','General'),(2,'Y',NULL,'2019-03-25 11:53:37','Specific brand'),(4,'Y',NULL,NULL,'Proprietary');
/*!40000 ALTER TABLE `purchaseType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'inventory'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-12 11:48:07
