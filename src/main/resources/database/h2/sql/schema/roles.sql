CREATE TABLE IF NOT EXISTS roles (
  `id`   INT         NOT NULL PRIMARY KEY,
  `role` VARCHAR(32) NOT NULL,

  UNIQUE ("role")
);