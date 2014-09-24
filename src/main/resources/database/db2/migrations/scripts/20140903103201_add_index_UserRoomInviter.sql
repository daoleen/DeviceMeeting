-- // add index UserRoomInviter
-- Migration SQL that makes the change goes here.
CREATE UNIQUE INDEX invites_idx ON invites(user_id, room_id, inviter_id)


-- //@UNDO
-- SQL to undo the change goes here.
DROP INDEX invites_idx