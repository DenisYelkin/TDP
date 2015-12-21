package com.students.entity;

import com.students.entity.utils.LocalDateAdapter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Director extends BaseEntity {

    private LocalDate birthDate;

    private String birthCountry;

    private List<Movie> movies = new LinkedList<>();

    public Director() {
        super();
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    @XmlElementWrapper(name = "movies")
    @XmlElement(name = "movie")
    @XmlIDREF
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
