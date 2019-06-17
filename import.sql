-- MySQL Script generated by MySQL Workbench
-- Mon Jun 17 10:56:34 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tfg
-- -----------------------------------------------------
-- Proyecto

-- -----------------------------------------------------
-- Schema tfg
--
-- Proyecto
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tfg` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci ;
USE `tfg` ;

-- -----------------------------------------------------
-- Table `tfg`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Users` (
  `ID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(50) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  `Enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Authorities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Authorities` (
  `ID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `User_ID` SMALLINT UNSIGNED NOT NULL,
  `Authority` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_Authorities_Users`
    FOREIGN KEY (`User_ID`)
    REFERENCES `tfg`.`Users` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Clientes` (
  `ID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(100) NOT NULL,
  `Apellidos` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Created_At` DATE NOT NULL,
  `Foto` VARCHAR(50) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Facturas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Facturas` (
  `ID` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Descripcion` VARCHAR(150) NOT NULL,
  `Observacion` VARCHAR(200) NULL,
  `Created_At` DATE NOT NULL,
  `Cliente_ID` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_Facturas_Clientes_idx` (`Cliente_ID` ASC) VISIBLE,
  CONSTRAINT `FK_Facturas_Clientes`
    FOREIGN KEY (`Cliente_ID`)
    REFERENCES `tfg`.`Clientes` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Productos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Productos` (
  `ID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
  `Precio` DECIMAL(8,2) NOT NULL,
  `Created_At` DATE NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Facturas_Items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Facturas_Items` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Cantidad` TINYINT UNSIGNED NOT NULL,
  `Factura_ID` MEDIUMINT UNSIGNED NOT NULL,
  `Producto_ID` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_FI_Facturas_idx` (`Factura_ID` ASC) VISIBLE,
  INDEX `FK_FI_Productos_idx` (`Producto_ID` ASC) VISIBLE,
  CONSTRAINT `FK_FI_Facturas`
    FOREIGN KEY (`Factura_ID`)
    REFERENCES `tfg`.`Facturas` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_FI_Productos`
    FOREIGN KEY (`Producto_ID`)
    REFERENCES `tfg`.`Productos` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
