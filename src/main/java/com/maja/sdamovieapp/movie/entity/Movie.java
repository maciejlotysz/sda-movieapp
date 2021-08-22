package com.maja.sdamovieapp.movie.entity;

import com.maja.sdamovieapp.copy.entity.MovieCopy;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private String title;

    @NotNull
    @Column(name = "premiere_year")
    private Integer premiereYear;

    @NotBlank
    private String director;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "movie_genre")
    private MovieGenreEnum movieGenre;

    @Size(max = 500)
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovieRates> movieRates = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "movie", cascade ={CascadeType.MERGE, CascadeType.PERSIST})
    private List<MovieCopy> copies = new ArrayList<>();
}
