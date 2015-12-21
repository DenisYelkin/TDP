package com.students.entity;

import com.students.entity.utils.LocalDateAdapter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Movie extends BaseEntity {

    private LocalDate releaseDate;

    private int duration;

    private long budget;

    private String description;

    private String genres;

    private String countries;

    private List<Director> directors = new LinkedList<>();

    private List<Персонаж> characters = new LinkedList<>();

    public Movie() {
        super();
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

    public void addDirector(Director director) {
        directors.add(director);
    }

    @XmlElementWrapper(name = "directors")
    @XmlElement(name = "director")
    @XmlIDREF
    public List<Director> getDirectors() {
        return directors;
    }

    public void addCharacter(Персонаж character) {
        characters.add(character);
    }

    @XmlElementWrapper(name = "characters")
    @XmlElement(name = "character")
    @XmlIDREF
    public List<Персонаж> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Персонаж> characters) {
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
