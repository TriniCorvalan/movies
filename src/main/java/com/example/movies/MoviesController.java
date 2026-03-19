package com.example.movies;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController {
    private List<Movie> movies = new ArrayList<>();

    public MoviesController() {
        // initialize the movies list with some movies
        movies.add(new Movie(1, "The Dark Knight", "Christopher Nolan", "Action", 2008, "A superhero movie about a superhero who saves the world from a bad guy."));
        movies.add(new Movie(2, "The Dark Knight Rises", "Christopher Nolan", "Action", 2012, "A superhero movie about a superhero who saves the world from a bad guy."));
        movies.add(new Movie(3, "The Notebook", "Nick Cassavetes", "Romance", 2004, "A romantic movie about a man and a woman who fall in love."));
        movies.add(new Movie(4, "The Lord of the Rings: The Return of the King", "Peter Jackson", "Fantasy", 2003, "A fantasy movie about a man who goes on a journey to destroy a ring."));
        movies.add(new Movie(5, "Pride and Prejudice", "Joe Wright", "Romance", 2005, "A romantic movie about a woman who falls in love with a man who is not who he seems to be."));
    }
    
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return movies;
    }

    @GetMapping("/movies/{id}")
    public Movie getMovieById(@PathVariable int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {  
                return movie;
            }
        }
        return null;
    }
}
