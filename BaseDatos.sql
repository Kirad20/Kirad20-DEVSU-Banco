-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.3.0 - MySQL Community Server - GPL
-- SO del servidor:              Linux
-- HeidiSQL Versión:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando estructura para tabla banco_db.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `persona_entity_rfc` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o7kkjdibnhmj8brwd8r5o8ihf` (`persona_entity_rfc`),
  CONSTRAINT `FKmn404apt37eu8wgrltv09sxok` FOREIGN KEY (`persona_entity_rfc`) REFERENCES `personas` (`rfc`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla banco_db.cuentas
CREATE TABLE IF NOT EXISTS `cuentas` (
  `numero_cuenta` bigint NOT NULL,
  `estado` bit(1) NOT NULL,
  `id_cliente` bigint NOT NULL,
  `saldo` double NOT NULL,
  `tipo_cuenta` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`numero_cuenta`) USING BTREE,
  KEY `FK1_cliente_id` (`id_cliente`),
  CONSTRAINT `FK1_cliente_id` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla banco_db.movimientos
CREATE TABLE IF NOT EXISTS `movimientos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `monto` double NOT NULL,
  `numero_cuenta` bigint NOT NULL,
  `tipo_movimiento` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `fecha_movimiento` datetime(6) DEFAULT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cuenta_id` (`numero_cuenta`),
  KEY `idx_cuenta_fecha` (`numero_cuenta`,`fecha_movimiento`),
  CONSTRAINT `FK_movimientos_cuentas` FOREIGN KEY (`numero_cuenta`) REFERENCES `cuentas` (`numero_cuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla banco_db.personas
CREATE TABLE IF NOT EXISTS `personas` (
  `rfc` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin NOT NULL,
  `curp` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `direccion` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `fecha_nacimiento` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `genero` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  `telefono` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`rfc`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
