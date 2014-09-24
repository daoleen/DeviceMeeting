-- // _sample data for user_details
-- Migration SQL that makes the change goes here.
INSERT INTO user_details(user_id, first_name, last_name) VALUES 
(1, 'Alexander', 'Kozlov'),
(2, null, null)


-- //@UNDO
-- SQL to undo the change goes here.
DELETE FROM user_details WHERE user_id IN (1,2)