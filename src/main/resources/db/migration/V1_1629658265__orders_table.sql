CREATE TABLE orders
(
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT not null,
    rent_start_date     DATE,
    rent_end_date       DATE,
    rent_price_per_day  DECIMAL,
    total_price         DECIMAL,
    order_status        VARCHAR(50),
    CONSTRAINT orders__user_id_fk
        FOREIGN KEY(user_id)
            REFERENCES users(id)
)