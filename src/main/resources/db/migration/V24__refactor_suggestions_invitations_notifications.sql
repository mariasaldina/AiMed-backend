DROP TABLE doctor_suggestion_payload;

CREATE TABLE doctor_suggestions (
    doctor_id INT NOT NULL REFERENCES users(id),
    message_id INT NOT NULL REFERENCES messages(id)
);

DROP TABLE invitation_message_payload;

ALTER TABLE invitations ADD COLUMN message_id INT REFERENCES messages(id);

ALTER TYPE INVITATION_STATUS ADD VALUE 'CANCELLED';

ALTER TABLE notifications DROP COLUMN type;

DROP TYPE NOTIFICATION_TYPE;

ALTER TABLE notifications ADD COLUMN historic_status INVITATION_STATUS NOT NULL;