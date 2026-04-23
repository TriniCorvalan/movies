package com.example.movies.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.movies.model.Movie;
import com.example.movies.repository.MovieRepository;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieServiceImpl service;

    private Movie movie;

    @BeforeEach
    void setMovie() {
        movie = new Movie(1L, "The Dark Knight", "Christopher Nolan", "Action", 2008, "A superhero movie about a man who becomes a superhero and fights crime.");
    }

    @Test
    void testGetAllMovies() {
        List<Movie> expected = Arrays.asList(movie);
        when(repository.findAll()).thenReturn(expected);
        assertEquals(expected, service.getAllMovies());
    }

    @Test
    void testGetMovieById() {
        when(repository.findById(1L)).thenReturn(Optional.of(movie));
        assertEquals(movie, service.getMovieById(1L));
    }

    @Test
    void testCreateMovie() {
        when(repository.save(movie)).thenReturn(movie);
        assertEquals(movie, service.createMovie(movie));
    }

    @Test
    void testUpdateMovieExists() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(movie)).thenReturn(movie);
        Movie result = service.updateMovie(1L, movie);
        assertEquals(1L, movie.getId());
        assertEquals(movie, result);
        verify(repository).save(movie);
    }

    @Test
    void testUpdateMovieNotExists() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.updateMovie(1L, movie));
        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteMovie() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deleteMovie(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteMovieNotExists() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.deleteMovie(1L));
        verify(repository, never()).deleteById(1L);
    }
}