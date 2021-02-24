-- !Ups

CREATE TABLE IF NOT EXISTS files
(
    key     VARCHAR PRIMARY KEY,
    name    VARCHAR NOT NULL,
    size    BIGINT  NOT NULL,
    content BYTEA   NOT NULL
);


-- !Downs

DROP TABLE IF EXISTS files CASCADE;
