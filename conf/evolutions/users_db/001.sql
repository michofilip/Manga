-- !Ups

CREATE TABLE IF NOT EXISTS "user"
(
    id    SERIAL PRIMARY KEY,
    login VARCHAR NOT NULL
);


-- !Downs

DROP TABLE IF EXISTS "user" CASCADE;