CREATE TABLE `Fabrics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `Journal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `fabric_id` int(11) NOT NULL,
  `is_dr` tinyint(1) NOT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`fabric_id`) REFERENCES `Fabrics` (`id`)
);