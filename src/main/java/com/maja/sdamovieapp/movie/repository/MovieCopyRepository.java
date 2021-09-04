package com.maja.sdamovieapp.movie.repository;

import com.maja.sdamovieapp.movie.entity.MovieCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieCopyRepository extends JpaRepository<MovieCopy, Long> {

    Optional<MovieCopy> findByMovie_Title(String title);
}
