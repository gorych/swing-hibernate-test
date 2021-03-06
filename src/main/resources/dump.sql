-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: computerdetails
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Dumping data for table `cluster`
--

LOCK TABLES `cluster` WRITE;
/*!40000 ALTER TABLE `cluster` DISABLE KEYS */;
/*!40000 ALTER TABLE `cluster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `matrixtype`
--

LOCK TABLES `matrixtype` WRITE;
/*!40000 ALTER TABLE `matrixtype` DISABLE KEYS */;
INSERT INTO `matrixtype` VALUES (1,'Лучшая цветопередача; Cамый большой угол обзора; Высокая цена; Большое время отклика','IPS',3),(2,'Очень маленькое время отклика; Доступная цена; Плохая цветопередача; Маленький угол обзора; Высокая вероятность появления битых пикселей','TN+Film',1),(3,'Качественные цвета; Неплохое время отклика; Более высокая цена; Искажение цветов при большом угле обзора','VA',2),(4,'Более высокая плотность пикселей, высокая яркость, меньшее энергопотребление, низкий цветовой охват, более низкая контрастность по сравнению с IPS','PLS',0.1),(5,'Модификация матрицы для дисплеев с высоким разрешением (UHD), аналог H-IPS','AH-IPS',0),(6,'Cледующее поколение производства, уменьшение времени отклика, улучшение цветовой передачи.','VA(AMVA+)',0),(7,'Одно из улучшений IPS технологий увеличение яркости и уменьшение времени отклика','IPS(e-IPS)',0);
/*!40000 ALTER TABLE `matrixtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `monitor`
--

LOCK TABLES `monitor` WRITE;
/*!40000 ALTER TABLE `monitor` DISABLE KEYS */;
INSERT INTO `monitor` VALUES (1,21.5,36,0,'LG 22MP48HQ-P',224.25,1,1),(2,24,36,0,'BenQ XL2411Z',572.64,2,1),(3,18.5,24,0,'AOC e970Swn',142.65,1,2),(4,18.5,24,0,'Philips 193V5LSB2/10',147.45,2,2),(5,31.5,36,0,'NEC PA322UHD-BK-2',7306.87,1,3),(6,27,36,0,'Dell UltraSharp UP2715K',4976.21,1,4),(7,19.5,36,0,'BenQ GL2023A',158.50,2,5),(8,27,36,0,'Samsung C27F390FHI ',632.00,3,1),(9,22,24,0,'ViewSonic VX2276-smhd',346.55,1,1),(10,31.5,12,0,'Samsung C32F391FWI ',735.73,3,1),(11,34,36,0,'LG 34UC98-W',2128.30,1,6),(12,19,36,0,'BenQ BL912',308.78,2,7),(13,24,36,0,'Iiyama ProLite X2483HSU-B1',395.55,6,1),(14,23.6,24,0,'Samsung S24D590PL',374.38,4,1),(15,23.6,24,0,'Samsung T24D391EX',448.78,4,1),(16,23,24,0,'Philips 234E5QHSB/00',380.56,5,1),(17,27,24,0,'AOC i2769Vm',439.56,5,1),(18,21.5,36,0,'LG 22MP67HQ',230.10,5,1),(19,29.8,24,0,'NEC SpectraView Reference 302',6370.92,5,8),(20,31,36,0,'EIZO ColorEdge CG318-4K',10656.89,1,9),(21,17,12,0,'NEC MultiSync E171M',136.50,2,7);
/*!40000 ALTER TABLE `monitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `screenresolution`
--

LOCK TABLES `screenresolution` WRITE;
/*!40000 ALTER TABLE `screenresolution` DISABLE KEYS */;
INSERT INTO `screenresolution` VALUES (1,1920,1080),(2,1366,768),(3,3840,2160),(4,5120,2880),(5,1600,900),(6,3440,1440),(7,1280,1024),(8,2560,1600),(9,4096,2160);
/*!40000 ALTER TABLE `screenresolution` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-24 16:41:33
