-- // add peoples_count field to rooms
-- Migration SQL that makes the change goes here.
ALTER TABLE ROOMS ADD peoples_count SMALLINT NOT NULL DEFAULT '0'

-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE ROOMS DROP COLUMN peoples_count