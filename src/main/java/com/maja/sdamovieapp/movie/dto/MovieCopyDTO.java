package com.maja.sdamovieapp.movie.dto;

import lombok.Value;

@Value
public class MovieCopyDTO {

    Long id;
    String title;
    String director;
    Integer premiereYear;
}
