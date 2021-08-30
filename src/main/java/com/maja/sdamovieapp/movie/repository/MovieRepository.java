package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.movie.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findMovieByTitle(String title);

    List<Movie> findAllByOrderByAddedAtDesc(Pageable pageable);

    Optional<Movie> findMovieByTitleAndPremiereYearAndDirector(String title, int year, String director);
}
