DROP TABLE chats_symptoms;
DROP TABLE symptoms_diseases;
DROP TABLE symptoms;
DROP TABLE diseases;

CREATE TABLE specializations (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT
);

CREATE TABLE doctors_specializations (
    doctor_id INT NOT NULL REFERENCES doctor_profile(user_id),
    specialization_id INT NOT NULL REFERENCES specializations(id),
    PRIMARY KEY (doctor_id, specialization_id)
);

ALTER TABLE chats ADD COLUMN context JSONB;