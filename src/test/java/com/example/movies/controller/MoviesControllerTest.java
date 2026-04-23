package com.example.movies.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.movies.model.Movie;
import com.example.movies.service.MovieService;

import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(MoviesController.class)
class MoviesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setMovie() {
        movie = new Movie(1L, "The Dark Knight", "Christopher Nolan", "Action", 2008, "A superhero movie about a man who becomes a superhero and fights crime.");
    }

    @Test
    void getAllMoviesReturnsList() throws Exception {
        when(movieService.getAllMovies()).thenReturn(List.of(movie));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("The Dark Knight"));
    }

    @Test
    void getMovieByIdWhenFoundReturnsOk() throws Exception {
        when(movieService.getMovieById(1L)).thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("The Dark Knight"));
    }

    @Test
    void getMovieByIdWhenMissingReturnsNotFound() throws Exception {
        when(movieService.getMovieById(99L)).thenReturn(null);

        mockMvc.perform(get("/movies/99"))
                .andExpect(status().isNotFound());

        verify(movieService).getMovieById(99L);
    }

    @Test
    void createMovieReturnsCreatedResource() throws Exception {
        when(movieService.createMovie(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/movies")
                        .contentType(APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.director").value("Christopher Nolan"));

        verify(movieService).createMovie(any(Movie.class));
    }

    @Test
    void updateMovieWhenExistsReturnsOk() throws Exception {
        when(movieService.updateMovie(eq(1L), any(Movie.class))).thenReturn(movie);

        mockMvc.perform(put("/movies/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(movieService).updateMovie(eq(1L), any(Movie.class));
    }

    @Test
    void updateMovieWhenMissingReturnsNotFoundBody() throws Exception {
        when(movieService.updateMovie(eq(1L), any(Movie.class)))
                .thenThrow(new RuntimeException("Movie not found"));

        mockMvc.perform(put("/movies/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(movie)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(jsonPath("$.message").value("Movie not found"));
    }

    @Test
    void deleteMovieWhenExistsReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());

        verify(movieService).deleteMovie(1L);
    }

    @Test
    void deleteMovieWhenMissingReturnsNotFound() throws Exception {
        doThrow(new RuntimeException("Movie not found")).when(movieService).deleteMovie(1L);

        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(jsonPath("$.message").value("Movie not found"));
    }
}
