-- !Ups

CREATE TABLE IF NOT EXISTS collection
(
    id      SERIAL PRIMARY KEY,
    user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS collection_manga
(
    collection_id INT NOT NULL,
    manga_id      INT NOT NULL,

    CONSTRAINT collection_fk FOREIGN KEY (collection_id) REFERENCES collection (id),
    CONSTRAINT manga_fk FOREIGN KEY (manga_id) REFERENCES mangas (id)
);


-- !Downs

DROP TABLE IF EXISTS collection CASCADE;
DROP TABLE IF EXISTS collection_manga CASCADE;
