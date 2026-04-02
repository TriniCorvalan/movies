package com.example.movies.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movies.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
