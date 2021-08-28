package com.maja.sdamovieapp.movie.mapper;

import com.maja.sdamovieapp.movie.dto.MovieDTO;
import com.maja.sdamovieapp.movie.entity.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface MovieMapper {

    MovieDTO mapToMovieDto(Movie movie);

    Movie mapToMovie(MovieDTO movieDTO);

}
