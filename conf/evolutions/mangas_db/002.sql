-- !Ups

INSERT INTO manga(title)
VALUES ('KonoSuba');

INSERT INTO chapter(number, sub_number, title, manga_id)
VALUES (1, 1, 'This Self Proclaimed Goddess and Reincarnation in Another World!', 1),
       (2, 1, 'Joy for This Beautiful Upper Class Job!', 1),
       (3, 1, 'Peace for These Undead! ', 1);

INSERT INTO page(key, chapter_id, page_nr)
VALUES ('page_1', 1, 1),
       ('page_2', 1, 2),
       ('page_3', 1, 3),
       ('page_4', 2, 1),
       ('page_5', 2, 2),
       ('page_6', 2, 3);

INSERT INTO genre(name)
VALUES ('Harem'),
       ('Isekai');

INSERT INTO manga_genre(manga_id, genre_id)
VALUES (1, 1),
       (1, 2);

INSERT INTO franchise(name)
VALUES ('KonoSuba');

INSERT INTO manga_franchise(manga_id, franchise_id)
VALUES (1, 1);

INSERT INTO user_manga(user_id, manga_id, read, score)
VALUES (1, 1, false, 5);

INSERT INTO user_tag(user_id, tag)
VALUES (1, 'KONOSUBA');

INSERT INTO user_manga_tag(user_id, manga_id, tag)
VALUES (1, 1, 'KONOSUBA');

-- !Downs

TRUNCATE TABLE manga CASCADE;
TRUNCATE TABLE chapter CASCADE;
TRUNCATE TABLE page CASCADE;
TRUNCATE TABLE genre CASCADE;
TRUNCATE TABLE manga_genre CASCADE;
TRUNCATE TABLE franchise CASCADE;
TRUNCATE TABLE manga_franchise CASCADE;
TRUNCATE TABLE user_manga CASCADE;
TRUNCATE TABLE user_manga_tag CASCADE;
