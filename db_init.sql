DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS positions CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS users_to_departments CASCADE;
DROP TABLE IF EXISTS users_to_roles CASCADE;
DROP TABLE IF EXISTS indicators CASCADE;
DROP TABLE IF EXISTS indicator_categories CASCADE;
DROP TABLE IF EXISTS indicators_to_positions CASCADE;
DROP TABLE IF EXISTS files CASCADE;
DROP TABLE IF EXISTS facts CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roles
(
  id   UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE positions
(
  id   UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE departments
(
  id   UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE users
(
  id          UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  login       VARCHAR(15)  NOT NULL UNIQUE,
  password    VARCHAR(255) NOT NULL,
  email       VARCHAR(100) NOT NULL,
  name        VARCHAR(30)  NOT NULL,
  surname     VARCHAR(20)  NOT NULL,
  position_id UUID,
  created     TIMESTAMP             DEFAULT now() NOT NULL,
  last_seen   TIMESTAMP,
  CONSTRAINT user_login_idx UNIQUE (login),
  FOREIGN KEY (position_id) REFERENCES positions (id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE users_to_departments
(
  user_id       UUID NOT NULL,
  department_id UUID NOT NULL,
  CONSTRAINT users_to_departments_idx UNIQUE (user_id, department_id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (department_id) REFERENCES departments (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE users_to_roles
(
  user_id UUID NOT NULL,
  role_id UUID NOT NULL,
  CONSTRAINT users_to_roles_idx UNIQUE (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE indicator_categories
(
  id   UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE indicators
(
  id          UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name        VARCHAR(400) NOT NULL UNIQUE,
  category_id UUID,
  description VARCHAR(500),
  base        INTEGER      NOT NULL DEFAULT 5,
  max         INTEGER      NOT NULL DEFAULT 5,
  CONSTRAINT indicators_idx UNIQUE (name),
  FOREIGN KEY (category_id) REFERENCES indicator_categories (id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE indicators_to_positions
(
  indicator_id UUID NOT NULL,
  position_id  UUID NOT NULL,
  CONSTRAINT indicators_to_positions_idx UNIQUE (indicator_id, position_id),
  FOREIGN KEY (indicator_id) REFERENCES indicators (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (position_id) REFERENCES positions (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE facts
(
  id              UUID    NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  indicator_id    UUID    NOT NULL,
  pointsSuggested INTEGER NOT NULL,
  pointsApproved  INTEGER NOT NULL,
  user_id         UUID    NOT NULL,
  creator_id      UUID,
  created         TIME    NOT NULL DEFAULT 'now()',
  modified        TIME    NOT NULL DEFAULT 'now()',
  approved        TIME,
  signer_id       UUID,
  CONSTRAINT facts_idx UNIQUE (indicator_id, created, user_id),
  FOREIGN KEY (indicator_id) REFERENCES indicators (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (creator_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL,
  FOREIGN KEY (signer_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE files
(
  id      UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  path    VARCHAR(255) NOT NULL,
  fact_id UUID         NOT NULL,
  user_id UUID         NOT NULL,
  CONSTRAINT files_idx UNIQUE (path, fact_id),
  FOREIGN KEY (fact_id) REFERENCES facts (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO positions (id, name)
VALUES ('9e834c77-8513-4fad-944c-f66ed0370ab7', 'Административно-управленческйи персонал'),
       ('f332fbd7-e6a6-468e-8096-fb754db71c72', 'Учитель'),
       ('a9c645cf-a2b3-4867-bbf8-4e8d08ac6d9f', 'Воспитатель'),
       ('f1b37423-0a4a-42d4-823d-9a9fe693312b', 'Помощник воспитателя'),
       ('0d6a6798-dd1a-4832-9c0e-df652db8ee1e', 'Иной педагогический сотрудник'),
       ('b07b8c68-1f3f-4af8-8a44-2d731ce77650', 'Иной непедагогический сотрудник');

INSERT INTO roles (id, name)
VALUES ('35825bdb-700e-4e5d-801e-66ab703805c9', 'Администратор системы'),
       ('d7fcca28-616b-43e9-9ba0-f7dbc441675e', 'Модератор'),
       ('d0cd12ff-7dcd-4ae5-b2d2-8dcd244f0bad', 'Пользователь');

INSERT INTO users (id, email, login, password, name, surname, position_id)
VALUES ('af61adb1-63ed-4123-bbab-63eceda8d2c2', 'morozovda-103@1507.org.ru', 'morozovda-103', '1234', 'Дмитрий',
        'Морозов', 'b07b8c68-1f3f-4af8-8a44-2d731ce77650'),
       ('3b74235a-c24b-4dfc-96fe-47088e7371c0', 'trofimovlb@1507.org.ru', 'admin2', '1234', 'Леонид', 'Трофимов',
        'b07b8c68-1f3f-4af8-8a44-2d731ce77650'),
       ('66b742d7-5189-40c3-b104-77a1f1c12ad5', 'user1@mail.ru', 'user1', '1234', 'Жанникалавна', 'Стаканчикова',
        'f332fbd7-e6a6-468e-8096-fb754db71c72'),
       ('2d7ddbff-234f-46e9-861b-98da29927672', 'manager1@mail.ru', 'manager1', '1234', 'Елена Васильевна', 'Скачкова',
        '9e834c77-8513-4fad-944c-f66ed0370ab7'),
       ('a662ee82-f8ae-43ea-b63f-928c47edd897', 'manager2@mail.ru', 'manager2', '1234', 'Лариса Васильевна', 'Комягина',
        '9e834c77-8513-4fad-944c-f66ed0370ab7');

INSERT INTO departments (id, name)
VALUES ('0e04951b-4c05-4c76-bdf8-dd4780431dc8', '132-9'),
       ('40b844c8-74d5-42d9-bf24-da2434936b11', '142-5'),
       ('bab087e0-6a48-4fc7-ab1e-2deb515d7529', '18-5');

INSERT INTO users_departments (user_id, department_id)
VALUES ('af61adb1-63ed-4123-bbab-63eceda8d2c2', '0e04951b-4c05-4c76-bdf8-dd4780431dc8'), -- Морозов 1507
       ('af61adb1-63ed-4123-bbab-63eceda8d2c2', '40b844c8-74d5-42d9-bf24-da2434936b11'), -- Морозов 865
       ('3b74235a-c24b-4dfc-96fe-47088e7371c0', '40b844c8-74d5-42d9-bf24-da2434936b11'), -- Трофимов 865
       ('66b742d7-5189-40c3-b104-77a1f1c12ad5', '0e04951b-4c05-4c76-bdf8-dd4780431dc8'), -- Стаканчикова 1507
       ('2d7ddbff-234f-46e9-861b-98da29927672', '0e04951b-4c05-4c76-bdf8-dd4780431dc8'), -- Скачкова 1507
       ('a662ee82-f8ae-43ea-b63f-928c47edd897', '40b844c8-74d5-42d9-bf24-da2434936b11'), -- Комягина 865
       ('a662ee82-f8ae-43ea-b63f-928c47edd897', '0e04951b-4c05-4c76-bdf8-dd4780431dc8'), -- Комягина 1507
       ('a662ee82-f8ae-43ea-b63f-928c47edd897', 'bab087e0-6a48-4fc7-ab1e-2deb515d7529'); -- Комягина 930

INSERT INTO users_to_roles (user_id, role_id)
VALUES ('af61adb1-63ed-4123-bbab-63eceda8d2c2', '35825bdb-700e-4e5d-801e-66ab703805c9'), -- Морозов Адмнистратор системы
       ('af61adb1-63ed-4123-bbab-63eceda8d2c2', 'd0cd12ff-7dcd-4ae5-b2d2-8dcd244f0bad'), -- Морозов Пользователь
       ('3b74235a-c24b-4dfc-96fe-47088e7371c0', 'd0cd12ff-7dcd-4ae5-b2d2-8dcd244f0bad'), -- Трофимов Пользователь
       ('66b742d7-5189-40c3-b104-77a1f1c12ad5', 'd0cd12ff-7dcd-4ae5-b2d2-8dcd244f0bad'), -- Стаканчикова Пользователь
       ('2d7ddbff-234f-46e9-861b-98da29927672', 'd7fcca28-616b-43e9-9ba0-f7dbc441675e'), -- Скачкова Модератор
       ('2d7ddbff-234f-46e9-861b-98da29927672', 'd0cd12ff-7dcd-4ae5-b2d2-8dcd244f0bad'), -- Скачкова Пользователь
       ('a662ee82-f8ae-43ea-b63f-928c47edd897', 'd7fcca28-616b-43e9-9ba0-f7dbc441675e'), -- Комягина Модератор
       ('a662ee82-f8ae-43ea-b63f-928c47edd897', 'd0cd12ff-7dcd-4ae5-b2d2-8dcd244f0bad'); -- Комягина Пользователь

INSERT INTO indicator_categories (id, name)
VALUES ('9017fe31-7037-4112-a63c-77fe80081e08', 'Внешняя оценка деятельности'),
       ('caad2c5a-17df-46ec-a2ca-23dc554b10a6', 'Качественное массовое образование');

INSERT INTO indicators (id, name, category_id, description, base, max)
VALUES ('c6f33b37-b426-4120-9d96-a157c61ca2ce', 'Административная оценка деятельности',
        null, 'Грамоты, благодарственные письма, благодарности -  8 баллов каждая', 8,
        8),
       ('88d0dcf1-0103-41b2-a528-4c71047bc3be',
        'Выполнение общественно-значимых функций (субботник, демонстрации, городские мероприятия) в том числе поощрения руководства школы',
        null, 'Невыполнение - 0 баллов, выполнение -  5 баллов за каждое мероприятие', 5, 5),
       ('9f5cdc41-e831-4cbc-ad3c-4ef9e2a8bbc7',
        'Высокий уровень исполнительской дисциплины и эффективная производственная деятельность', NULL,
        'Отсутствие замечаний со стороны руководителя отдела, службы и администрации  - 10 баллов, наличие замечаний - 0 баллов ',
        0, 10),
       ('a893d347-0b62-4a53-b021-b331ed3d7cb3',
        'Положительная динамика среднего балла по предмету по всем классам или стабильно высокий средний балл по итогам триместров/полугодий',
        'caad2c5a-17df-46ec-a2ca-23dc554b10a6',
        'За повышение качества на 0.1 - 1 балл, за стабильно высокое качество (75% или средний балл 4,2+ среднее значение по всем предметам и по всем классам) - 5 баллов. При наличии неуспевающих - 0 баллов.',
        0, 5),
       ('ce029498-301e-409c-93bd-dcb4bb3501f0',
        'Результаты независимых диагностик, комплексной диагностики 1-3 классов',
        'caad2c5a-17df-46ec-a2ca-23dc554b10a6',
        '"100 % справились с работой - 3 балла, 60-70% качества - 4 балла, 71-80% качества - 5 баллов, 81-90% качества - 7 баллов, более 91% качества - 10 баллов. Берется среднее значение по всем классам, качество прибавляется к 100% справившихся. При условии 100% успеваемости"',
        0, 10),
       ('e4e6c509-9274-4f3c-89dd-bb8a2122714e', 'Результаты ОГЭ по предметам', 'caad2c5a-17df-46ec-a2ca-23dc554b10a6',
        'За каждого сдавшего на "4" - 0,8 балла, на "5" - 1 балл. При условии отсутствия расхождения результата ОГЭ и годовой оценки в сторону уменьшения на 2 балла . В противном случае все результаты обнуляются.',
        0, 30),
       ('9f9c4070-4578-475c-aedb-6f0271d23c5c', 'Результаты ЕГЭ по предметам', 'caad2c5a-17df-46ec-a2ca-23dc554b10a6',
        '"За каждого получившего: 63-72 - 0,5 б; 73-82 - 1 б; 83-89-1,5 б; 90-99 -3 б; 100 б - 5 б. При условии отсутствия обучающихся, имеющих итоговые ""4"" или ""5"" не преодолевших минимальный порог. В противном случае все результаты обнуляются. . Математика (база): ""5""-0,5 б; ""4""-0,3б. При условии 100% успеваемости"',
        0, 150);

INSERT INTO indicators_to_positions (indicator_id, position_id)
VALUES
  -- Иные непедагогические сотрудники
  ('c6f33b37-b426-4120-9d96-a157c61ca2ce', 'b07b8c68-1f3f-4af8-8a44-2d731ce77650'), -- Администр.оценка деятельности
  ('88d0dcf1-0103-41b2-a528-4c71047bc3be', 'b07b8c68-1f3f-4af8-8a44-2d731ce77650'), -- Общ знач функции
  ('9f5cdc41-e831-4cbc-ad3c-4ef9e2a8bbc7', 'b07b8c68-1f3f-4af8-8a44-2d731ce77650'), -- Выс.ур. дисц.
  -- Учитель
  ('a893d347-0b62-4a53-b021-b331ed3d7cb3', 'f332fbd7-e6a6-468e-8096-fb754db71c72'), -- Полож.динамика среднего балла
  ('ce029498-301e-409c-93bd-dcb4bb3501f0', 'f332fbd7-e6a6-468e-8096-fb754db71c72'), -- Результаты независ диагностик
  ('e4e6c509-9274-4f3c-89dd-bb8a2122714e', 'f332fbd7-e6a6-468e-8096-fb754db71c72'), -- Результаты ОГЭ
  ('9f9c4070-4578-475c-aedb-6f0271d23c5c', 'f332fbd7-e6a6-468e-8096-fb754db71c72'); -- Результаты ЕГЭ

INSERT INTO facts (indicator_id, pointsSuggested, pointsApproved, user_id, creator_id)
VALUES
  ('c6f33b37-b426-4120-9d96-a157c61ca2ce',5,0,'af61adb1-63ed-4123-bbab-63eceda8d2c2','af61adb1-63ed-4123-bbab-63eceda8d2c2'), -- Морозов административная оценка
  ('88d0dcf1-0103-41b2-a528-4c71047bc3be',8,0,'af61adb1-63ed-4123-bbab-63eceda8d2c2','af61adb1-63ed-4123-bbab-63eceda8d2c2'), -- Морозов обща зн.
  ('9f5cdc41-e831-4cbc-ad3c-4ef9e2a8bbc7',10,0,'af61adb1-63ed-4123-bbab-63eceda8d2c2','af61adb1-63ed-4123-bbab-63eceda8d2c2'), -- Морозов выс ур дисц.

  ('c6f33b37-b426-4120-9d96-a157c61ca2ce',5,0,'3b74235a-c24b-4dfc-96fe-47088e7371c0','3b74235a-c24b-4dfc-96fe-47088e7371c0'), -- Трофимов административная оценка
  ('88d0dcf1-0103-41b2-a528-4c71047bc3be',8,0,'3b74235a-c24b-4dfc-96fe-47088e7371c0','3b74235a-c24b-4dfc-96fe-47088e7371c0'), -- Трофимов Общ.зн
  ('9f5cdc41-e831-4cbc-ad3c-4ef9e2a8bbc7',10,0,'3b74235a-c24b-4dfc-96fe-47088e7371c0','3b74235a-c24b-4dfc-96fe-47088e7371c0'), -- Трофимов выс ур дисц.

  ('a893d347-0b62-4a53-b021-b331ed3d7cb3',5,0,'66b742d7-5189-40c3-b104-77a1f1c12ad5','66b742d7-5189-40c3-b104-77a1f1c12ad5'), -- Стаканчикова Положит.динамика среднего балла
  ('ce029498-301e-409c-93bd-dcb4bb3501f0',7,0,'66b742d7-5189-40c3-b104-77a1f1c12ad5','66b742d7-5189-40c3-b104-77a1f1c12ad5'), -- Стаканчикова результаты незхависимых диагностик
  ('e4e6c509-9274-4f3c-89dd-bb8a2122714e',20,0,'66b742d7-5189-40c3-b104-77a1f1c12ad5','66b742d7-5189-40c3-b104-77a1f1c12ad5'), -- Стаканчикова Результаты ОГЭ
  ('9f9c4070-4578-475c-aedb-6f0271d23c5c',50,0,'66b742d7-5189-40c3-b104-77a1f1c12ad5','66b742d7-5189-40c3-b104-77a1f1c12ad5'); -- Стаканчикова Результаты ЕГЭ