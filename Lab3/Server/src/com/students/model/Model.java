package com.students.model;

import com.students.entity.*;
import com.students.entity.Персонаж;
import com.students.util.XMLUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.xml.sax.SAXException;

@XmlRootElement
public class Model implements Serializable {

    public static final String PATH_TO_SCHEMA = "src/com/students/model/resources/modelSchema.xsd";
    
    @XmlElementWrapper(name = "characters")
    @XmlElement(name = "character")
    private final List<Персонаж> characters = new LinkedList<>();

    @XmlElementWrapper(name = "actors")
    @XmlElement(name = "actor")
    private final List<Actor> actors = new LinkedList<>();

    @XmlElementWrapper(name = "directors")
    @XmlElement(name = "director")
    private final List<Director> directors = new LinkedList<>();

    @XmlElementWrapper(name = "movies")
    @XmlElement(name = "movie")
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

    private void addCharacter(Персонаж character) {
        character.getActor().getCharacters().add(character);
        character.getMovie().getCharacters().add(character);
        characters.add(character);
    }

    private void editCharacter(Персонаж character) {
        removeCharacter(character);
        addCharacter(character);
    }

    private boolean removeCharacter(Персонаж character) {
        if (character == null) {
            return false;
        }
        character.getActor().getCharacters().remove(character);
        character.getMovie().getCharacters().remove(character);
        return characters.remove(character);
    }

    private List<Персонаж> getCharacters() {
        return characters;
    }

    private Movie getMovieById(final String id) {
        Optional<Movie> result = getMovies().stream()
                .filter((movie) -> movie.getId().equals(id)).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    private Персонаж getCharacterById(final String id) {
        Optional<Персонаж> result = getCharacters().stream()
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

    public BaseEntity getEntityById(EntityType type, String id) {
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

    public EntityType addEntity(BaseEntity entity) {
        synchronized (this) {
            if (entity instanceof Movie) {
                addMovie((Movie) entity);
                return EntityType.MOVIE;
            } else if (entity instanceof Персонаж) {
                addCharacter((Персонаж) entity);
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

    public EntityType editEntity(BaseEntity entity) {
        synchronized (this) {
            if (entity instanceof Movie) {
                editMovie((Movie) entity);
                return EntityType.MOVIE;
            } else if (entity instanceof Персонаж) {
                editCharacter((Персонаж) entity);
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

    public List<? extends BaseEntity> getEntities(EntityType type) {
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

    public void saveData(File file) throws IOException, JAXBException {
        synchronized (this) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                XMLUtils.write(fos, this);
            }
        }
    }

    public static Model loadData(File file) throws IOException, JAXBException, SAXException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return XMLUtils.read(Model.class, fis, PATH_TO_SCHEMA);
        }
    }
}
