package com.students.entity;

public class Персонаж extends BaseEntity {

    private String actorId;

    private String movieId;

    private String description;

    public Персонаж() {

    }

    public String getActor() {
        return actorId;
    }

    public void setActor(String actorId) {
        this.actorId = actorId;
    }

    public String getMovie() {
        return movieId;
    }

    public void setMovie(String movieId) {
        this.movieId = movieId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
