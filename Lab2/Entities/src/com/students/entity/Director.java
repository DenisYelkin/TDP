package com.students.entity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Director extends AbstractEntity {

    private LocalDate birthDate;

    private String birthCountry;

    private List<Movie> movies = new LinkedList<>();

    public Director() {
        super();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public void addMovie(Movie movie)
    {
        movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }
    
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
