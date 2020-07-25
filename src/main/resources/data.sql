INSERT INTO genders (name, is_deleted, created_at)
VALUES ('Muž', FALSE, CURRENT_TIMESTAMP);
INSERT INTO genders (name, is_deleted, created_at)
VALUES ('Žena', FALSE, CURRENT_TIMESTAMP);
INSERT INTO genders (name, is_deleted, created_at)
VALUES ('Iné', FALSE, CURRENT_TIMESTAMP);

INSERT INTO users (account_id, first_name, last_name, gender, balance, tel, is_deleted, created_at)
VALUES (2, 'Ján', 'Široký', 'Muž', 500.00, '+421917642985', FALSE, CURRENT_TIMESTAMP);
INSERT INTO users (account_id, first_name, last_name, gender, balance, tel, is_deleted, created_at)
VALUES (3, 'Peter', 'Varga', 'Muž', 350.45, '+421915521884', FALSE, CURRENT_TIMESTAMP);
INSERT INTO users (account_id, first_name, last_name, gender, balance, tel, is_deleted, created_at)
VALUES (4, 'Michal', 'Velký', 'Muž', 100.00, '+421911521835', FALSE, CURRENT_TIMESTAMP);
INSERT INTO users (account_id, first_name, last_name, gender, balance, tel, is_deleted, created_at)
VALUES (5, 'Lukáš', 'Trnka', 'Muž', 80.00, '+421453142155', FALSE, CURRENT_TIMESTAMP);
INSERT INTO users (account_id, first_name, last_name, gender, balance, tel, is_deleted, created_at)
VALUES (6, 'Denis', 'Malý', 'Muž', 125.00, '+421912681844', FALSE, CURRENT_TIMESTAMP);

INSERT INTO places (user_id, country, region, district, place, street, zip, code, is_deleted, created_at)
VALUES (1, 'Slovakia', 'Nitriansky kraj', 'Nové Zámky', 'Nesvady', 'Nová 25', '94651', 'sk', FALSE, CURRENT_TIMESTAMP);
INSERT INTO places (user_id, country, region, district, place, street, zip, code, is_deleted, created_at)
VALUES (2, 'Slovakia', 'Banskobystrický kraj', 'Poltár', 'Bádice', 'Obchodná 11', '95146', 'sk', FALSE, CURRENT_TIMESTAMP);
INSERT INTO places (user_id, country, region, district, place, street, zip, code, is_deleted, created_at)
VALUES (3, 'Slovakia', 'Bratislavský kraj', 'Pezinok', 'Báčovce', 'Knižná 33', '96268', 'sk', FALSE, CURRENT_TIMESTAMP);
INSERT INTO places (user_id, country, region, district, place, street, zip, code, is_deleted, created_at)
VALUES (4, 'Slovakia', 'Banskobystrický kraj', 'Banská Bystrica', 'Banská Bystrica', 'Hlvná 1', '97401', 'sk', FALSE, CURRENT_TIMESTAMP);
INSERT INTO places (user_id, country, region, district, place, street, zip, code, is_deleted, created_at)
VALUES (5, 'Slovakia', 'Bratislavský kraj', 'Bratislava I', 'Devín', 'Hutnicka 14', '84110', 'sk', FALSE, CURRENT_TIMESTAMP);