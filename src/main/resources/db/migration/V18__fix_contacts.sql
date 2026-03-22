ALTER TABLE contacts_message_payload DROP COLUMN content;

ALTER TABLE contacts_message_payload ADD COLUMN content JSONB NOT NULL DEFAULT '[]'::jsonb;