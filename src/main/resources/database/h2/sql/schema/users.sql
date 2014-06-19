CREATE TABLE IF NOT EXISTS users (
  `id`            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email`         VARCHAR(255) NOT NULL,
  `password`      VARCHAR(128) NOT NULL,
  `is_enabled`    BOOLEAN      NOT NULL DEFAULT FALSE,
  `created_at`    DATETIME,
  `expires_at`    DATETIME,
  `is_locked`     BOOLEAN      NOT NULL DEFAULT FALSE,
  `locked_reason` VARCHAR(255),

  UNIQUE ("email")
);