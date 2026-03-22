-- Вставляем симптомы, только по имени
INSERT INTO symptoms (name) VALUES
('боль в горле'),
('кашель'),
('повышенная температура'),
('головная боль'),
('одышка'),
('слабость'),
('насморк'),
('чихание'),
('потеря вкуса'),
('потеря обоняния'),
('пульсирующая головная боль'),
('тошнота'),
('изжога'),
('вздутие живота'),
('понос'),
('запор'),
('ложные позывы к мочеиспусканию'),
('боль при мочеиспускании'),
('кожный зуд'),
('сыпь'),
('шелушение кожи'),
('опухание суставов'),
('жажда'),
('частое мочеиспускание'),
('потеря веса'),
('усталость'),
('нарушение сна'),
('онемение'),
('свистящие хрипы'),
('шум в ушах'),
('головокружение'),
('покраснение глаз'),
('слезотечение'),
('дрожание конечностей'),
('боль в груди'),
('сердцебиение'),
('изменение аппетита');

-- Вставляем заболевания
INSERT INTO diseases (name) VALUES
('ОРВИ'),
('грипп'),
('COVID-19'),
('пневмония'),
('бронхит'),
('астма'),
('аллергический ринит'),
('гастрит'),
('язвенная болезнь'),
('диарея'),
('запор'),
('гипертония'),
('диабет'),
('анемия'),
('артрит'),
('дерматит'),
('псориаз'),
('мигрень'),
('цистит'),
('инфекция мочевых путей'),
('ожирение'),
('депрессия'),
('анорексия'),
('бессонница'),
('инфаркт миокарда'),
('инсульт'),
('фибромиалгия'),
('аутоиммунное заболевание'),
('болезнь Крона');

-- Связи symptoms ↔ diseases через подзапросы по имени
INSERT INTO symptoms_diseases (disease_id, symptom_id)
-- ОРВИ
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'ОРВИ' AND s.name IN ('кашель','насморк','боль в горле','чихание','повышенная температура');

-- Грипп
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'грипп' AND s.name IN ('повышенная температура','головная боль','слабость','кашель');

-- COVID-19
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'COVID-19' AND s.name IN ('кашель','повышенная температура','потеря вкуса','потеря обоняния','слабость');

-- Пневмония
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'пневмония' AND s.name IN ('кашель','одышка','повышенная температура','боль в груди');

-- Бронхит
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'бронхит' AND s.name IN ('кашель','свистящие хрипы','повышенная температура');

-- Астма
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'астма' AND s.name IN ('одышка','свистящие хрипы','кашель');

-- Аллергический ринит
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'аллергический ринит' AND s.name IN ('чихание','насморк','зуд','слезотечение');

-- Гастрит
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'гастрит' AND s.name IN ('боль в животе','тошнота','изжога','вздутие живота');

-- Язвенная болезнь
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'язвенная болезнь' AND s.name IN ('боль в животе','изжога','вздутие живота','потеря веса');

-- Диарея
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'диарея' AND s.name IN ('понос','боль в животе','слабость');

-- Запор
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'запор' AND s.name IN ('запор','боль в животе','вздутие живота');

-- Гипертония
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'гипертония' AND s.name IN ('головная боль','головокружение','сердцебиение');

-- Диабет
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'диабет' AND s.name IN ('жажда','частое мочеиспускание','потеря веса','слабость');

-- Анемия
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'анемия' AND s.name IN ('слабость','бледность кожи','головокружение');

-- Артрит
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'артрит' AND s.name IN ('опухание суставов','боль в груди');

-- Дерматит
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'дерматит' AND s.name IN ('кожный зуд','сыпь','шелушение кожи');

-- Псориаз
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'псориаз' AND s.name IN ('шелушение кожи','кожный зуд');

-- Мигрень
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'мигрень' AND s.name IN ('пульсирующая головная боль','тошнота','светобоязнь');

-- Цистит
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'цистит' AND s.name IN ('боль при мочеиспускании','ложные позывы к мочеиспусканию','частое мочеиспускание');

-- Инфекция мочевых путей
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'инфекция мочевых путей' AND s.name IN ('боль при мочеиспускании','частое мочеиспускание','повышенная температура');

-- Ожирение
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'ожирение' AND s.name IN ('усталость','одышка');

-- Депрессия
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'депрессия' AND s.name IN ('усталость','нарушение сна','слабость');

-- Анорексия
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'анорексия' AND s.name IN ('потеря веса','изменение аппетита');

-- Бессонница
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'бессонница' AND s.name IN ('нарушение сна','усталость');

-- Инфаркт миокарда
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'инфаркт миокарда' AND s.name IN ('боль в груди','одышка','потливость');

-- Инсульт
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'инсульт' AND s.name IN ('онемение','головная боль');

-- Фибромиалгия
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'фибромиалгия' AND s.name IN ('слабость','дрожание конечностей','пульсирующая головная боль');

-- Аутоиммунное заболевание
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'аутоиммунное заболевание' AND s.name IN ('усталость','слабость');

-- Болезнь Крона
INSERT INTO symptoms_diseases (disease_id, symptom_id)
SELECT d.id, s.id FROM diseases d JOIN symptoms s ON d.name = 'болезнь Крона' AND s.name IN ('боль в животе','понос','вздутие живота');