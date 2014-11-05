-- // add rooms table
-- Migration SQL that makes the change goes here.
CREATE TABLE rooms (
	id BIGINT AUTO_INCREMENT NOT NULL,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(1024),
	owner_id BIGINT NOT NULL,
	is_anyone_speaker SMALLINT NOT NULL DEFAULT 0,
	is_public SMALLINT NOT NULL DEFAULT 1,
	logo VARCHAR(255),
	starting_at TIMESTAMP NOT NULL DEFAULT NOW(),
	peoples_count SMALLINT NOT NULL DEFAULT '0',
	
	PRIMARY KEY(id),
	FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE NO ACTION,
	INDEX rooms(name)
)
DEFAULT CHARACTER SET utf8 
COLLATE utf8_general_ci ENGINE = INNODB;


-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE rooms;

