package com.students.entity;

import javax.xml.bind.annotation.XmlIDREF;

public class Персонаж extends BaseEntity {

    private Actor actor;

    private Movie movie;

    private String description;

    public Персонаж() {

    }

    @XmlIDREF
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @XmlIDREF
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
