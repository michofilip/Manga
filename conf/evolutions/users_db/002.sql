-- !Ups

INSERT INTO "user"(login)
VALUES ('test_user');

-- !Downs

TRUNCATE TABLE "user" CASCADE;
