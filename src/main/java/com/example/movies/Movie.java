package com.example.movies;

public class Movie {
    private int id;
    private String title;
    private String director;
    private String genre;
    private int year;
    private String synopsis;

    // Setters and Getters
    public Movie(int id, String title, String director, String genre, int year, String synopsis) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
        this.synopsis = synopsis;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    
    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }
    
    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }
}
