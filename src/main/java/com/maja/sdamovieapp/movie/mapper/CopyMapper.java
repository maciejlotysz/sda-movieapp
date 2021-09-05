package com.maja.sdamovieapp.movie.mapper;

import com.maja.sdamovieapp.movie.dto.CopyRequestDTO;
import com.maja.sdamovieapp.movie.entity.MovieCopy;
import org.mapstruct.Mapper;

@Mapper
public interface CopyMapper {

    MovieCopy mapToMovieCopy(CopyRequestDTO copyRequestDTO);

}