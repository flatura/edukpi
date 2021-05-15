DROP TABLE IF EXISTS users_roles CASCADE;
DROP TABLE IF EXISTS indicators_roles CASCADE;
DROP TABLE IF EXISTS files CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS indicators CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS facts CASCADE;
DROP TABLE IF EXISTS positions CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
login VARCHAR(15) NOT NULL UNIQUE,
password VARCHAR(45) NOT NULL,
name VARCHAR(30) NOT NULL,
surname VARCHAR(20) NOT NULL,
position_id UUID NOT NULL,
created TIMESTAMP NOT NULL,
last_seen TIMESTAMP NOT NULL);

CREATE TABLE roles (
id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
name VARCHAR(150) NOT NULL);

CREATE TABLE users_roles (
user_id UUID NOT NULL,
role_id UUID NOT NULL);

CREATE TABLE positions (
id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE);

CREATE TABLE indicators (
id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE,
description VARCHAR(255),
base INTEGER NOT NULL DEFAULT 5,
max INTEGER NOT NULL DEFAULT 5);

CREATE TABLE indicators_roles (
indicator_id UUID NOT NULL,
role_id UUID NOT NULL);

CREATE TABLE facts
(
  id              UUID    NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  indicator_id    UUID    NOT NULL,
  pointsSuggested INTEGER NOT NULL,
  pointsApproved  INTEGER NOT NULL,
  user_id         UUID    NOT NULL,
  creator_id      UUID    NOT NULL,
  created         TIME    NOT NULL DEFAULT 'now()',
  modified         TIME    NOT NULL DEFAULT 'now()',
  approved         TIME

);


CREATE TABLE files (
id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
path VARCHAR(255) NOT NULL,
fact_id UUID NOT NULL,
user_id UUID NOT NULL);

ALTER TABLE indicators_roles ADD CONSTRAINT indicators_roles_indicator_id_indicators_id FOREIGN KEY (indicator_id) REFERENCES indicators(id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE indicators_roles ADD CONSTRAINT indicators_roles_role_id_roles_id FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE facts ADD CONSTRAINT facts_indicator_id_indicators_id FOREIGN KEY (indicator_id) REFERENCES indicators(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE facts ADD CONSTRAINT facts_user_id_users_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE facts ADD CONSTRAINT facts_author_id_users_id FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET DEFAULT ON UPDATE CASCADE;
ALTER TABLE files ADD CONSTRAINT files_fact_id_facts_id FOREIGN KEY (fact_id) REFERENCES facts(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE files ADD CONSTRAINT files_user_id_users_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE users_roles ADD CONSTRAINT users_roles_user_id_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE users_roles ADD CONSTRAINT users_roles_role_id_role_id FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE CASCADE;