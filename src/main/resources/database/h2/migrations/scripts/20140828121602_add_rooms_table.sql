-- // add rooms tables
-- Migration SQL that makes the change goes here.
CREATE TABLE rooms (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(1024),
	owner_id BIGINT NOT NULL,
	is_anyone_speaker SMALLINT NOT NULL DEFAULT 0,
	is_public SMALLINT NOT NULL DEFAULT 1,
	logo VARCHAR(255),
	
	PRIMARY KEY(id),
	FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE NO ACTION
)


-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE rooms;

