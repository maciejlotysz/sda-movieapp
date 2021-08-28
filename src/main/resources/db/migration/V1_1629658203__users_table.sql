CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(50) not null,
    last_name       VARCHAR (50) not null,
    login           VARCHAR (50) not null unique,
    email           VARCHAR (75) not null unique,
    password        VARCHAR (100) not null,
    is_active       boolean not null,
    client_type     VARCHAR(30) not null
)