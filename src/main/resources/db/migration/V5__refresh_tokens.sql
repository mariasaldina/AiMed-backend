CREATE TABLE refresh_tokens (
    token TEXT PRIMARY KEY,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW() + '7 days'::INTERVAL,
    user_id INT NOT NULL REFERENCES users(id)
);