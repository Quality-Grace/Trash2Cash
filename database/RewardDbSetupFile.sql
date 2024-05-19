SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `rewards` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `rewards`;

CREATE TABLE `rewards` (
  `POSITION` INT PRIMARY KEY,
  `COST` INT NOT NULL,
  `LEVEL` INT NOT NULL,
  `ICON` TEXT COLLATE utf8_bin NOT NULL,
  `TITLE` varchar(100) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE DATABASE IF NOT EXISTS `RecyclableMaterialTypes` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `RecyclableMaterialTypes`;

CREATE TABLE `RecyclableMaterialTypes` (
  `TYPE` varchar(100) COLLATE utf8_bin NOT NULL,
  `EXP` INT NOT NULL,
  `REWARD_AMOUNT` INT NOT NULL,
  `IMAGE` TEXT COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `RecyclableMaterialTypes` (`TYPE`, `EXP`,  `REWARD_AMOUNT`,  `IMAGE`) VALUES
('Paper', '0', '0', '/paper_type.xml'),
('Glass', '0', '0', '/glass_type.xml'),
('Metal', '0', '0', '/metal_type.xml'),
('Plastic', '0', '0', '/plastic_type.xml'),
('Other', '0', '0', '/other_type.xml');
