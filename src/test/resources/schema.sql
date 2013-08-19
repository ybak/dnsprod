-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.1.58-community - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 dnsprod 的数据库结构
DROP DATABASE IF EXISTS `dnsprod`;
CREATE DATABASE IF NOT EXISTS `dnsprod` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `dnsprod`;


-- 导出  表 dnsprod.domain_entry 结构
DROP TABLE IF EXISTS `domain_entry`;
CREATE TABLE IF NOT EXISTS `domain_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dns_server` varchar(20) DEFAULT NULL,
  `domain` varchar(30) DEFAULT NULL,
  `max_ip_number` bigint(20) DEFAULT NULL,
  `min_ip_number` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_DOMAIN` (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
