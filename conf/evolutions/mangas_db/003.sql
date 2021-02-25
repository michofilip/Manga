-- !Ups

INSERT INTO mangas(title)
VALUES ('KonoSuba');

INSERT INTO chapters(number, sub_number, title, manga_id)
VALUES (1, 1, 'This Self Proclaimed Goddess and Reincarnation in Another World!', 1),
       (2, 1, 'Joy for This Beautiful Upper Class Job!', 1),
       (3, 1, 'Peace for These Undead! ', 1);

INSERT INTO pages(key, chapter_id, page_nr)
VALUES ('page_1', 1, 1),
       ('page_2', 1, 2),
       ('page_3', 1, 3),
       ('page_4', 2, 1),
       ('page_5', 2, 2),
       ('page_6', 2, 3);

INSERT INTO genres(name)
VALUES ('Harem'),
       ('Isekai');

INSERT INTO manga_genres(manga_id, genre_id)
VALUES (1, 1),
       (1, 2);

INSERT INTO franchises(name)
VALUES ('KonoSuba');

INSERT INTO manga_franchises(manga_id, franchise_id)
VALUES (1, 1);

-- !Downs

TRUNCATE TABLE mangas CASCADE;
TRUNCATE TABLE chapters CASCADE;
TRUNCATE TABLE pages CASCADE;
TRUNCATE TABLE genres CASCADE;
TRUNCATE TABLE manga_genres CASCADE;
TRUNCATE TABLE franchises CASCADE;
TRUNCATE TABLE manga_franchises CASCADE;
