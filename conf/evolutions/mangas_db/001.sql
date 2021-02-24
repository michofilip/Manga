-- !Ups

CREATE TABLE IF NOT EXISTS mangas
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS chapters
(
    id         SERIAL PRIMARY KEY,
    number     INT     NOT NULL,
    sub_number INT     NOT NULL,
    title      VARCHAR NOT NULL,
    manga_id   INT     NOT NULL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES mangas (id)
);

CREATE TABLE IF NOT EXISTS pages
(
    id         SERIAL PRIMARY KEY,
    key        VARCHAR NOT NULL,
    chapter_id INT     NOT NULL,
    page_nr    INT     NOT NULL,

    CONSTRAINT chapter_fk FOREIGN KEY (chapter_id) REFERENCES chapters (id)
);

CREATE TABLE IF NOT EXISTS genres
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS manga_genres
(
    manga_id INT NOT NULL,
    genre_id INT NOT NULL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES mangas (id),
    CONSTRAINT genre_fk FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE IF NOT EXISTS franchises
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS manga_franchises
(
    manga_id     INT NOT NULL,
    franchise_id INT NOT NULL,

    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES mangas (id),
    CONSTRAINT franchise_fk FOREIGN KEY (franchise_id) REFERENCES franchises (id)
);


-- !Downs

DROP TABLE IF EXISTS mangas CASCADE;
DROP TABLE IF EXISTS chapters CASCADE;
DROP TABLE IF EXISTS pages CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS manga_genres CASCADE;
DROP TABLE IF EXISTS franchises CASCADE;
DROP TABLE IF EXISTS manga_franchises CASCADE;
