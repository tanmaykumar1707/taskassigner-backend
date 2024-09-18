CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int AUTO_INCREMENT  PRIMARY KEY,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL UNIQUE,
  `mobile_number` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` BOOLEAN DEFAULT true,
  `role` ENUM('admin', 'delegator', 'executor'),
  `passwordResetToken` varchar(100) DEFAULT NULL,
  `passwordResetTokenExpiry` date DEFAULT NULL,
  `lastPasswordChangedAt` date DEFAULT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
   `updated_by` varchar(20) DEFAULT NULL
);