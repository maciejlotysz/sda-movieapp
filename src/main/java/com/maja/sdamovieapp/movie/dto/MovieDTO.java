package com.maja.sdamovieapp.movie.dto;

import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import lombok.Value;

@Value
public class MovieDTO {

    Long id;
    String title;
    Integer premiereYear;
    String director;
    MovieGenreEnum movieGenre;
    String description;
}
