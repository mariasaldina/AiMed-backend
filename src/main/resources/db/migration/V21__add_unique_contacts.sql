ALTER TABLE user_contacts ADD UNIQUE (user_id, type);

DROP TABLE contacts_message_payload;

CREATE TYPE message_type_new AS ENUM (
    'USER',
    'ASSISTANT',
    'DOCTOR_SUGGESTIONS'
);

ALTER TABLE messages
ALTER COLUMN type TYPE message_type_new
USING type::text::message_type_new;

DROP TYPE message_type;

ALTER TYPE message_type_new
RENAME TO message_type;