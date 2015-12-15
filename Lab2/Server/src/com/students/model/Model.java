package com.students.model;

import com.students.entity.*;
import com.students.entity.Character;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Model implements Serializable {

    private final List<Character> characters = new LinkedList<>();

    private final List<Actor> actors = new LinkedList<>();

    private final List<Director> directors = new LinkedList<>();

    private final List<Movie> movies = new LinkedList<>();

    public Model() {
    }

    private void addActor(Actor actor) {
        actors.add(actor);
    }

    private void editActor(Actor actor) {
        actors.remove(actor);
        actors.add(actor);
    }

    private boolean removeActor(Actor actor) {
        if (actor == null) {
            return false;
        }
        actor.getCharacters().stream().forEach((character) -> {
            removeCharacter(character);
        });
        return actors.remove(actor);
    }

    private List<Actor> getActors() {
        return actors;
    }

    private void addDirector(Director director) {
        director.getMovies().stream().forEach((movie) -> {
            movie.addDirector(director);
        });
        directors.add(director);
    }

    public void editDirector(Director director) {
        removeDirector(director);
        addDirector(director);
    }

    private boolean removeDirector(Director director) {
        if (director == null) {
            return false;
        }
        director.getMovies().stream().forEach((movie) -> {
            movie.getDirectors().remove(director);
        });
        return directors.remove(director);
    }

    private List<Director> getDirectors() {
        return directors;
    }

    private void addMovie(Movie movie) {
        movies.add(movie);
        movie.getDirectors().stream().forEach((director) -> {
            director.getMovies().add(movie);
        });
        movie.getCharacters().stream().forEach((character) -> {
            addCharacter(character);
        });
    }

    private void editMovie(Movie movie) {
        removeMovie(movie);
        addMovie(movie);
    }

    private boolean removeMovie(Movie movie) {
        if (movie == null) {
            return false;
        }
        movie.getDirectors().stream().forEach((director) -> {
            director.getMovies().remove(movie);
        });
        movie.getCharacters().stream().forEach((character) -> {
            removeCharacter(character);
        });
        return movies.remove(movie);
    }

    private List<Movie> getMovies() {
        return movies;
    }

    private void addCharacter(Character character) {
        character.getActor().getCharacters().add(character);
        character.getMovie().getCharacters().add(character);
        characters.add(character);
    }

    private void editCharacter(Character character) {
        removeCharacter(character);
        addCharacter(character);
    }

    private boolean removeCharacter(Character character) {
        if (character == null) {
            return false;
        }
        character.getActor().getCharacters().remove(character);
        character.getMovie().getCharacters().remove(character);
        return characters.remove(character);
    }

    private List<Character> getCharacters() {
        return characters;
    }

    private Movie getMovieById(final String id) {
        Optional<Movie> result = getMovies().stream()
                .filter((movie) -> movie.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    private Character getCharacterById(final String id) {
        Optional<Character> result = getCharacters().stream()
                .filter((character) -> character.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    private Director getDirectorById(final String id) {
        Optional<Director> result = getDirectors().stream()
                .filter((director) -> director.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    private Actor getActorById(final String id) {
        Optional<Actor> result = getActors().stream()
                .filter((actor) -> actor.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public AbstractEntity getEntityById(EntityType type, String id) {
        synchronized (this) {
            switch (type) {
                case MOVIE:
                    return getMovieById(id);
                case ACTOR:
                    return getActorById(id);
                case CHARACTER:
                    return getCharacterById(id);
                case DIRECTOR:
                    return getDirectorById(id);
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public boolean removeEntityById(EntityType type, String id) {
        synchronized (this) {
            switch (type) {
                case MOVIE:
                    return removeMovie(getMovieById(id));
                case ACTOR:
                    return removeActor(getActorById(id));
                case CHARACTER:
                    return removeCharacter(getCharacterById(id));
                case DIRECTOR:
                    return removeDirector(getDirectorById(id));
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public EntityType addEntity(AbstractEntity entity) {
        synchronized (this) {
            if (entity instanceof Movie) {
                addMovie((Movie) entity);
                return EntityType.MOVIE;
            } else if (entity instanceof Character) {
                addCharacter((Character) entity);
                return EntityType.CHARACTER;
            } else if (entity instanceof Actor) {
                addActor((Actor) entity);
                return EntityType.ACTOR;
            } else if (entity instanceof Director) {
                addDirector((Director) entity);
                return EntityType.DIRECTOR;
            } else {
                throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public EntityType editEntity(AbstractEntity entity) {
        synchronized (this) {
            if (entity instanceof Movie) {
                editMovie((Movie) entity);
                return EntityType.MOVIE;
            } else if (entity instanceof Character) {
                editCharacter((Character) entity);
                return EntityType.CHARACTER;
            } else if (entity instanceof Actor) {
                editActor((Actor) entity);
                return EntityType.ACTOR;
            } else if (entity instanceof Director) {
                editDirector((Director) entity);
                return EntityType.DIRECTOR;
            } else {
                throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public List<? extends AbstractEntity> getEntities(EntityType type) {
        synchronized (this) {
            switch (type) {
                case MOVIE:
                    return getMovies();
                case ACTOR:
                    return getActors();
                case CHARACTER:
                    return getCharacters();
                case DIRECTOR:
                    return getDirectors();
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public void saveData(File file) throws IOException {        
        synchronized (this) {            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(this);
                oos.flush();
            }
        }
    }

    public static Model loadData(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Model) ois.readObject();
        }
    }
}
