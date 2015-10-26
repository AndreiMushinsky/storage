DROP TABLE IF EXISTS `Fabrics`;
CREATE TABLE `Fabrics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `Journal`;
CREATE TABLE `Journal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `fabric_id` int(11) NOT NULL,
  `is_dr` boolean NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`fabric_id`) REFERENCES `Fabrics` (`id`)
);