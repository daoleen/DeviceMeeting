-- // add invites table
-- Migration SQL that makes the change goes here.
CREATE TABLE invites (
	id BIGINT AUTO_INCREMENT NOT NULL,
	user_id BIGINT NOT NULL DEFAULT 0,
	room_id BIGINT NOT NULL DEFAULT 0,
	inviter_id BIGINT NOT NULL DEFAULT 0,
	comment VARCHAR(255),
	is_accepted SMALLINT NOT NULL DEFAULT 0,
	is_rejected SMALLINT NOT NULL DEFAULT 0,
	date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE NO ACTION,
	FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE NO ACTION,
	FOREIGN KEY (inviter_id) REFERENCES users(id) ON DELETE NO ACTION,
	UNIQUE INDEX invites(user_id, room_id, inviter_id)
)
DEFAULT CHARACTER SET utf8 
COLLATE utf8_general_ci ENGINE = INNODB;

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE invites;