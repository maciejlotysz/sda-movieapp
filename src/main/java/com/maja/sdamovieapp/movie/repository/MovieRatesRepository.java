package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.movie.entity.MovieRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRatesRepository extends JpaRepository<MovieRates, Long> {
}
