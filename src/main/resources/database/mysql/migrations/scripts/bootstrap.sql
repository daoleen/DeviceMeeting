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

INSERT INTO users (id, email, password, is_enabled, created_at, expires_at, avatar) VALUES
  (1, 'admin@localhost', 'adc65614b4ac6d94a57d7b9aa3d4802914ef6380d8be6e2ea2964aee3502444bcaca8b7911e46011', 1, '2014-06-18 00:00:00', '2015-06-18 00:00:00', 'Donald-Duck.gif'),
  (2, 'user@localhost', '627c695fd827b2d017309bf7085a8d1740383ac680c8f66af5f92eb430d24b44b1939d3716569055', 1, '2014-06-18 00:00:00', '2015-06-18 00:00:00', null);

INSERT INTO user_details(user_id, first_name, last_name) VALUES (1, 'Alexander', 'Kozlov'), (2, null, null);

INSERT INTO roles (id, role) VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER');
  
INSERT INTO user_role_refs (user_id, role_id) VALUES (1, 1), (1, 2), (2, 2);