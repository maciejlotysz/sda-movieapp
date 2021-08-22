CREATE TABLE movie_copies
(
    id              BIGSERIAL PRIMARY KEY,
    movie_id        BIGINT not null,
    copy_id         uuid DEFAULT uuid_generate_v4(),
    status          VARCHAR(20),
    disc_type       VARCHAR(20),
    CONSTRAINT movie_copies__movie_id_fk
        FOREIGN KEY(movie_id)
            REFERENCES movies(id)
)