DROP TABLE doctor_suggestions;
DROP TABLE contacts_messages;

CREATE TABLE doctor_suggestion_payload (
    message_id INT NOT NULL REFERENCES messages(id) PRIMARY KEY,
    doctors JSONB NOT NULL DEFAULT '{}'::jsonb
);

CREATE TABLE contacts_message_payload (
    message_id INT NOT NULL REFERENCES messages(id) PRIMARY KEY,
    doctor_id INT NOT NULL REFERENCES users(id),
    content TEXT NOT NULL
);

ALTER TYPE MESSAGE_TYPE ADD VALUE 'DOCTOR_SUGGESTIONS';
ALTER TYPE MESSAGE_TYPE ADD VALUE 'CONTACTS';

ALTER TABLE users ADD COLUMN full_name TEXT;