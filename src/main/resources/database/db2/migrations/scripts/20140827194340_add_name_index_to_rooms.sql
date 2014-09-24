-- // add name_index to rooms
-- Migration SQL that makes the change goes here.
CREATE INDEX rooms_name_idx ON rooms (name)


-- //@UNDO
-- SQL to undo the change goes here.
DROP INDEX rooms_name_idx