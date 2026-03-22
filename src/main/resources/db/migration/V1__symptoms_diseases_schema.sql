CREATE TABLE symptoms (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT
);

CREATE TABLE diseases (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT
);

CREATE TABLE symptoms_diseases (
    symptom_id INT REFERENCES symptoms(id),
    disease_id INT REFERENCES diseases(id)
);