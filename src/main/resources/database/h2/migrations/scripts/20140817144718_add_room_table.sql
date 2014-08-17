-- // add room table
-- Migration SQL that makes the change goes here.
CREATE TABLE `rooms` (
	id bigint not null auto_increment primary key,
	name varchar(255) not null,
	description text,
	owner_id bigint not null,
	is_anyone_speaker boolean not null default false,
	is_public boolean not null default true,
	
	
	..../// indexes
);


-- //@UNDO
-- SQL to undo the change goes here.


