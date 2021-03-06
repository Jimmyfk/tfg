-- MySQL Script generated by MySQL Workbench
-- Mon Jul  8 11:10:51 2019
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
-- Table `tfg`.`Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Roles` (
  `ID` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Rol` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Rol_UNIQUE` (`Rol` ASC) )
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
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) )
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
  INDEX `FK_Facturas_Clientes_idx` (`Cliente_ID` ASC) ,
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
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Facturas_Items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Facturas_Items` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Cantidad` TINYINT UNSIGNED NOT NULL,
  `Precio` DECIMAL(8,2) NOT NULL,
  `Factura_ID` MEDIUMINT UNSIGNED NULL,
  `Producto_ID` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_FI_Facturas_idx` (`Factura_ID` ASC) ,
  INDEX `FK_FI_Productos_idx` (`Producto_ID` ASC) ,
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


-- -----------------------------------------------------
-- Table `tfg`.`Usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Usuarios` (
  `ID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(100) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  `Enabled` TINYINT NULL DEFAULT 1,
  `Cliente_id` SMALLINT UNSIGNED NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC),
  CONSTRAINT `FK_Usuarios_Clientes`
   FOREIGN KEY (`Cliente_id`)
   REFERENCES `tfg`.`Clientes` (`ID`)
   ON DELETE CASCADE
   ON UPDATE CASCADE )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Usuarios_Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Usuarios_Roles` (
  `ID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `User_ID` SMALLINT UNSIGNED NULL,
  `Rol_ID` TINYINT UNSIGNED NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Usuario_Rol_UNIQUE` (`User_ID` ASC, `Rol_ID` ASC) ,
  INDEX `FK_UR_Roles_idx` (`Rol_ID` ASC) ,
  CONSTRAINT `FK_UR_Usuarios`
    FOREIGN KEY (`User_ID`)
    REFERENCES `tfg`.`Usuarios` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_UR_Roles`
    FOREIGN KEY (`Rol_ID`)
    REFERENCES `tfg`.`Roles` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Privilegios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Privilegios` (
  `ID` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Privilegio` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Privilegio_UNIQUE` (`Privilegio` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tfg`.`Privilegios_Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfg`.`Privilegios_Roles` (
  `ID` SMALLINT NOT NULL AUTO_INCREMENT,
  `Rol_ID` TINYINT UNSIGNED NOT NULL,
  `Privilegio_ID` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Rol_Pivilegio_UNIQUE` (`Rol_ID` ASC, `Privilegio_ID` ASC) ,
  INDEX `FK_PR_Privilegios_idx` (`Privilegio_ID` ASC) ,
  CONSTRAINT `FK_PR_Roles`
    FOREIGN KEY (`Rol_ID`)
    REFERENCES `tfg`.`Roles` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PR_Privilegios`
    FOREIGN KEY (`Privilegio_ID`)
    REFERENCES `tfg`.`Privilegios` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
