package com.maja.sdamovieapp.movie.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "movie_rates")
public class MovieRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private int rate;

    @Size(max = 255)
    private String comment;
}
