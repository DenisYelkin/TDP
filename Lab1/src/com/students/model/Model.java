package com.students.model;

import com.students.entity.*;
import com.students.entity.Character;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Model implements Serializable {

    private final List<Character> characters = new LinkedList<>();

    private final List<Actor> actors = new LinkedList<>();

    private final List<Director> directors = new LinkedList<>();

    private final List<Movie> movies = new LinkedList<>();

    public Model() {
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public boolean removeActor(Actor actor) {
        for (Character character : actor.getCharacters()) {
            removeCharacter(character);
        }
        return actors.remove(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void addDirector(Director director) {
        for (Movie movie : director.getMovies()) {
            movie.addDirector(director);
        }
        directors.add(director);
    }

    public void editDirector(Director director) {
        removeDirector(director);
        addDirector(director);
    }

    public boolean removeDirector(Director director) {
        for (Movie movie : director.getMovies()) {
            movie.getDirectors().remove(director);
        }
        return directors.remove(director);
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        for (Director director : movie.getDirectors()) {
            director.getMovies().add(movie);
        }
        for (Character character : movie.getCharacters()) {
            addCharacter(character);
        }
    }

    public void editMovie(Movie movie) {
        removeMovie(movie);
        addMovie(movie);
    }

    public boolean removeMovie(Movie movie) {
        for (Director director : movie.getDirectors()) {
            director.getMovies().remove(movie);
        }
        for (Character character : movie.getCharacters()) {
            removeCharacter(character);
        }
        return movies.remove(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addCharacter(Character character) {
        character.getActor().getCharacters().add(character);
        character.getMovie().getCharacters().add(character);
        characters.add(character);
    }

    public void editCharacter(Character character) {
        removeCharacter(character);
        addCharacter(character);
    }

    public boolean removeCharacter(Character character) {
        character.getActor().getCharacters().remove(character);
        character.getMovie().getCharacters().remove(character);
        return characters.remove(character);
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
