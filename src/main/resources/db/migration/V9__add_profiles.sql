CREATE TYPE GENDER AS ENUM ('MALE', 'FEMALE');

CREATE TABLE patient_profile (
    user_id INT NOT NULL REFERENCES users(id) PRIMARY KEY,
    address TEXT,
    birthdate DATE CHECK (birthdate <= CURRENT_DATE),
    gender GENDER,
    medical_history TEXT
);

CREATE TABLE doctor_profile (
    user_id INT NOT NULL REFERENCES users(id) PRIMARY KEY,
    address TEXT,
    education TEXT,
    description TEXT,
    practice_start_date DATE CHECK (practice_start_date <= CURRENT_DATE),
    license TEXT UNIQUE,
    license_issue_date DATE CHECK (license_issue_date <= CURRENT_DATE),
    license_expiry_date DATE CHECK (license_expiry_date > license_issue_date)
);

CREATE TYPE CONTACT_TYPE AS ENUM ('EMAIL', 'PHONE', 'MESSENGER', 'OTHER');

CREATE TABLE user_contacts (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    type CONTACT_TYPE NOT NULL,
    value TEXT NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE
);