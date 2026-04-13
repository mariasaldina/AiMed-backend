ALTER TABLE invitations
DROP CONSTRAINT invitations_message_id_fkey;

ALTER TABLE invitations
ADD CONSTRAINT invitations_message_id_fkey
FOREIGN KEY (message_id)
REFERENCES messages(id)
ON DELETE SET NULL;