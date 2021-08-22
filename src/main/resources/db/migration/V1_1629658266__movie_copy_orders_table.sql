CREATE TABLE movie_copy_orders
(
    id          BIGSERIAL PRIMARY KEY,
    order_id    BIGINT not null,
    copy_id     BIGINT not null,
    CONSTRAINT movie_copy_order__order_id_fk
        FOREIGN KEY(order_id)
            REFERENCES orders(id),
    CONSTRAINT movie_copy_orders__copy_id_fk
        FOREIGN KEY(copy_id)
            REFERENCES movie_copies(id)
)