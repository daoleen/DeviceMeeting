-- // add user details table
-- Migration SQL that makes the change goes here.
CREATE TABLE user_details (
	user_id BIGINT NOT NULL,
	first_name VARCHAR(32),
	last_name VARCHAR(32),
	about VARCHAR(1024),
	is_online SMALLINT NOT NULL DEFAULT 0,
	skype VARCHAR(32),
	linkedin VARCHAR(255),
	rating DECIMAL NOT NULL DEFAULT '0.00',
	rating_votes_count INTEGER NOT NULL DEFAULT 0,
	
	PRIMARY KEY (user_id),
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE NO ACTION
)
DEFAULT CHARACTER SET utf8 
COLLATE utf8_general_ci ENGINE = INNODB;

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE user_details

