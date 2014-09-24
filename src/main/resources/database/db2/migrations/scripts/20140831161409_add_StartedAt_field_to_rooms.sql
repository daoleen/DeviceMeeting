-- // add StartedAt field to rooms
-- Migration SQL that makes the change goes here.
ALTER TABLE ROOMS ADD starting_at TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP

-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE ROOMS DROP COLUMN starting_at