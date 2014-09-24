-- // _sample data for user_role_refs
-- Migration SQL that makes the change goes here.
INSERT INTO user_role_refs (user_id, role_id) VALUES 
	(1, 1), 
	(1, 2), 
	(2, 2)


-- //@UNDO
-- SQL to undo the change goes here.
DELETE FROM user_role_refs WHERE (user_id IN (1,2)) OR (role_id IN (1,2))