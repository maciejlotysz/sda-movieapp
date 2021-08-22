CREATE TABLE movie_rates
(
    id              BIGSERIAL PRIMARY KEY,
    movie_id        BIGINT not null,
    user_id         BIGINT not null,
    rate            BIGINT,
    comment         VARCHAR(255),
    CONSTRAINT movie_rates__movie_id_fk
        FOREIGN KEY(movie_id)
            REFERENCES movies(id),
    CONSTRAINT movie_rates__user_id_fk
        FOREIGN KEY(user_id)
            REFERENCES users(id)
)