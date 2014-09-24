-- // _sample data for roles
-- Migration SQL that makes the change goes here.
INSERT INTO roles (id, role) VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER')


-- //@UNDO
-- SQL to undo the change goes here.
DELETE FROM roles WHERE id IN (1,2)

