package com.students.model;

import com.google.common.collect.Lists;
import com.students.cache.EntitiesCache;
import com.students.entity.*;
import com.students.entity.Персонаж;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Model implements Serializable {

    private Connection connection;
    private EntitiesCache cache;

    public Model() {
        try {
            cache = new EntitiesCache();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "123");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Подключение не удалось!");
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addActor(Actor actor) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "INSERT INTO actor(id, name, birth_date, birth_country) VALUES (?, ?, ?, ?)")) {
            updStatement.setString(1, actor.getId());
            updStatement.setString(2, actor.getName());
            updStatement.setDate(3, new Date(actor.getBirthDate().toEpochDay()));
            updStatement.setString(4, actor.getBirthCountry());
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private void editActor(Actor actor) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "UPDATE actor SET name=?, birth_date=?, birth_country=? WHERE id = ?")) {
            updStatement.setString(1, actor.getName());
            updStatement.setDate(2, new Date(actor.getBirthDate().toEpochDay()));
            updStatement.setString(3, actor.getBirthCountry());
            updStatement.setString(4, actor.getId());
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private void removeActor(String actorId) {
        if (actorId == null) {
            return;
        }
        try (PreparedStatement updStatement = connection.prepareStatement("DELETE FROM actor WHERE id = ?")) {
            updStatement.setString(1, actorId);
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private List<String> cascadeQuery(String selectColumnName, String tableName, String whereColumnName, String entityId) throws SQLException {
        List<String> result = Lists.newArrayList();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + selectColumnName + " FROM " + tableName + " WHERE " + whereColumnName + " = ?")) {
            preparedStatement.setString(1, entityId);
            try (ResultSet cascadeRs = preparedStatement.executeQuery()) {
                while (cascadeRs.next()) {
                    result.add(cascadeRs.getString(1));
                }
            }
        }
        return result;
    }

    private List<BaseEntity> getActors() {
        List<BaseEntity> result = new ArrayList<>();
        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM actor")) {
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setId(rs.getString(1));
                actor.setName(rs.getString(2));
                actor.setBirthDate(rs.getDate(3).toLocalDate());
                actor.setBirthCountry(rs.getString(4));
                actor.setCharacters(cascadeQuery("id", "character", "actor_id", actor.getId()));
                result.add(actor);
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return result;
    }

    private void addDirector(Director director) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "INSERT INTO director(id, name, birth_date, birth_country) VALUES (?, ?, ?, ?)")) {
            updStatement.setString(1, director.getId());
            updStatement.setString(2, director.getName());
            updStatement.setDate(3, new Date(director.getBirthDate().toEpochDay()));
            updStatement.setString(4, director.getBirthCountry());
            updStatement.executeQuery();

            for (String movieId : director.getMovies()) {
                try (PreparedStatement cascadeStatement = connection.prepareStatement("INSERT INTO MovieDirectorConnector(movie_id, director_id) VALUES (?, ?)")) {
                    cascadeStatement.setString(1, movieId);
                    cascadeStatement.setString(2, director.getId());
                    cascadeStatement.executeQuery();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    public void editDirector(Director director) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "UPDATE director SET name=?, birth_date=?, birth_country=? WHERE id = ?")) {
            updStatement.setString(1, director.getName());
            updStatement.setDate(2, new Date(director.getBirthDate().toEpochDay()));
            updStatement.setString(3, director.getBirthCountry());
            updStatement.setString(4, director.getId());
            updStatement.executeQuery();

            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM MovieDirectorConnector WHERE director_id = ?")) {
                preparedStatement.setString(1, director.getId());
                preparedStatement.executeQuery();
            }
            for (String movieId : director.getMovies()) {
                try (PreparedStatement cascadeStatement = connection.prepareStatement("INSERT INTO MovieDirectorConnector(movie_id, director_id) VALUES (?, ?)")) {
                    cascadeStatement.setString(1, movieId);
                    cascadeStatement.setString(2, director.getId());
                    cascadeStatement.executeQuery();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private void removeDirector(String directorId) {
        if (directorId == null) {
            return;
        }
        try (PreparedStatement updStatement = connection.prepareStatement("DELETE FROM director WHERE id = ?")) {
            updStatement.setString(1, directorId);
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private List<BaseEntity> getDirectors() {
        List<BaseEntity> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM director")) {
            while (rs.next()) {
                Director director = new Director();
                director.setId(rs.getString(1));
                director.setName(rs.getString(2));
                director.setBirthDate(rs.getDate(3).toLocalDate());
                director.setBirthCountry(rs.getString(4));
                director.setMovies(cascadeQuery("movie_id", "MovieDirectorConnector", "director_id", director.getId()));
                result.add(director);
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return result;
    }

    private void addMovie(Movie movie) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "INSERT INTO movie(id, name, release_date, duration, budget, "
                + "description, countries, genres) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            updStatement.setString(1, movie.getId());
            updStatement.setString(2, movie.getName());
            updStatement.setDate(3, new Date(movie.getReleaseDate().toEpochDay()));
            updStatement.setInt(4, movie.getDuration());
            updStatement.setLong(5, movie.getBudget());
            updStatement.setString(6, movie.getDescription());
            updStatement.setString(7, movie.getCountries());
            updStatement.setString(8, movie.getGenres());
            updStatement.executeQuery();

            for (String directorId : movie.getDirectors()) {
                try (PreparedStatement cascadeStatement = connection.prepareStatement("INSERT INTO MovieDirectorConnector(movie_id, director_id) VALUES (?, ?)")) {
                    cascadeStatement.setString(1, movie.getId());
                    cascadeStatement.setString(2, directorId);
                    cascadeStatement.executeQuery();
                }
            }
            for (String characterId : movie.getCharacters()) {
                try (PreparedStatement cascadeStatement = connection.prepareStatement("UPDATE character SET movie_id = ? WHERE id = ?")) {
                    cascadeStatement.setString(1, movie.getId());
                    cascadeStatement.setString(2, characterId);
                    cascadeStatement.executeQuery();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private void editMovie(Movie movie) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "UPDATE movie SET name=?, release_date=?, duration=?, budget=?, "
                + "description=?, countries=?, genres=? WHERE id = ?")) {
            updStatement.setString(1, movie.getName());
            updStatement.setDate(2, new Date(movie.getReleaseDate().toEpochDay()));
            updStatement.setInt(3, movie.getDuration());
            updStatement.setLong(4, movie.getBudget());
            updStatement.setString(5, movie.getDescription());
            updStatement.setString(6, movie.getCountries());
            updStatement.setString(7, movie.getGenres());
            updStatement.setString(8, movie.getId());
            updStatement.executeQuery();

            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM MovieDirectorConnector WHERE movie_id = ?")) {
                preparedStatement.setString(1, movie.getId());
                preparedStatement.executeQuery();
            }
            for (String directorId : movie.getDirectors()) {
                try (PreparedStatement cascadeStatement = connection.prepareStatement("INSERT INTO MovieDirectorConnector(movie_id, director_id) VALUES (?, ?)")) {
                    cascadeStatement.setString(1, movie.getId());
                    cascadeStatement.setString(2, directorId);
                    cascadeStatement.executeQuery();
                }
            }
            PreparedStatement ps = connection.prepareStatement("DELETE FROM character WHERE movie_id = ? AND id NOT IN (?)");
            ps.setString(1, movie.getId());
            String listIds = movie.getCharacters().stream().collect(Collectors.joining(","));
            ps.setString(2, listIds);
            for (String characterId : movie.getCharacters()) {
                try (PreparedStatement cascadeStatement = connection.prepareStatement("UPDATE character SET movie_id = ? WHERE id = ?")) {
                    cascadeStatement.setString(1, movie.getId());
                    cascadeStatement.setString(2, characterId);
                    cascadeStatement.executeQuery();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private void removeMovie(String movieId) {
        if (movieId == null) {
            return;
        }
        try (PreparedStatement updStatement = connection.prepareStatement("DELETE FROM movie WHERE id = ?")) {
            updStatement.setString(1, movieId);
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private List<BaseEntity> getMovies() {
        List<BaseEntity> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM movie")) {
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getString(1));
                movie.setName(rs.getString(2));
                movie.setReleaseDate(rs.getDate(3).toLocalDate());
                movie.setDuration(rs.getInt(4));
                movie.setBudget(rs.getLong(5));
                movie.setDescription(rs.getString(6));
                movie.setCountries(rs.getString(7));
                movie.setGenres(rs.getString(8));
                movie.setCharacters(cascadeQuery("id", "character", "movie_id", movie.getId()));
                movie.setDirectors(cascadeQuery("director_id", "MovieDirectorConnector", "movie_id", movie.getId()));
                result.add(movie);
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return result;
    }

    private void addCharacter(Персонаж персонаж) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "INSERT INTO character(id, movie_id, actor_id, char_name, description) VALUES (?, ?, ?, ?, ?)")) {
            updStatement.setString(1, персонаж.getId());
            updStatement.setString(2, персонаж.getMovie());
            updStatement.setString(3, персонаж.getActor());
            updStatement.setString(4, персонаж.getName());
            updStatement.setString(5, персонаж.getDescription());
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private void editCharacter(Персонаж персонаж) {
        try (PreparedStatement updStatement = connection.prepareStatement(
                "UPDATE character SET movie_id=?, actor_id=?, char_name=?, description=? WHERE id = ?")) {
            updStatement.setString(1, персонаж.getMovie());
            updStatement.setString(2, персонаж.getActor());
            updStatement.setString(3, персонаж.getName());
            updStatement.setString(4, персонаж.getDescription());
            updStatement.setString(5, персонаж.getId());
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private void removeCharacter(String идентификаторПерсонажа) {
        if (идентификаторПерсонажа == null) {
            return;
        }
        try (PreparedStatement updStatement = connection.prepareStatement("DELETE FROM character WHERE id = ?")) {
            updStatement.setString(1, идентификаторПерсонажа);
            updStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
    }

    private List<BaseEntity> getCharacters() {
        List<BaseEntity> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM character")) {
            while (rs.next()) {
                Персонаж персонаж = new Персонаж();
                персонаж.setId(rs.getString(1));
                персонаж.setActor(rs.getString(2));
                персонаж.setMovie(rs.getString(3));
                персонаж.setName(rs.getString(4));
                персонаж.setDescription(rs.getString(5));
                result.add(персонаж);
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return result;
    }

    private Movie getMovieById(final String id) {
        Movie movie = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie WHERE id = ?")) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    movie = new Movie();
                    movie.setId(rs.getString(1));
                    movie.setName(rs.getString(2));
                    movie.setReleaseDate(rs.getDate(3).toLocalDate());
                    movie.setDuration(rs.getInt(4));
                    movie.setBudget(rs.getLong(5));
                    movie.setDescription(rs.getString(6));
                    movie.setCountries(rs.getString(7));
                    movie.setGenres(rs.getString(8));
                    movie.setCharacters(cascadeQuery("id", "character", "movie_id", movie.getId()));
                    movie.setDirectors(cascadeQuery("director_id", "MovieDirectorConnector", "movie_id", movie.getId()));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return movie;
    }

    private Персонаж getCharacterById(final String id) {
        Персонаж персонаж = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM character WHERE id = ?")) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    персонаж = new Персонаж();
                    персонаж.setId(rs.getString(1));
                    персонаж.setMovie(rs.getString(2));
                    персонаж.setActor(rs.getString(3));
                    персонаж.setName(rs.getString(4));
                    персонаж.setDescription(rs.getString(5));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return персонаж;
    }

    private Director getDirectorById(final String id) {
        Director director = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM director WHERE id = ?")) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    director = new Director();
                    director.setId(rs.getString(1));
                    director.setName(rs.getString(2));
                    director.setBirthDate(rs.getDate(3).toLocalDate());
                    director.setBirthCountry(rs.getString(4));
                    director.setMovies(cascadeQuery("movie_id", "MovieDirectorConnector", "director_id", director.getId()));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return director;
    }

    private Actor getActorById(final String id) {
        Actor actor = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE id = ?")) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    actor = new Actor();
                    actor.setId(rs.getString(1));
                    actor.setName(rs.getString(2));
                    actor.setBirthDate(rs.getDate(3).toLocalDate());
                    actor.setBirthCountry(rs.getString(4));
                    actor.setCharacters(cascadeQuery("id", "character", "actor_id", actor.getId()));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка при выполнении SQL запроса");
            ex.printStackTrace();
        }
        return actor;
    }

    public BaseEntity getEntityById(EntityType type, String id) {
        synchronized (this) {
            if (cache.hasValue(type, id)) {
                return cache.getValueForId(type, id);
            }
            BaseEntity entity = null;
            switch (type) {
                case MOVIE:
                    entity = getMovieById(id);
                    break;
                case ACTOR:
                    entity = getActorById(id);
                    break;
                case CHARACTER:
                    entity = getCharacterById(id);
                    break;
                case DIRECTOR:
                    entity = getDirectorById(id);
                    break;
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
            cache.put(entity);
            return entity;
        }
    }

    public void removeEntityById(EntityType type, String id) {
        synchronized (this) {
            cache.remove(type, id);
            switch (type) {
                case MOVIE:
                    removeMovie(id);
                    break;
                case ACTOR:
                    removeActor(id);
                    break;
                case CHARACTER:
                    removeCharacter(id);
                    break;
                case DIRECTOR:
                    removeDirector(id);
                    break;
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public EntityType addEntity(BaseEntity entity) {
        synchronized (this) {
            cache.put(entity);
            EntityType type = EntityType.fromEntity(entity);
            switch (type) {
                case MOVIE:
                    addMovie((Movie) entity);
                    cache.clearCacheByType(EntityType.MOVIE);
                    return EntityType.MOVIE;
                case CHARACTER:
                    addCharacter((Персонаж) entity);
                    cache.clearCacheByType(EntityType.ACTOR);
                    cache.clearCacheByType(EntityType.MOVIE);
                    return EntityType.CHARACTER;
                case ACTOR:
                    addActor((Actor) entity);
                    return EntityType.ACTOR;
                case DIRECTOR:
                    addDirector((Director) entity);
                    cache.clearCacheByType(EntityType.MOVIE);
                    return EntityType.DIRECTOR;
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public EntityType editEntity(BaseEntity entity) {
        synchronized (this) {
            cache.put(entity);
            EntityType type = EntityType.fromEntity(entity);
            switch (type) {
                case MOVIE:
                    editMovie((Movie) entity);
                    cache.clearCacheByType(EntityType.MOVIE);
                    return EntityType.MOVIE;
                case CHARACTER:
                    editCharacter((Персонаж) entity);
                    cache.clearCacheByType(EntityType.ACTOR);
                    cache.clearCacheByType(EntityType.MOVIE);
                    return EntityType.CHARACTER;
                case ACTOR:
                    editActor((Actor) entity);
                    return EntityType.ACTOR;
                case DIRECTOR:
                    editDirector((Director) entity);
                    cache.clearCacheByType(EntityType.MOVIE);
                    return EntityType.DIRECTOR;
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
        }
    }

    public List<? extends BaseEntity> getEntities(EntityType type) {
        synchronized (this) {
            if (cache.hasValuesForType(type)) {
                return Lists.newArrayList(cache.getValuesForType(type));
            }
            List<? extends BaseEntity> result = null;
            switch (type) {
                case MOVIE:
                    result = getMovies();
                    break;
                case ACTOR:
                    result = getActors();
                    break;
                case CHARACTER:
                    result = getCharacters();
                    break;
                case DIRECTOR:
                    result = getDirectors();
                    break;
                default:
                    throw new IllegalArgumentException("Неопознанный вид сущности");
            }
            cache.putAll(result);
            return result;
        }
    }

}
