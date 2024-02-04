CREATE TABLE `expenses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `item` varchar(255) NOT NULL,
  `category` enum('GROCERIES','VEGETABLES_FRUITS_DAIRY_AND_MEAT','MEDICAL','OUTSIDE_FOOD','LOSE_OF_MONEY','SALON_AND_COSMETICS','TRANSPORT','SHOPPING','ENTERTAINMENT','RENT_AND_OTHER_BILLS','OTHERS') NOT NULL,
  `spent` double NOT NULL,
  `spent_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;