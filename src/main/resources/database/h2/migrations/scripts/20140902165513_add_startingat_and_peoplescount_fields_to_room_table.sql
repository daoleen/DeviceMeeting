-- // add startingat and peoplescount fields to room table
-- Migration SQL that makes the change goes here.
ALTER TABLE ROOMS ADD starting_at TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP;
ALTER TABLE ROOMS ADD peoples_count SMALLINT NOT NULL DEFAULT '0';


-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE ROOMS DROP COLUMN peoples_count;
ALTER TABLE ROOMS DROP COLUMN starting_at;