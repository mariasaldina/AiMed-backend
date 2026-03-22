-- Терапевт
INSERT INTO users (username, password, role) VALUES ('therapist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (user_id,address,education,description,practice_start_date,license,license_issue_date,license_expiry_date)
SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, Городская поликлиника №1',
'Сеченовский университет',
'Врач общей практики',
'2012-01-01','LIC-T-001','2012-02-01','2032-02-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),1);

-- Кардиолог
INSERT INTO users (username, password, role) VALUES ('cardiologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'СПб, Кардиоцентр',
'СПбГМУ',
'Кардиолог, заболевания сердца',
'2013-03-01','LIC-C-001','2013-04-01','2033-04-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),2);

-- Невролог
INSERT INTO users (username, password, role) VALUES ('neurologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Казань, Неврологический центр',
'Казанский ГМУ',
'Невролог',
'2011-05-01','LIC-N-001','2011-06-01','2031-06-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),3);

-- Эндокринолог
INSERT INTO users (username, password, role) VALUES ('endocrinologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, Эндокринологический центр',
'Сеченовский университет',
'Эндокринолог',
'2014-02-01','LIC-E-001','2014-03-01','2034-03-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),4);

-- Гастроэнтеролог
INSERT INTO users (username, password, role) VALUES ('gastroenterologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'СПб, Центр гастроэнтерологии',
'СПбГМУ',
'Гастроэнтеролог',
'2012-04-01','LIC-GE-001','2012-05-01','2032-05-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),5);

-- Педиатр
INSERT INTO users (username, password, role) VALUES ('pediatrician1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, Детская поликлиника',
'РНИМУ им. Пирогова',
'Педиатр',
'2013-01-01','LIC-P-001','2013-02-01','2033-02-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),6);

-- Дерматолог
INSERT INTO users (username, password, role) VALUES ('dermatologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Казань, Дерматологический центр',
'Казанский ГМУ',
'Дерматолог',
'2011-09-01','LIC-D-001','2011-10-01','2031-10-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),7);

-- Офтальмолог
INSERT INTO users (username, password, role) VALUES ('ophthalmologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'СПб, Центр зрения',
'СПбГМУ',
'Офтальмолог',
'2014-01-01','LIC-O-001','2014-02-01','2034-02-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),8);

-- ЛОР
INSERT INTO users (username, password, role) VALUES ('ent1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, ЛОР центр',
'Сеченовский университет',
'Оториноларинголог',
'2012-07-01','LIC-L-001','2012-08-01','2032-08-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),9);

-- Психиатр
INSERT INTO users (username, password, role) VALUES ('psychiatrist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'СПб, Психиатрическая клиника',
'СПбГМУ',
'Психиатр',
'2010-06-01','LIC-PS-001','2010-07-01','2030-07-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),10);

-- Нефролог
INSERT INTO users (username, password, role) VALUES ('nephrologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Казань, Центр нефрологии',
'Казанский ГМУ',
'Нефролог',
'2013-03-01','LIC-NF-001','2013-04-01','2033-04-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),11);

-- Ревматолог
INSERT INTO users (username, password, role) VALUES ('rheumatologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, Центр ревматологии',
'Сеченовский университет',
'Ревматолог',
'2011-02-01','LIC-R-001','2011-03-01','2031-03-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),12);

-- Хирург
INSERT INTO users (username, password, role) VALUES ('surgeon1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'СПб, Хирургический центр',
'СПбГМУ',
'Общая хирургия',
'2012-11-01','LIC-S-001','2012-12-01','2032-12-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),13);

-- Стоматолог
INSERT INTO users (username, password, role) VALUES ('dentist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, Стоматология Центр',
'МГМСУ',
'Стоматолог',
'2014-04-01','LIC-ST-001','2014-05-01','2034-05-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),14);

-- Гинеколог
INSERT INTO users (username, password, role) VALUES ('gynecologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'СПб, Центр женского здоровья',
'СПбГМУ',
'Гинеколог',
'2013-08-01','LIC-G-001','2013-09-01','2033-09-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),15);

-- Онколог
INSERT INTO users (username, password, role) VALUES ('oncologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, Онкоцентр',
'Сеченовский университет',
'Онколог',
'2011-05-01','LIC-ON-001','2011-06-01','2031-06-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),16);

-- Уролог
INSERT INTO users (username, password, role) VALUES ('urologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Казань, Урологический центр',
'Казанский ГМУ',
'Уролог',
'2012-10-01','LIC-U-001','2012-11-01','2032-11-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),17);

-- Аллерголог
INSERT INTO users (username, password, role) VALUES ('allergist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'СПб, Центр аллергологии',
'СПбГМУ',
'Аллерголог',
'2013-06-01','LIC-A-001','2013-07-01','2033-07-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),18);

-- Инфекционист
INSERT INTO users (username, password, role) VALUES ('infectiologist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Москва, Инфекционная больница',
'Сеченовский университет',
'Инфекционист',
'2010-01-01','LIC-I-001','2010-02-01','2030-02-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),19);

-- Физиотерапевт
INSERT INTO users (username, password, role) VALUES ('physiotherapist1','$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile SELECT currval(pg_get_serial_sequence('users','id')),
'Новосибирск, Центр реабилитации',
'НГМУ',
'Физиотерапевт',
'2014-03-01','LIC-F-001','2014-04-01','2034-04-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')),20);