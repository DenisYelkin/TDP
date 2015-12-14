package com.students.controller;

import com.students.entity.*;
import com.students.entity.Character;
import com.students.model.Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private Model model;

    public Controller() {
        model = new Model();
    }

    public void addActor(Actor actor) {
        model.addActor(actor);
    }

    public boolean removeActor(String id) {
        Actor actor = getActorById(id);

        return model.removeActor(actor);
    }

    public List<Actor> getActors() {
        return model.getActors();
    }

    public void addDirector(Director director) {
        model.addDirector(director);
    }

    public void editDirector(Director director) {
        model.editDirector(director);
    }

    public boolean removeDirector(String id) {
        Director director = getDirectorById(id);

        return model.removeDirector(director);
    }

    public List<Director> getDirectors() {
        return model.getDirectors();
    }

    public void addMovie(Movie movie) {
        model.addMovie(movie);
    }

    public void editMovie(Movie movie) {
        model.editMovie(movie);
    }

    public boolean removeMovie(String id) {
        Movie movie = getMovieById(id);
        return model.removeMovie(movie);
    }

    public List<Movie> getMovies() {
        return model.getMovies();
    }

    public void addCharacter(Character character) {
        model.addCharacter(character);
    }

    public void editCharacter(Character character) {
        model.editCharacter(character);
    }

    public boolean removeCharacter(String id) {
        Character character = getCharacterById(id);
        return model.removeCharacter(character);
    }

    public List<Character> getCharacters() {
        return model.getCharacters();
    }

    public Movie getMovieById(final String id) {
        Optional<Movie> result = model.getMovies().stream()
                .filter((movie) -> movie.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public Character getCharacterById(final String id) {
        Optional<Character> result = model.getCharacters().stream()
                .filter((character) -> character.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public Director getDirectorById(final String id) {
        Optional<Director> result = model.getDirectors().stream()
                .filter((director) -> director.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public Actor getActorById(final String id) {
        Optional<Actor> result = model.getActors().stream()
                .filter((actor) -> actor.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public void saveData(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(model);
            oos.flush();
        }
    }
    
    public void loadData(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            model = (Model) ois.readObject();
        }
    }
}
