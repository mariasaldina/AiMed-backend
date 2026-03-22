ALTER TABLE assistant_messages DROP COLUMN described_symptoms;

CREATE TABLE chats_symptoms (
    chat_id INT NOT NULL REFERENCES chats(id),
    symptom_id INT NOT NULL REFERENCES symptoms(id),
    PRIMARY KEY(chat_id, symptom_id)
);

ALTER TABLE symptoms_diseases ADD PRIMARY KEY (symptom_id, disease_id);