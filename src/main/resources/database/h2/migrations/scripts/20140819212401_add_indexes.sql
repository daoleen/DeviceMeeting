-- // add indexes
-- Migration SQL that makes the change goes here.
CREATE INDEX users_email_idx ON users(email);


-- //@UNDO
-- SQL to undo the change goes here.
DROP INDEX users_email_idx;