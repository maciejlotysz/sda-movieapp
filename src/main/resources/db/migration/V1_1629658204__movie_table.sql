CREATE TABLE movies
(
    id                  BIGSERIAL PRIMARY KEY,
    title               VARCHAR(70) not null,
    premiere_year       BIGINT not null,
    director            VARCHAR(70) not null,
    movie_genre         VARCHAR(50) not null,
    description         VARCHAR(500)
)