-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
SHOW WARNINGS;
-- -----------------------------------------------------
-- Schema realestate
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema realestate
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `realestate` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
SHOW WARNINGS;
USE `realestate` ;

-- -----------------------------------------------------
-- Table `realestate`.`admindetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `realestate`.`admindetails` (
  `adminid` INT NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `emailid` VARCHAR(50) NOT NULL,
  `contactnumber` INT NOT NULL,
  PRIMARY KEY (`adminid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `realestate`.`housedetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `realestate`.`housedetails` (
  `pid` INT NOT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `bathrooms` INT NULL DEFAULT NULL,
  `bedrooms` INT NULL DEFAULT NULL,
  `ownercontactnumber` VARCHAR(255) NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `size_sqft` INT NULL DEFAULT NULL,
  `image` BLOB NULL DEFAULT NULL,
  `img_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`pid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `realestate`.`pictures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `realestate`.`pictures` (
  `image_id` INT NOT NULL AUTO_INCREMENT,
  `image` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`image_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `realestate`.`purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `realestate`.`purchase` (
  `pid` INT NOT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `transaction_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`pid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `realestate`.`soldhouses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `realestate`.`soldhouses` (
  `pid` INT NOT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `bathrooms` INT NULL DEFAULT NULL,
  `bedrooms` INT NULL DEFAULT NULL,
  `ownercontactnumber` VARCHAR(255) NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `size_sqft` INT NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`pid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `realestate`.`userdetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `realestate`.`userdetails` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `emailid` VARCHAR(50) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `contactnumber` INT NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
