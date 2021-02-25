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


-- !Downs

DROP TABLE IF EXISTS manga CASCADE;
DROP TABLE IF EXISTS chapter CASCADE;
DROP TABLE IF EXISTS page CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS manga_genre CASCADE;
DROP TABLE IF EXISTS franchise CASCADE;
DROP TABLE IF EXISTS manga_franchise CASCADE;
