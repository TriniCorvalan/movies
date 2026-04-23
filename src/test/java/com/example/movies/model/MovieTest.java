package com.example.movies.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MovieTest {
    @Test
    void testGettersAndSetters() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Dark Knight");
        movie.setDirector("Christopher Nolan");
        movie.setGenre("Action");
        movie.setYear(2008);
        movie.setSynopsis("A superhero movie about a man who becomes a superhero and fights crime.");
        assertEquals(1L, movie.getId());
        assertEquals("The Dark Knight", movie.getTitle());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals("Action", movie.getGenre());
        assertEquals(2008, movie.getYear());
        assertEquals("A superhero movie about a man who becomes a superhero and fights crime.", movie.getSynopsis());
    }
}