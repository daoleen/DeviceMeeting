-- // add user roles ref table
-- Migration SQL that makes the change goes here.
CREATE TABLE user_role_refs (
	user_id BIGINT NOT NULL,
	role_id INTEGER NOT NULL,
	
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE NO ACTION,
	FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE NO ACTION
)
DEFAULT CHARACTER SET utf8 
COLLATE utf8_general_ci ENGINE = INNODB;


-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE user_role_refs

