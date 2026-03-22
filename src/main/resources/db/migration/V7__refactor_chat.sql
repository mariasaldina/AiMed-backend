CREATE TYPE MESSAGE_TYPE as ENUM ('USER', 'ASSISTANT');

CREATE TABLE messages (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    chat_id INT NOT NULL REFERENCES chats(id),
    type MESSAGE_TYPE NOT NULL
);

DROP TABLE user_messages;

DROP TABLE assistant_messages;

CREATE TABLE user_message_payload (
    message_id INT NOT NULL REFERENCES messages(id) PRIMARY KEY,
    content TEXT NOT NULL
);

CREATE TABLE assistant_message_payload (
    message_id INT NOT NULL REFERENCES messages(id) PRIMARY KEY,
    possible_causes TEXT[] DEFAULT '{}',
    urgency URGENCY_STATUS,
    recommendations TEXT[] DEFAULT '{}',
    doctors TEXT[] DEFAULT '{}'
);