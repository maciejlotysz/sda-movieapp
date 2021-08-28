package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.movie.entity.MovieRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRatesRepository extends JpaRepository<MovieRates, Long> {
}
