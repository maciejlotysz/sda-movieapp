package com.maja.sdamovieapp.movie.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tittle;

    @NotBlank
    @Column(name = "premiere_year")
    private int premiereYear;

    @NotBlank
    private String director;

    @NotBlank
    @Column(name = "movie_genre")
    private String movieGenre;

    @Size(max = 500)
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<MovieRates> movieRates = new ArrayList<>();
}
