-- // add avatar to user
-- Migration SQL that makes the change goes here.
alter table `users` add `avatar` varchar(255);


-- //@UNDO
-- SQL to undo the change goes here.
alter table `"users"` drop column `avatar`;