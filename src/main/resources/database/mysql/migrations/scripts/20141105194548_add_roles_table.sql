-- // add roles table
-- Migration SQL that makes the change goes here.
CREATE TABLE roles (
	id INTEGER AUTO_INCREMENT NOT NULL,
	role VARCHAR(32) NOT NULL UNIQUE,
	
	PRIMARY KEY(id)
)
DEFAULT CHARACTER SET utf8 
COLLATE utf8_general_ci ENGINE = INNODB;

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE roles

