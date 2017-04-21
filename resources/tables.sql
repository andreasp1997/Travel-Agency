-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema travelagency
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema travelagency
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travelagency` DEFAULT CHARACTER SET utf8 ;
USE `travelagency` ;

-- -----------------------------------------------------
-- Table `travelagency`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(100) NOT NULL,
  `lastname` VARCHAR(100) NOT NULL,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(80) NOT NULL,
  `role` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`bookings` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `listing_id` INT NOT NULL,
  `from_date` DATE NOT NULL,
  `to_date` DATE NOT NULL,
  `seats` INT NOT NULL COMMENT 'Seats/Rooms',
  `created_at` DATE NOT NULL,
  PRIMARY KEY (`booking_id`, `user_id`, `listing_id`),
  INDEX `fk_bookings_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_bookings_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travelagency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`cities` (
  `city_id` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`city_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`airlines`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`airlines` (
  `airline_id` INT NOT NULL AUTO_INCREMENT,
  `airline` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`airline_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`flights`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`flights` (
  `flight_id` INT NOT NULL AUTO_INCREMENT,
  `airline_id` INT NOT NULL,
  `from` INT NOT NULL,
  `to` INT NOT NULL,
  `seats` INT NOT NULL,
  `price` INT NOT NULL,
  `take_off` DATE NOT NULL,
  PRIMARY KEY (`flight_id`, `airline_id`, `to`),
  INDEX `fk_flights_airlines1_idx` (`airline_id` ASC),
  INDEX `fk_flights_cities1_idx` (`to` ASC),
  CONSTRAINT `fk_flights_airlines1`
    FOREIGN KEY (`airline_id`)
    REFERENCES `travelagency`.`airlines` (`airline_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_flights_cities1`
    FOREIGN KEY (`to`)
    REFERENCES `travelagency`.`cities` (`city_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`cars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`cars` (
  `car_id` INT NOT NULL AUTO_INCREMENT,
  `car` VARCHAR(45) NOT NULL,
  `seats` INT NOT NULL,
  `location` INT NOT NULL,
  `price` DATE NOT NULL,
  PRIMARY KEY (`car_id`, `location`),
  INDEX `fk_cars_cities1_idx` (`location` ASC),
  CONSTRAINT `fk_cars_cities1`
    FOREIGN KEY (`location`)
    REFERENCES `travelagency`.`cities` (`city_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`cruises`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`cruises` (
  `cruise_id` INT NOT NULL AUTO_INCREMENT,
  `from` INT NOT NULL,
  `to` INT NOT NULL,
  `take_off` DATE NOT NULL,
  `rooms` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`cruise_id`, `to`),
  INDEX `fk_cruises_cities1_idx` (`to` ASC),
  CONSTRAINT `fk_cruises_cities1`
    FOREIGN KEY (`to`)
    REFERENCES `travelagency`.`cities` (`city_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`hotels`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`hotels` (
  `hotel_id` INT NOT NULL AUTO_INCREMENT,
  `hotel_name` VARCHAR(100) NOT NULL,
  `location` INT NOT NULL,
  `rooms` INT NOT NULL,
  PRIMARY KEY (`hotel_id`, `location`),
  INDEX `fk_hotels_cities1_idx` (`location` ASC),
  CONSTRAINT `fk_hotels_cities1`
    FOREIGN KEY (`location`)
    REFERENCES `travelagency`.`cities` (`city_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`flight_bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`flight_bookings` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `flight_id` INT NOT NULL,
  PRIMARY KEY (`booking_id`, `user_id`, `flight_id`),
  INDEX `fk_flight_bookings_flights1_idx` (`flight_id` ASC),
  CONSTRAINT `fk_flight_bookings_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travelagency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_flight_bookings_flights1`
    FOREIGN KEY (`flight_id`)
    REFERENCES `travelagency`.`flights` (`flight_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`car_bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`car_bookings` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `car_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `starts` DATE NOT NULL,
  `ends` DATE NOT NULL,
  PRIMARY KEY (`booking_id`, `car_id`, `user_id`),
  INDEX `fk_cars_bookings_cars1_idx` (`car_id` ASC),
  INDEX `fk_cars_bookings_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_cars_bookings_cars1`
    FOREIGN KEY (`car_id`)
    REFERENCES `travelagency`.`cars` (`car_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cars_bookings_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travelagency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`rooms` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `hotel_id` INT NOT NULL,
  `for_people` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`room_id`, `hotel_id`),
  INDEX `fk_rooms_hotels1_idx` (`hotel_id` ASC),
  CONSTRAINT `fk_rooms_hotels1`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `travelagency`.`hotels` (`hotel_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`hotel_bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`hotel_bookings` (
  `booking_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `room_id` INT NOT NULL,
  `from` DATE NOT NULL,
  `to` DATE NOT NULL,
  PRIMARY KEY (`booking_id`, `user_id`, `room_id`),
  INDEX `fk_hotel_bookings_users1_idx` (`user_id` ASC),
  INDEX `fk_hotel_bookings_rooms1_idx` (`room_id` ASC),
  CONSTRAINT `fk_hotel_bookings_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travelagency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hotel_bookings_rooms1`
    FOREIGN KEY (`room_id`)
    REFERENCES `travelagency`.`rooms` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travelagency`.`cruise_bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`cruise_bookings` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `cruise_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `rooms` INT NOT NULL,
  PRIMARY KEY (`booking_id`, `cruise_id`, `user_id`),
  INDEX `fk_cruise_bookings_cruises1_idx` (`cruise_id` ASC),
  INDEX `fk_cruise_bookings_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_cruise_bookings_cruises1`
    FOREIGN KEY (`cruise_id`)
    REFERENCES `travelagency`.`cruises` (`cruise_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cruise_bookings_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `travelagency`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `travelagency` ;

-- -----------------------------------------------------
-- Placeholder table for view `travelagency`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelagency`.`view1` (`id` INT);

-- -----------------------------------------------------
-- View `travelagency`.`view1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `travelagency`.`view1`;
USE `travelagency`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
