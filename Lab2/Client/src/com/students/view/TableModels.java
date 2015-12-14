/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.view;

import com.students.entity.*;
import com.students.entity.Character;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pushi_000
 */
public class TableModels {
    
    public static String getNames(List<? extends AbstractEntity> entities) {
        StringBuilder builder = new StringBuilder();
        for(AbstractEntity entity : entities) {
            builder.append(entity.getName());
            builder.append(", ");
        }
        String result = builder.toString();
        if (result.length() > 0) {
            result = result.substring(0, builder.length() - 2);
        }
        return result;
    } 
    
    public static DefaultTableModel getMovieTableModel(List<Movie> movies) {
        Object[][] data = new Object[movies.size()][10];
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            data[i][0] = movie.getId();
            data[i][1] = movie.getName();
            data[i][2] = movie.getReleaseDate().toString();
            data[i][3] = movie.getDuration();
            data[i][4] = movie.getBudget();
            data[i][5] = movie.getDescription();
            data[i][6] = movie.getGenres();
            data[i][7] = movie.getCountries();
            data[i][8] = getNames(movie.getDirectors());
            data[i][9] = getNames(movie.getCharacters());
        }
        return new DefaultTableModel(data, new String[] {"Id", "Name", "Release Date", "Duration", "Budget", "Desctiption", "Genres", "Countries", "Directors", "Characters"});
    }
    
    public static DefaultTableModel getActorTableModel(List<Actor> actors) {
        Object[][] data = new Object[actors.size()][5];
        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            data[i][0] = actor.getId();
            data[i][1] = actor.getName();
            data[i][2] = actor.getBirthDate().toString();
            data[i][3] = actor.getBirthCountry();
            data[i][4] = getNames(actor.getCharacters());
        }
        return new DefaultTableModel(data, new String[] {"Id", "Name", "Birth Date", "Birth Country", "Characters"});
    }
    
    public static DefaultTableModel getCharacterTableModel(List<Character> characters) {
        Object[][] data = new Object[characters.size()][4];
        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);
            data[i][0] = character.getActor().getName();
            data[i][1] = character.getMovie().getName();
            data[i][2] = character.getName();
            data[i][3] = character.getDescription();
        }
        return new DefaultTableModel(data, new String[] {"Actor", "Movie", "Character Name", "Description"});
    }
    
    public static DefaultTableModel getDirectorTableModel(List<Director> directors) {
        Object[][] data = new Object[directors.size()][5];
        for (int i = 0; i < directors.size(); i++) {
            Director director = directors.get(i);
            data[i][0] = director.getId();
            data[i][1] = director.getName();
            data[i][2] = director.getBirthDate().toString();
            data[i][3] = director.getBirthCountry();
            data[i][4] = getNames(director.getMovies());
        }
        return new DefaultTableModel(data, new String[] {"Id", "Name", "Birth Date", "Birth Country", "Movies"});
    }
}
