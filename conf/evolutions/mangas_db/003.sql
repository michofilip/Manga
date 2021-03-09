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

INSERT INTO account(user_id, is_active)
VALUES (1, true);

INSERT INTO tag(account_id, tag)
VALUES (1, 'TEST_TAG');

INSERT INTO account_manga(account_id, manga_id, is_in_collection, is_read, is_favorite, score)
VALUES (1, 1, true, false, true, 5);

INSERT INTO manga_tag(manga_id, tag_id)
VALUES (1, 1);

-- !Downs

TRUNCATE TABLE manga CASCADE;
TRUNCATE TABLE chapter CASCADE;
TRUNCATE TABLE page CASCADE;
TRUNCATE TABLE genre CASCADE;
TRUNCATE TABLE manga_genre CASCADE;
TRUNCATE TABLE franchise CASCADE;
TRUNCATE TABLE manga_franchise CASCADE;

TRUNCATE TABLE account CASCADE;
TRUNCATE TABLE tag CASCADE;
TRUNCATE TABLE account_manga CASCADE;
TRUNCATE TABLE manga_tag CASCADE;
