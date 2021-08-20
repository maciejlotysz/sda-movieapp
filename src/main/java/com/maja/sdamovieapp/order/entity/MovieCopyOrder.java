package com.maja.sdamovieapp.order.entity;

import com.maja.sdamovieapp.copy.entity.MovieCopy;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "movie_copy_orders", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "movie_copy_id"}))
public class MovieCopyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "movie_copy_id")
    private MovieCopy movieCopy;
}
