package com.students.entity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Actor extends AbstractEntity {

    private LocalDate birthDate;

    private String birthCountry;

    private List<Character> characters = new LinkedList<>();

    public Actor() {
        super();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
