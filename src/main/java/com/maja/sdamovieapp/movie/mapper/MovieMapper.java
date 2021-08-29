package com.maja.sdamovieapp.movie.mapper;

import com.maja.sdamovieapp.movie.dto.MovieDTO;
import com.maja.sdamovieapp.movie.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {

    MovieDTO mapToMovieDto(Movie movie);

    List<MovieDTO> mapToMovieDtoList(List<Movie> movies);
}
