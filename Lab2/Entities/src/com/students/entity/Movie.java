package com.students.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Movie extends AbstractEntity {

    private LocalDate releaseDate;

    private int duration;

    private long budget;

    private String description;

    private String genres;

    private String countries;

    private List<Director> directors = new LinkedList<>();

    private List<Character> characters = new LinkedList<>();

    public Movie() {
        super();
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenres() {
        return genres;
    }

    public String getCountries() {
        return countries;
    }

    public void addDirector(Director director)
    {
        directors.add(director);
    }
    public List<Director> getDirectors() {
        return directors;
    }

    public void addCharacter(Character character)
    {
        characters.add(character);
    }

    public List<Character> getCharacters() {
        return characters;
    }
    
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }
}
