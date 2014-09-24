-- // _sample data for users
-- Migration SQL that makes the change goes here.

-- Applying the test data
-- Password for admin is: admin
-- Password for user is: 123456
INSERT INTO users (id, email, password, is_enabled, created_at, expires_at, avatar) VALUES
  (1, 'admin@localhost', 'adc65614b4ac6d94a57d7b9aa3d4802914ef6380d8be6e2ea2964aee3502444bcaca8b7911e46011', 1, '2014-06-18 00:00:00', '2015-06-18 00:00:00', 'Donald-Duck.gif'),
  (2, 'user@localhost', '627c695fd827b2d017309bf7085a8d1740383ac680c8f66af5f92eb430d24b44b1939d3716569055', 1, '2014-06-18 00:00:00', '2015-06-18 00:00:00', null)


-- //@UNDO
-- SQL to undo the change goes here.
DELETE FROM users WHERE id IN (1,2)