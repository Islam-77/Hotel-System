-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hotel_db
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `booking_packages`
--

DROP TABLE IF EXISTS `booking_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_packages` (
  `package_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `discount_percentage` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_packages`
--

LOCK TABLES `booking_packages` WRITE;
/*!40000 ALTER TABLE `booking_packages` DISABLE KEYS */;
INSERT INTO `booking_packages` VALUES (1,'Honeymoon Special 676','Romantic Getaway',0.00,10.00),(2,'VIP Package 1632','All Inclusive',0.00,15.00),(3,'VIP Package 1022','All Inclusive',0.00,15.00),(4,'VIP Package 1382','All Inclusive',0.00,15.00);
/*!40000 ALTER TABLE `booking_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `guest_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `package_id` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`booking_id`),
  KEY `guest_id` (`guest_id`),
  KEY `room_id` (`room_id`),
  KEY `package_id` (`package_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`customer_id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
  CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`package_id`) REFERENCES `booking_packages` (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (2,7,1022,3,'2025-12-01 17:15:22',NULL,500.00,1),(3,8,1382,4,'2025-12-01 17:17:22',NULL,500.00,1),(4,7,105,1,'2025-12-06 10:23:49','2025-12-11 10:23:49',1575.00,1),(5,7,1022,2,'2025-12-06 10:36:33','2025-12-08 10:36:33',850.00,1),(6,1,9332,NULL,'2025-12-06 12:52:02','2025-12-07 12:52:02',500.00,1),(7,5,105,NULL,'2025-12-06 13:11:07','2025-12-07 13:11:07',350.00,1),(8,5,105,NULL,'2025-12-06 13:19:33','2025-12-07 13:19:33',350.00,1),(9,5,8090,NULL,'2025-12-06 13:20:30','2025-12-07 13:20:30',500.00,1);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_rentals`
--

DROP TABLE IF EXISTS `car_rentals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_rentals` (
  `rental_id` int(11) NOT NULL AUTO_INCREMENT,
  `guest_id` int(11) DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`rental_id`),
  KEY `guest_id` (`guest_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `car_rentals_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`customer_id`),
  CONSTRAINT `car_rentals_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_rentals`
--

LOCK TABLES `car_rentals` WRITE;
/*!40000 ALTER TABLE `car_rentals` DISABLE KEYS */;
INSERT INTO `car_rentals` VALUES (1,6,4,'2025-12-01 17:11:12',NULL,400.00,1),(2,7,5,'2025-12-01 17:15:22',NULL,400.00,1),(3,8,6,'2025-12-01 17:17:22',NULL,400.00,1),(4,5,1,'2025-12-06 11:18:00','2025-12-11 11:18:00',750.00,1),(5,1,3,'2025-12-06 12:51:22','2025-12-07 12:51:22',200.00,1),(6,5,1,'2025-12-06 13:11:23','2025-12-07 13:11:23',150.00,1),(7,5,2,'2025-12-06 13:11:57','2025-12-07 13:11:57',150.00,1);
/*!40000 ALTER TABLE `car_rentals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `model` varchar(100) DEFAULT NULL,
  `price_per_day` decimal(10,2) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,'BMW X5',150.00,0),(2,'BMW X5',150.00,0),(3,'Tesla Model S -676',200.00,0),(4,'Ferrari 488 -1632',400.00,0),(5,'Ferrari 488 -1022',400.00,0),(6,'Ferrari 488 -1382',400.00,0);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salary` decimal(10,2) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Sarah 1632','sarah1632@hotel.com','456','pass',3000.00,'RECEPTIONIST'),(2,'Mike 1632','mike1632@hotel.com','789','pass',2500.00,'HOUSEKEEPING'),(3,'Sarah 1022','sarah1022@hotel.com','456','pass',3000.00,'RECEPTIONIST'),(4,'Mike 1022','mike1022@hotel.com','789','pass',2500.00,'HOUSEKEEPING'),(5,'Sarah 1382','sarah1382@hotel.com','456','pass',3000.00,'RECEPTIONIST'),(6,'Mike 1382','mike1382@hotel.com','789','pass',2500.00,'HOUSEKEEPING'),(7,'Ahmed Admin','admin@hotel.com','1234567890','admin123',8000.00,'MANAGER'),(8,'Recep','recep@hotel.com','1234567890','user123',5000.00,'RECEPTIONIST'),(9,'Chef Gordon','chef@hotel.com','555-FOOD','chef123',4000.00,'RESTAURANT');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` VALUES (1,'John Doe','john@example.com','555-0199','123 Main St'),(3,'Test User','test8090@email.com','01000000','123 Test St'),(4,'Test User','test9332@email.com','01000000','123 Test St'),(5,'Mr. John Doe','john676@test.com','012345678','123 Test Blvd'),(6,'Mr. Bond','bond1632@mi6.com','007007','London'),(7,'Mr. Bond','bond1022@mi6.com','007007','London'),(8,'Mr. Bond','bond1382@mi6.com','007007','London');
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `guest_id` int(11) DEFAULT NULL,
  `issue_date` datetime DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `details` text DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `guest_id` (`guest_id`),
  CONSTRAINT `invoices_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,6,'2025-12-01 17:11:13',500.00,'=== Hotel Final Bill ===\nPayment ID: 5818\nPayment Method: Credit Card\n\nRoom Booking: $500.0\n\nTOTAL AMOUNT: $500.0\n========================='),(2,7,'2025-12-01 17:15:22',500.00,'=== Hotel Final Bill ===\nPayment ID: 5684\nPayment Method: Credit Card\n\nRoom Booking: $500.0\n\nTOTAL AMOUNT: $500.0\n========================='),(3,8,'2025-12-01 17:17:23',500.00,'=== Hotel Final Bill ===\nPayment ID: 4914\nPayment Method: Credit Card\n\nRoom Booking: $500.0\n\nTOTAL AMOUNT: $500.0\n========================='),(4,8,'2025-12-06 12:50:36',1000.00,'=== Hotel Final Bill ===\nPayment ID: 2325\nPayment Method: Credit Card\n\nRoom Booking: $500.0\nCar Rental: $400.0\nRestaurant Order: $100.0\n\nTOTAL AMOUNT: $1000.0\n========================='),(5,5,'2025-12-06 12:50:47',920.00,'=== Hotel Final Bill ===\nPayment ID: 2661\nPayment Method: Credit Card\n\nCar Rental: $750.0\nRestaurant Order: $170.0\n\nTOTAL AMOUNT: $920.0\n========================='),(6,1,'2025-12-06 12:53:37',750.00,'=== Hotel Final Bill ===\nPayment ID: 3377\nPayment Method: Credit Card\n\nRoom Booking: $500.0\nCar Rental: $200.0\nRestaurant Order: $50.0\n\nTOTAL AMOUNT: $750.0\n========================='),(7,5,'2025-12-06 13:11:36',500.00,'=== Hotel Final Bill ===\nPayment ID: 7515\nPayment Method: Cash\n\nRoom Booking: $350.0\nCar Rental: $150.0\n\nTOTAL AMOUNT: $500.0\n========================='),(8,5,'2025-12-06 13:12:10',500.00,'=== Hotel Final Bill ===\nPayment ID: 4895\nPayment Method: Credit Card\n\nRoom Booking: $350.0\nCar Rental: $150.0\n\nTOTAL AMOUNT: $500.0\n========================='),(9,5,'2025-12-06 13:19:41',350.00,'=== Hotel Final Bill ===\nPayment ID: 1538\nPayment Method: Credit Card\n\nRoom Booking: $350.0\n\nTOTAL AMOUNT: $350.0\n========================='),(10,5,'2025-12-06 13:20:35',500.00,'=== Hotel Final Bill ===\nPayment ID: 1121\nPayment Method: Credit Card\n\nRoom Booking: $500.0\n\nTOTAL AMOUNT: $500.0\n=========================');
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_items`
--

DROP TABLE IF EXISTS `menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `restaurant_id` int(11) DEFAULT NULL,
  `item_name` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `menu_items_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_items`
--

LOCK TABLES `menu_items` WRITE;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
INSERT INTO `menu_items` VALUES (1,1,'Lobster Thermidor 676',85.00),(2,1,'Steak',50.00),(3,1,'Steak',50.00),(4,1,'Steak',50.00),(5,1,'test',999.00);
/*!40000 ALTER TABLE `menu_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `item_name` varchar(100) DEFAULT NULL,
  `quantity` int(11) DEFAULT 1,
  `price_at_time_of_order` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `restaurant_orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,'Lobster Thermidor 676',1,85.00),(2,1,'Lobster Thermidor 676',1,85.00),(3,2,'Steak',1,50.00),(4,2,'Steak',1,50.00),(5,3,'Steak',1,50.00),(6,3,'Steak',1,50.00),(7,4,'Steak',1,50.00),(8,4,'Steak',1,50.00),(9,5,'test',1,999.00),(10,6,'Steak',1,50.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_services`
--

DROP TABLE IF EXISTS `package_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package_services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL,
  `service_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `package_id` (`package_id`),
  CONSTRAINT `package_services_ibfk_1` FOREIGN KEY (`package_id`) REFERENCES `booking_packages` (`package_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_services`
--

LOCK TABLES `package_services` WRITE;
/*!40000 ALTER TABLE `package_services` DISABLE KEYS */;
INSERT INTO `package_services` VALUES (1,1,'Breakfast'),(2,1,'Spa Access'),(3,1,'Airport Pickup'),(4,2,'Spa'),(5,2,'Drinks'),(6,3,'Spa'),(7,3,'Drinks'),(8,4,'Spa'),(9,4,'Drinks');
/*!40000 ALTER TABLE `package_services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `method` varchar(50) DEFAULT NULL,
  `invoice_details` text DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (3,3,500.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 4914\nPayment Method: Credit Card\n\nRoom Booking: $500.0\n\nTOTAL AMOUNT: $500.0\n========================='),(4,3,1000.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 2325\nPayment Method: Credit Card\n\nRoom Booking: $500.0\nCar Rental: $400.0\nRestaurant Order: $100.0\n\nTOTAL AMOUNT: $1000.0\n========================='),(5,NULL,920.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 2661\nPayment Method: Credit Card\n\nCar Rental: $750.0\nRestaurant Order: $170.0\n\nTOTAL AMOUNT: $920.0\n========================='),(6,NULL,0.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 5418\nPayment Method: Credit Card\n\n\nTOTAL AMOUNT: $0.0\n========================='),(7,6,750.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 3377\nPayment Method: Credit Card\n\nRoom Booking: $500.0\nCar Rental: $200.0\nRestaurant Order: $50.0\n\nTOTAL AMOUNT: $750.0\n========================='),(8,7,500.00,'Cash','=== Hotel Final Bill ===\nPayment ID: 7515\nPayment Method: Cash\n\nRoom Booking: $350.0\nCar Rental: $150.0\n\nTOTAL AMOUNT: $500.0\n========================='),(9,7,500.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 4895\nPayment Method: Credit Card\n\nRoom Booking: $350.0\nCar Rental: $150.0\n\nTOTAL AMOUNT: $500.0\n========================='),(10,NULL,0.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 105\nPayment Method: Credit Card\n\n\nTOTAL AMOUNT: $0.0\n========================='),(11,8,350.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 1538\nPayment Method: Credit Card\n\nRoom Booking: $350.0\n\nTOTAL AMOUNT: $350.0\n========================='),(12,9,500.00,'Credit Card','=== Hotel Final Bill ===\nPayment ID: 1121\nPayment Method: Credit Card\n\nRoom Booking: $500.0\n\nTOTAL AMOUNT: $500.0\n=========================');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_orders`
--

DROP TABLE IF EXISTS `restaurant_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `guest_id` int(11) DEFAULT NULL,
  `restaurant_id` int(11) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`order_id`),
  KEY `guest_id` (`guest_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `restaurant_orders_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`customer_id`),
  CONSTRAINT `restaurant_orders_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_orders`
--

LOCK TABLES `restaurant_orders` WRITE;
/*!40000 ALTER TABLE `restaurant_orders` DISABLE KEYS */;
INSERT INTO `restaurant_orders` VALUES (1,5,1,'2025-12-01 16:47:37',170.00,1),(2,6,1,'2025-12-01 17:11:13',100.00,1),(3,7,1,'2025-12-01 17:15:22',100.00,1),(4,8,1,'2025-12-01 17:17:22',100.00,1),(5,7,1,'2025-12-06 12:29:54',999.00,1),(6,1,1,'2025-12-06 12:52:37',50.00,1);
/*!40000 ALTER TABLE `restaurant_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurants` (
  `restaurant_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (1,'The Royal Feast');
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_assignments`
--

DROP TABLE IF EXISTS `room_assignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_assignments` (
  `assignment_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `assigned_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`assignment_id`),
  KEY `employee_id` (`employee_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `room_assignments_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`),
  CONSTRAINT `room_assignments_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_assignments`
--

LOCK TABLES `room_assignments` WRITE;
/*!40000 ALTER TABLE `room_assignments` DISABLE KEYS */;
INSERT INTO `room_assignments` VALUES (1,2,1632,'2025-12-01 17:11:13'),(2,4,1022,'2025-12-01 17:15:22'),(3,6,1382,'2025-12-01 17:17:22');
/*!40000 ALTER TABLE `room_assignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `room_id` int(11) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (105,'Suite',350.00,0),(322,'Single',4232.00,1),(500,'Single',500.00,1),(1022,'Suite',500.00,0),(1382,'Suite',500.00,1),(1632,'Suite',500.00,1),(1676,'Suite',450.00,1),(8090,'Deluxe',500.00,0),(9332,'Deluxe',500.00,0);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-06 13:27:11
