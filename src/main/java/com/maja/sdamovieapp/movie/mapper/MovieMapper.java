package com.maja.sdamovieapp.movie.mapper;

import com.maja.sdamovieapp.movie.dto.MovieCopyDTO;
import com.maja.sdamovieapp.movie.dto.MovieDTO;
import com.maja.sdamovieapp.movie.dto.MovieRequestDTO;
import com.maja.sdamovieapp.movie.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {

    Movie mapToMovie(MovieRequestDTO requestDTO);

    List<MovieDTO> mapToMovieDtoList(List<Movie> movies);

    List<MovieCopyDTO> mapToMovieCopyDtoList(List<Movie> movies);
}
