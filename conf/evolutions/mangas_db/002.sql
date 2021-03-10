-- !Ups

CREATE OR REPLACE VIEW v_manga_avg_score AS
SELECT manga_id, avg(score) AS avg_score
FROM account_manga am
WHERE score IS NOT NULL
group by manga_id;


-- !Downs

DROP VIEW IF EXISTS v_manga_avg_score;
