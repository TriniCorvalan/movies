package com.example.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movies.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
