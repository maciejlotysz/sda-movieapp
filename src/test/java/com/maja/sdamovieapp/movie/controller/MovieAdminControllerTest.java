package com.maja.sdamovieapp.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.movie.dto.MovieRequestDTO;
import com.maja.sdamovieapp.movie.enums.MovieGenreEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MovieAdminControllerTest extends ContainersEnvironment {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @WithMockUser(roles = "ADMIN")
    @Test
    @DisplayName("Jako Admin Dodaje film do bazay i zwraca 201(CREATED)")
    void shouldAddAndSaveMovieAndReturnStatus201() throws Exception {

        //when
        var response = mockMvc.perform(addMovie()).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    private MovieRequestDTO getRequestDTO() {
        return new MovieRequestDTO(
                "Król Artur: Legenda Miecza",
                2017,
                "Guy Ritchie",
                MovieGenreEnum.ADVENTURE,
                "Młody Artur zdobywa miecz Excalibur i wiedzę na temat swojego królewskiego pochodzenia. " +
                        "Przyłącza się do rebelii, aby pokonać tyrana, który zamordował jego rodziców."
        );
    }

    private MockHttpServletRequestBuilder addMovie() throws JsonProcessingException {
        return MockMvcRequestBuilders.post("/api/v1/admin/addMovie")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getRequestDTO()));
    }
}