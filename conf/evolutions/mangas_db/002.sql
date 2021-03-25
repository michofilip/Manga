-- !Ups

CREATE OR REPLACE VIEW v_manga_statistics AS
SELECT manga_id,
       sum(case when is_in_collection then 1 else 0 end) as collections,
       sum(case when is_read then 1 else 0 end)          as reads,
       sum(case when is_favorite then 1 else 0 end)      as favorites,
       count(score)                                      as votes,
       avg(score)                                        as avg_score
FROM account_manga am
GROUP BY manga_id;


-- !Downs

DROP VIEW IF EXISTS v_manga_statistics;
