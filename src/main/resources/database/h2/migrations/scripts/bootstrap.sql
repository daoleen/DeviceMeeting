-- // Bootstrap.sql

-- This is the only SQL script file that is NOT
-- a valid migration and will not be run or tracked
-- in the changelog.  There is no @UNDO section.

-- // Do I need this file?

-- New projects likely won't need this file.
-- Existing projects will likely need this file.
-- It's unlikely that this bootstrap should be run
-- in the production environment.

-- // Purpose

-- The purpose of this file is to provide a facility
-- to initialize the database to a state before MyBatis
-- SQL migrations were applied.  If you already have
-- a database in production, then you probably have
-- a script that you run on your developer machine
-- to initialize the database.  That script can now
-- be put in this bootstrap file (but does not have
-- to be if you are comfortable with your current process.

-- // Running

-- The bootstrap SQL is run with the "migrate bootstrap"
-- command.  It must be run manually, it's never run as
-- part of the regular migration process and will never
-- be undone. Variables (e.g. ${variable}) are still
-- parsed in the bootstrap SQL.

-- After the boostrap SQL has been run, you can then
-- use the migrations and the changelog for all future
-- database change management.

CREATE TABLE users (
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

CREATE TABLE roles (
  `id`   INT         NOT NULL PRIMARY KEY,
  `role` VARCHAR(32) NOT NULL,

  UNIQUE ("role")
);

CREATE TABLE user_role_refs (
  `user_id` BIGINT NOT NULL,
  `role_id` INT    NOT NULL,

  PRIMARY KEY ("user_id", "role_id"),
  FOREIGN KEY ("user_id") REFERENCES "users" (id),
  FOREIGN KEY ("role_id") REFERENCES "roles" (id)
);


-- Applying a test data
-- Password for admin is: admin
-- Password for user is: 123456
INSERT INTO users (`id`, `email`, `password`, `is_enabled`, `created_at`, `expires_at`) VALUES
  (1, 'admin@localhost', 'adc65614b4ac6d94a57d7b9aa3d4802914ef6380d8be6e2ea2964aee3502444bcaca8b7911e46011', TRUE,
   '2014-06-18 00:00:00', '2015-06-18 00:00:00'),
  (2, 'user@localhost', '627c695fd827b2d017309bf7085a8d1740383ac680c8f66af5f92eb430d24b44b1939d3716569055', TRUE,
   '2014-06-18 00:00:00', '2015-06-18 00:00:00');

INSERT INTO roles (id, role) VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER');

INSERT INTO `user_role_refs` (user_id, role_id) VALUES (1, 1), (1, 2), (2, 2);