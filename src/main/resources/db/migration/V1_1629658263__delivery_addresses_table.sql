CREATE TABLE delivery_addresses
(
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT not null,
    is_default          boolean,
    street              VARCHAR(50) not null,
    building_number     VARCHAR(10) not null,
    appartement_number  VARCHAR (10),
    zip_code            VARCHAR(10) not null,
    city                VARCHAR(50) not null,
    CONSTRAINT delivery_addresses__user_id_fk
        FOREIGN KEY(user_id)
            REFERENCES users(id)
)