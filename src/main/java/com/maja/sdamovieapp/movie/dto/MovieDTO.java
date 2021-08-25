package com.maja.sdamovieapp.movie.dto;

import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieDTO {
    private Long id;
    private String title;
    private Integer premiereYear;
    private String director;
    private MovieGenreEnum movieGenre;
    private String description;
}
