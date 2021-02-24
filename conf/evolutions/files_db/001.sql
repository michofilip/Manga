-- !Ups

CREATE TABLE IF NOT EXISTS files
(
    key            VARCHAR PRIMARY KEY,
    file_name      VARCHAR NOT NULL,
    content        BYTEA   NOT NULL,
    content_length BIGINT  NOT NULL,
    content_type   VARCHAR NOT NULL
);


-- !Downs

DROP TABLE IF EXISTS files CASCADE;
