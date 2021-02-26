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

CREATE TABLE IF NOT EXISTS user_manga
(
    user_id  INT     NOT NULL,
    manga_id INT     NOT NULL,
    read     BOOLEAN NOT NULL,
    score    INT,

    CONSTRAINT user_manga_pk PRIMARY KEY (user_id, manga_id),
    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES manga (id)
);

CREATE TABLE IF NOT EXISTS user_tag
(
    user_id INT     NOT NULL,
    tag     VARCHAR NOT NULL,

    CONSTRAINT user_folder_pk PRIMARY KEY (user_id, tag)
);

CREATE TABLE IF NOT EXISTS user_manga_tag
(
    user_id  INT     NOT NULL,
    manga_id INT     NOT NULL,
    tag      VARCHAR NOT NULL,

    CONSTRAINT user_manga_fk FOREIGN KEY (user_id, manga_id) REFERENCES user_manga (user_id, manga_id),
    CONSTRAINT tag_fk FOREIGN KEY (user_id, tag) REFERENCES user_tag (user_id, tag)
);

-- !Downs

DROP TABLE IF EXISTS manga CASCADE;
DROP TABLE IF EXISTS chapter CASCADE;
DROP TABLE IF EXISTS page CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS manga_genre CASCADE;
DROP TABLE IF EXISTS franchise CASCADE;
DROP TABLE IF EXISTS manga_franchise CASCADE;
DROP TABLE IF EXISTS user_manga CASCADE;
DROP TABLE IF EXISTS user_tag CASCADE;
DROP TABLE IF EXISTS user_manga_tag CASCADE;
