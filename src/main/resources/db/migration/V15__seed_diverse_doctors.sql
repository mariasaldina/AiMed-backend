-- =========================================================
-- ТЕРАПЕВТЫ
-- =========================================================

INSERT INTO users (username, password, role) VALUES ('therapist_extra_01', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Москва, Городская поликлиника №18',
    'Первый МГМУ им. И.М. Сеченова, лечебное дело, ординатура по терапии',
    'Ведёт взрослых пациентов с ОРВИ, гипертонией, анемией, хроническими заболеваниями ЖКТ. Спокойный и внимательный стиль ведения.',
    '2011-09-01',
    'LIC-T-101',
    '2016-03-12',
    '2028-03-12';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

INSERT INTO users (username, password, role) VALUES ('therapist_extra_02', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Санкт-Петербург, Медицинский центр на Литейном',
    'СЗГМУ им. И.И. Мечникова, лечебное дело',
    'Терапевт с уклоном в профилактическую медицину. Часто работает с жалобами на слабость, субфебрилитет, головокружение, скачки давления.',
    '2015-02-15',
    'LIC-T-102',
    '2019-04-20',
    '2027-04-20';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

INSERT INTO users (username, password, role) VALUES ('therapist_extra_03', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Казань, Клиника семейной медицины',
    'Казанский ГМУ, лечебное дело, интернатура по терапии',
    'Принимает пациентов с жалобами на кашель, температуру, боли в суставах, общую слабость. Умеет выстраивать план дообследования.',
    '2008-07-01',
    'LIC-T-103',
    '2014-06-11',
    '2025-01-15';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

INSERT INTO users (username, password, role) VALUES ('therapist_extra_04', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Екатеринбург, Центральная поликлиника',
    'Уральский ГМУ, лечебное дело',
    'Работает с пациентами с хроническими заболеваниями, контролем анализов, жалобами на утомляемость и боли неясного происхождения.',
    '2012-11-10',
    NULL,
    NULL,
    NULL;
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

INSERT INTO users (username, password, role) VALUES ('therapist_extra_05', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Новосибирск, Клиника Здоровье+',
    NULL,
    'Часто принимает пациентов после первичного онлайн-скрининга, помогает определить дальнейший маршрут по обследованиям и узким специалистам.',
    '2017-05-20',
    'LIC-T-105',
    '2020-09-01',
    '2029-09-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

INSERT INTO users (username, password, role) VALUES ('therapist_extra_06', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Нижний Новгород, Многопрофильная клиника №4',
    'Приволжский исследовательский медицинский университет, лечебное дело, ординатура по терапии',
    NULL,
    '2010-03-01',
    'LIC-T-106',
    '2015-07-10',
    '2026-07-10';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

INSERT INTO users (username, password, role) VALUES ('therapist_extra_07', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Самара, Амбулаторный центр Медлайн',
    'СамГМУ, лечебное дело',
    'Ведёт пациентов с длительным кашлем, постинфекционной астенией, колебаниями артериального давления. Аккуратно назначает минимально достаточное обследование.',
    '2018-01-12',
    'LIC-T-107',
    '2021-02-18',
    '2030-02-18';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

INSERT INTO users (username, password, role) VALUES ('therapist_extra_08', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Ростов-на-Дону, Клиника Южная',
    'РостГМУ, лечебное дело, терапия',
    'Терапевт с опытом ведения пациентов с сахарным диабетом 2 типа, ожирением, артериальной гипертензией и сочетанной хронической патологией.',
    '2006-04-01',
    'LIC-T-108',
    '2013-11-05',
    '2029-11-05';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 1);

-- =========================================================
-- ПЕДИАТРЫ
-- =========================================================

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_01', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Москва, Детская поликлиника №41',
    'РНИМУ им. Н.И. Пирогова, педиатрический факультет',
    'Ведёт детей с ОРВИ, отитами, бронхитами, аллергическими проявлениями. Консультирует родителей по вакцинации и восстановлению после инфекций.',
    '2014-09-01',
    'LIC-P-101',
    '2018-01-17',
    '2028-01-17';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_02', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Санкт-Петербург, Детский медицинский центр Балтика',
    'СПбГПМУ, педиатрия',
    'Педиатр с опытом наблюдения часто болеющих детей. Разбирает рецидивирующие инфекции, длительный кашель и проблемы с аппетитом.',
    '2011-02-10',
    'LIC-P-102',
    '2016-05-30',
    '2026-05-30';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_03', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Казань, Детская клиника Айболит',
    'Казанский ГМУ, педиатрия',
    'Принимает детей раннего возраста, помогает при температуре, сыпи, кишечных инфекциях, нарушениях сна и беспокойстве у младенцев.',
    '2019-06-01',
    'LIC-P-103',
    '2021-10-01',
    '2030-10-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_04', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Екатеринбург, Детская поликлиника №7',
    'Уральский ГМУ, педиатрический факультет',
    NULL,
    '2010-01-15',
    'LIC-P-104',
    '2014-08-08',
    '2024-08-08';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_05', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Новосибирск, Семейная клиника Дети',
    NULL,
    'Педиатр, ориентированный на амбулаторное ведение. Часто работает с вирусными инфекциями, жалобами на боли в животе и функциональными нарушениями ЖКТ у детей.',
    '2016-03-20',
    'LIC-P-105',
    '2020-06-15',
    '2029-06-15';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_06', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Челябинск, Детский амбулаторный центр',
    'Южно-Уральский ГМУ, педиатрия',
    'Работает с подростками и детьми школьного возраста. Жалобы: слабость, частые простуды, спортивные справки, контроль хронических состояний.',
    '2012-12-01',
    NULL,
    NULL,
    NULL;
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_07', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Самара, Центр детского здоровья',
    'СамГМУ, педиатрия',
    'Педиатр с акцентом на профилактику, наблюдение детей первого года жизни, прикорм, оценку физического развития и иммунопрофилактику.',
    '2009-09-01',
    'LIC-P-107',
    '2013-12-12',
    '2027-12-12';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

INSERT INTO users (username, password, role) VALUES ('pediatrician_extra_08', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Уфа, Детская клиника Радуга',
    'Башкирский ГМУ, педиатрия, ординатура по педиатрии',
    'Ведёт детей с пищевой аллергией, атопическим дерматитом, повторяющимися инфекциями верхних дыхательных путей.',
    '2017-11-01',
    'LIC-P-108',
    '2022-03-03',
    '2031-03-03';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 6);

-- =========================================================
-- ДЕРМАТОЛОГИ
-- =========================================================

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_01', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Москва, Кожно-венерологический центр на Таганке',
    'Первый МГМУ им. И.М. Сеченова, лечебное дело, ординатура по дерматовенерологии',
    'Консультирует по акне, розацеа, экземе, грибковым поражениям кожи и зудящим высыпаниям неясного происхождения.',
    '2013-04-01',
    'LIC-D-101',
    '2018-02-14',
    '2028-02-14';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_02', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Санкт-Петербург, Центр дерматологии Невский',
    'СЗГМУ им. И.И. Мечникова, дерматовенерология',
    'Часто работает с хроническими дерматозами, псориазом, атопическим дерматитом, а также с высыпаниями после приёма препаратов.',
    '2009-10-01',
    'LIC-D-102',
    '2014-01-20',
    '2025-12-20';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_03', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Казань, Клиника кожи и волос',
    'Казанский ГМУ, лечебное дело',
    'Специализируется на акне, себорейном дерматите, выпадении волос, воспалительных изменениях кожи лица и волосистой части головы.',
    '2018-06-15',
    'LIC-D-103',
    '2021-01-11',
    '2030-01-11';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_04', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Екатеринбург, Амбулаторный кожный кабинет',
    NULL,
    'Принимает взрослых с жалобами на зуд, шелушение, покраснение, локальные высыпания и контактные реакции на косметику.',
    '2011-01-20',
    'LIC-D-104',
    '2016-06-01',
    '2027-06-01';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_05', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Новосибирск, Сибирский центр дерматологии',
    'НГМУ, лечебное дело, ординатура по дерматовенерологии',
    NULL,
    '2010-08-01',
    'LIC-D-105',
    '2015-09-09',
    '2029-09-09';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_06', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Самара, Центр эстетической и клинической дерматологии',
    'СамГМУ, дерматовенерология',
    'Помимо общей дерматологии ведёт пациентов с постакне, воспалительными элементами, пигментацией и чувствительной кожей.',
    '2016-11-01',
    NULL,
    NULL,
    NULL;
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_07', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Ростов-на-Дону, Южный дерматологический центр',
    'РостГМУ, лечебное дело, дерматовенерология',
    'Работает с инфекционными и неинфекционными заболеваниями кожи: микозы, дерматиты, экзема, папулёзные высыпания.',
    '2007-03-01',
    'LIC-D-107',
    '2012-10-10',
    '2026-10-10';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

INSERT INTO users (username, password, role) VALUES ('dermatologist_extra_08', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Уфа, Клиника Дермамед',
    'Башкирский ГМУ, лечебное дело, ординатура по дерматовенерологии',
    'Консультирует подростков и взрослых. Частые обращения: акне, зуд кожи, сухость, трещины, реакция на бытовую химию.',
    '2020-02-01',
    'LIC-D-108',
    '2022-07-18',
    '2032-07-18';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 7);

-- =========================================================
-- ИНФЕКЦИОНИСТЫ
-- =========================================================

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_01', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Москва, Инфекционная клиническая больница №2',
    'Первый МГМУ им. И.М. Сеченова, лечебное дело, ординатура по инфекционным болезням',
    'Ведёт пациентов с лихорадкой неясного генеза, кишечными инфекциями, вирусными инфекциями, затяжным восстановлением после болезни.',
    '2012-05-01',
    'LIC-I-101',
    '2017-01-25',
    '2028-01-25';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_02', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Санкт-Петербург, Центр инфекционной патологии',
    'СЗГМУ им. И.И. Мечникова, инфекционные болезни',
    'Консультирует по затяжной температуре, увеличению лимфоузлов, подозрению на мононуклеоз, вирусные гепатиты и последствия кишечных инфекций.',
    '2008-12-01',
    'LIC-I-102',
    '2014-04-02',
    '2027-04-02';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_03', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Казань, Центр инфекционной диагностики',
    'Казанский ГМУ, лечебное дело',
    'Работает с пациентами после поездок, при подозрении на бактериальные и вирусные инфекции, при длительном субфебрилитете.',
    '2017-08-01',
    'LIC-I-103',
    '2020-03-15',
    '2030-03-15';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_04', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Екатеринбург, Областной инфекционный центр',
    'Уральский ГМУ, инфекционные болезни',
    NULL,
    '2011-04-01',
    'LIC-I-104',
    '2015-06-06',
    '2024-06-06';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_05', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Новосибирск, Клиника инфекционного наблюдения',
    NULL,
    'Специализируется на амбулаторных консультациях после перенесённых вирусных инфекций, при слабости, длительной температуре и изменениях в анализах.',
    '2014-09-01',
    'LIC-I-105',
    '2018-11-11',
    '2027-11-11';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_06', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Самара, Инфекционный амбулаторный кабинет',
    'СамГМУ, лечебное дело, ординатура по инфекционным болезням',
    'Ведёт пациентов с кишечными расстройствами инфекционного генеза, постинфекционной слабостью, длительными симптомами после ОРВИ.',
    '2019-01-15',
    NULL,
    NULL,
    NULL;
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_07', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Ростов-на-Дону, Южный центр инфекционных болезней',
    'РостГМУ, инфекционные болезни',
    'Инфекционист с опытом работы в стационаре и амбулаторном звене. Жалобы: лихорадка, слабость, сыпь, боли в горле, диарея инфекционного происхождения.',
    '2006-06-01',
    'LIC-I-107',
    '2012-02-02',
    '2026-02-02';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);

INSERT INTO users (username, password, role) VALUES ('infectiologist_extra_08', '$2a$12$jGO3rIuFRN42py6CBB.lZulVWAK7sagD2dVwRLPxg9/YkOBVWjfcS','DOCTOR'::USER_ROLE);
INSERT INTO doctor_profile (
    user_id, address, education, description,
    practice_start_date, license, license_issue_date, license_expiry_date
) SELECT
    currval(pg_get_serial_sequence('users','id')),
    'Уфа, Центр инфекционной медицины',
    'Башкирский ГМУ, лечебное дело, инфекционные болезни',
    'Консультирует по поводу вирусных инфекций, герпесвирусных состояний, увеличенных лимфоузлов и длительного воспалительного синдрома.',
    '2015-10-01',
    'LIC-I-108',
    '2019-08-19',
    '2031-08-19';
INSERT INTO doctors_specializations VALUES (currval(pg_get_serial_sequence('users','id')), 19);