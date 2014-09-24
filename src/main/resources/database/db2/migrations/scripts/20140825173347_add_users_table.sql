-- // add users table
-- Migration SQL that makes the change goes here.
CREATE TABLE users (
	id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(128) NOT NULL,
	is_enabled SMALLINT NOT NULL DEFAULT 0,
	created_at TIMESTAMP,
	expires_at TIMESTAMP,
	is_locked SMALLINT NOT NULL DEFAULT 0,
	locked_reason VARCHAR(255),
	avatar VARCHAR(255),
	
	PRIMARY KEY(id)
)


-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE users