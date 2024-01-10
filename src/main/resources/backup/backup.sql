-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: attendance_sheet
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
  `admin_num` int NOT NULL AUTO_INCREMENT COMMENT '管理员账号',
  `admin_name` char(10) NOT NULL COMMENT '管理员名称',
  `admin_password` char(20) NOT NULL COMMENT '管理员密码',
  PRIMARY KEY (`admin_num`),
  KEY `admin_name_index` (`admin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员账号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'管理员1','admin123');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendances`
--

DROP TABLE IF EXISTS `attendances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendances` (
  `attendance_num` int NOT NULL AUTO_INCREMENT COMMENT '考勤编号',
  `worker_num` int NOT NULL COMMENT '员工号',
  `day_time` date NOT NULL COMMENT '日期',
  `arrival_time` time DEFAULT NULL COMMENT '上班时间',
  `leave_time` time DEFAULT NULL COMMENT '下班时间',
  PRIMARY KEY (`attendance_num`),
  KEY `workers_num_index` (`worker_num`),
  CONSTRAINT `attendence_worker_FK` FOREIGN KEY (`worker_num`) REFERENCES `workers` (`worker_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendances`
--

LOCK TABLES `attendances` WRITE;
/*!40000 ALTER TABLE `attendances` DISABLE KEYS */;
INSERT INTO `attendances` VALUES (1,1,'2023-06-03','07:50:00','18:10:00'),(2,2,'2023-06-02','07:54:00','18:01:00'),(3,1,'2023-09-21','22:22:13','23:00:57'),(4,1,'2023-09-22','00:03:25','00:03:54'),(5,1,'2023-10-01','08:30:00','18:00:00'),(6,1,'2023-10-02','08:40:00','18:00:00'),(7,1,'2023-10-03','08:30:00','17:45:00'),(8,1,'2023-10-04','08:30:00','18:00:00'),(9,1,'2023-10-05','08:30:00','17:15:00'),(10,1,'2023-10-06','08:40:00','18:00:00'),(11,1,'2023-10-07','08:30:00','18:00:00'),(12,1,'2023-10-09','08:35:00','18:00:00'),(13,1,'2023-10-10','08:30:00','18:00:00'),(14,1,'2023-10-11','08:30:00','18:00:00'),(15,1,'2023-10-12','08:20:00','18:10:00'),(16,1,'2023-10-13','08:50:00','18:00:00'),(19,1,'2023-10-16','08:30:00','18:00:00'),(20,1,'2023-10-17','08:30:00','17:50:00'),(21,1,'2023-10-18','08:30:00','18:00:00'),(24,1,'2023-10-21','08:30:00','18:00:00'),(25,1,'2023-10-22','08:30:00','17:45:00'),(26,1,'2023-10-23','08:30:00','18:00:00'),(30,1,'2023-10-27','08:30:00','18:00:00'),(31,1,'2023-10-28','08:30:00','18:30:00'),(32,1,'2023-10-29','08:30:00','18:00:00'),(33,1,'2023-10-30','08:40:00','18:00:00'),(34,1,'2023-10-31','08:30:00','18:00:00');
/*!40000 ALTER TABLE `attendances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `department_num` int NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `department_name` char(20) NOT NULL COMMENT '部门名称',
  `work_time` time NOT NULL COMMENT '上班时间',
  `closing_time` time NOT NULL COMMENT '下班时间',
  PRIMARY KEY (`department_num`),
  UNIQUE KEY `department_name` (`department_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES (1,'部门修改','08:30:00','18:00:00'),(2,'测试','05:00:00','18:00:00');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evection_infos`
--

DROP TABLE IF EXISTS `evection_infos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evection_infos` (
  `evection_num` int NOT NULL AUTO_INCREMENT COMMENT '出差编号',
  `worker_num` int NOT NULL COMMENT '员工编号',
  `reason` char(50) NOT NULL COMMENT '出差理由',
  `start_time` date NOT NULL COMMENT '起始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  `is_pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审批状态(0:未审批;1:不通过;2:通过)',
  PRIMARY KEY (`evection_num`),
  KEY `workers_num_index` (`worker_num`),
  CONSTRAINT `evection_worker_FK` FOREIGN KEY (`worker_num`) REFERENCES `workers` (`worker_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evection_infos`
--

LOCK TABLES `evection_infos` WRITE;
/*!40000 ALTER TABLE `evection_infos` DISABLE KEYS */;
INSERT INTO `evection_infos` VALUES (1,1,'出差理由1','2023-05-07','2023-05-14',1),(3,1,'出差','2023-10-24','2023-10-26',2),(4,1,'出差','2023-10-28','2023-11-03',1);
/*!40000 ALTER TABLE `evection_infos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_infos`
--

DROP TABLE IF EXISTS `leave_infos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_infos` (
  `leave_num` int NOT NULL AUTO_INCREMENT COMMENT '请假编号',
  `worker_num` int NOT NULL COMMENT '员工编号',
  `type` enum('病假','事假') NOT NULL COMMENT '请假类别',
  `reason` char(30) NOT NULL COMMENT '请假事由',
  `start_time` date NOT NULL COMMENT '起始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  `is_pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审批状态(0:未审批;1:不通过;2:通过)',
  PRIMARY KEY (`leave_num`),
  KEY `workers_num_index` (`worker_num`),
  CONSTRAINT `leave_worker_FK` FOREIGN KEY (`worker_num`) REFERENCES `workers` (`worker_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `leave_typeCK` CHECK ((`type` in (_utf8mb4'事假',_utf8mb4'病假')))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_infos`
--

LOCK TABLES `leave_infos` WRITE;
/*!40000 ALTER TABLE `leave_infos` DISABLE KEYS */;
INSERT INTO `leave_infos` VALUES (1,2,'病假','病假理由1','2023-06-01','2023-06-03',2),(4,1,'事假','家里有事','2023-10-14','2023-10-15',1),(5,1,'病假','感冒发烧','2023-10-19','2023-10-20',1);
/*!40000 ALTER TABLE `leave_infos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_attendances`
--

DROP TABLE IF EXISTS `monthly_attendances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthly_attendances` (
  `attendance_num` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `worker_num` int NOT NULL COMMENT '员工号',
  `month_time` date NOT NULL COMMENT '日期',
  `full_attendance` tinyint(1) NOT NULL COMMENT '是否全勤',
  `sick_leave` int NOT NULL COMMENT '病假次数',
  `general_leave` int NOT NULL COMMENT '事假次数',
  `evection_leave` int NOT NULL COMMENT '出差次数',
  `overtime` int NOT NULL COMMENT '加班时间',
  PRIMARY KEY (`attendance_num`),
  KEY `workers_num_index` (`worker_num`),
  CONSTRAINT `monthly_attendence_worker_FK` FOREIGN KEY (`worker_num`) REFERENCES `workers` (`worker_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `evection_leave_cheack` CHECK ((`evection_leave` >= 0)),
  CONSTRAINT `general_leave_cheack` CHECK ((`general_leave` >= 0)),
  CONSTRAINT `overtime_cheack` CHECK ((`overtime` >= 0)),
  CONSTRAINT `sick_leave_cheack` CHECK ((`sick_leave` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_attendances`
--

LOCK TABLES `monthly_attendances` WRITE;
/*!40000 ALTER TABLE `monthly_attendances` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_attendances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workers`
--

DROP TABLE IF EXISTS `workers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workers` (
  `worker_num` int NOT NULL AUTO_INCREMENT COMMENT '员工编号',
  `worker_name` char(20) NOT NULL COMMENT '员工姓名',
  `gender` enum('男','女') NOT NULL COMMENT '性别',
  `phone_number` char(11) NOT NULL COMMENT '电话号码',
  `salary` int NOT NULL COMMENT '工资',
  `department_num` int DEFAULT NULL COMMENT '部门编号',
  `password` char(20) NOT NULL DEFAULT '123456' COMMENT '密码',
  PRIMARY KEY (`worker_num`),
  KEY `worker_department_FK` (`department_num`),
  KEY `worker_name_index` (`worker_name`),
  CONSTRAINT `worker_department_FK` FOREIGN KEY (`department_num`) REFERENCES `departments` (`department_num`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workers`
--

LOCK TABLES `workers` WRITE;
/*!40000 ALTER TABLE `workers` DISABLE KEYS */;
INSERT INTO `workers` VALUES (1,'员工','男','1234',5000,2,'123456'),(2,'工作人员2','女','12345678913',4100,1,'1234567');
/*!40000 ALTER TABLE `workers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-26 20:57:00
