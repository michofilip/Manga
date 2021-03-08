-- !Ups

CREATE OR REPLACE VIEW manga_avg_score AS
SELECT manga_id, avg(score) AS avg_score
FROM account_manga am
group by manga_id;


-- !Downs

DROP VIEW IF EXISTS manga_avg_score;