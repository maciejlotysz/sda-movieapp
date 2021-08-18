package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
