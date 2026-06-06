CREATE EXTENSION IF NOT EXISTS vector;

ALTER TABLE chats ADD COLUMN context_embedding vector(768);
ALTER TABLE chats ADD COLUMN last_user_message_at timestamp with time zone DEFAULT now();
ALTER TABLE chats ADD COLUMN last_doctor_search_at timestamp with time zone DEFAULT now();

ALTER TABLE doctor_profile ADD COLUMN profile_embedding vector(768);

CREATE INDEX ON chats
USING hnsw (context_embedding vector_cosine_ops);

CREATE INDEX ON doctor_profile
USING hnsw (profile_embedding vector_cosine_ops);