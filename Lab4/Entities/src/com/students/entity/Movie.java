package com.students.entity;

import com.google.common.collect.Lists;
import com.students.entity.utils.LocalDateAdapter;
import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Movie extends BaseEntity {

    private LocalDate releaseDate;

    private int duration;

    private long budget;

    private String description;

    private String genres;

    private String countries;

    private List<String> directorIds = Lists.newArrayList();

    private List<String> идентификаторыПерсонажей = Lists.newArrayList();

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

    public void addDirector(String directorId) {
        directorIds.add(directorId);
    }

    @XmlElementWrapper(name = "directors")
    @XmlElement(name = "director")
    public List<String> getDirectors() {
        return directorIds;
    }

    public void addCharacter(String идентификаторПерсонажа) {
        идентификаторыПерсонажей.add(идентификаторПерсонажа);
    }

    @XmlElementWrapper(name = "characters")
    @XmlElement(name = "character")
    public List<String> getCharacters() {
        return идентификаторыПерсонажей;
    }

    public void setCharacters(List<String> идентификаторыПерсонажей) {
        this.идентификаторыПерсонажей = идентификаторыПерсонажей;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public void setDirectors(List<String> directorIds) {
        this.directorIds = directorIds;
    }
}
