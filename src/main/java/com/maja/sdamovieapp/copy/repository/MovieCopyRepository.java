package com.maja.sdamovieapp.copy.repository;

import com.maja.sdamovieapp.copy.entity.MovieCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieCopyRepository extends JpaRepository<MovieCopy, Long> {

    Optional<MovieCopy> findByMovie_Tittle(String tittle);
}
