package com.maja.sdamovieapp.movie.controller;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import com.maja.sdamovieapp.application.exceptions.RequestExceptionHandler;
import com.maja.sdamovieapp.movie.dto.CopyRequestDTO;
import com.maja.sdamovieapp.movie.dto.MovieCopyDTO;
import com.maja.sdamovieapp.movie.dto.MovieRequestDTO;
import com.maja.sdamovieapp.movie.service.MovieAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MovieAdminController {

    private final MovieAdminService adminService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addMovie")
    public ResponseEntity<MovieRequestDTO> addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        adminService.addMovie(movieRequestDTO);
        return new ResponseEntity<>(RequestExceptionHandler.buildHeadersWithMessage(ErrorCode.MOVIE_CREATED.internalCode), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getMoviesList")
    public List<MovieCopyDTO> getListOfMoviesByTitle(@Valid String title) {
        return adminService.getMoviesByTitle(title);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{movieId}/addCopy")
    public void addCopy(@Valid @RequestBody CopyRequestDTO copyRequestDTO, @PathVariable Long movieId) {
        adminService.addCopy(movieId, copyRequestDTO);
    }
}
