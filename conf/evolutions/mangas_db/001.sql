-- !Ups

CREATE TABLE IF NOT EXISTS manga
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS chapter
(
    id         SERIAL PRIMARY KEY,
    number     INT     NOT NULL,
    sub_number INT     NOT NULL,
    title      VARCHAR NOT NULL,
    manga_id   INT     NOT NULL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES manga (id)
);

CREATE TABLE IF NOT EXISTS page
(
    id         SERIAL PRIMARY KEY,
    key        VARCHAR NOT NULL,
    chapter_id INT     NOT NULL,
    page_nr    INT     NOT NULL,

    CONSTRAINT chapter_fk FOREIGN KEY (chapter_id) REFERENCES chapter (id)
);

CREATE TABLE IF NOT EXISTS genre
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS manga_genre
(
    manga_id INT NOT NULL,
    genre_id INT NOT NULL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES manga (id),
    CONSTRAINT genre_fk FOREIGN KEY (genre_id) REFERENCES genre (id)
);

CREATE TABLE IF NOT EXISTS franchise
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS manga_franchise
(
    manga_id     INT NOT NULL,
    franchise_id INT NOT NULL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES manga (id),
    CONSTRAINT franchise_fk FOREIGN KEY (franchise_id) REFERENCES franchise (id)
);

CREATE TABLE IF NOT EXISTS account
(
    id        SERIAL PRIMARY KEY,
    user_id   INT     NOT NULL,
    is_active BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX user_idx ON account (user_id);

CREATE TABLE IF NOT EXISTS tag
(
    id         SERIAL PRIMARY KEY,
    account_id INT     NOT NULL,
    tag        VARCHAR NOT NULL,

    CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE UNIQUE INDEX account_tag_idx ON tag (account_id, tag);

CREATE TABLE IF NOT EXISTS account_manga
(
    account_id       INT     NOT NULL,
    manga_id         INT     NOT NULL,
    is_in_collection BOOLEAN NOT NULL,
    is_read          BOOLEAN NOT NULL,
    is_favorite      BOOLEAN NOT NULL,
    score            REAL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES manga (id),
    CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE IF NOT EXISTS manga_tag
(
    manga_id INT NOT NULL,
    tag_id   INT NOT NULL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES manga (id),
    CONSTRAINT tag_fk FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX manga_tag_idx ON manga_tag (manga_id, tag_id);


-- !Downs

DROP TABLE IF EXISTS manga CASCADE;
DROP TABLE IF EXISTS chapter CASCADE;
DROP TABLE IF EXISTS page CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS manga_genre CASCADE;
DROP TABLE IF EXISTS franchise CASCADE;
DROP TABLE IF EXISTS manga_franchise CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS account_manga CASCADE;
DROP TABLE IF EXISTS manga_tag CASCADE;
