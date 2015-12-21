package com.students.entity;

import com.students.entity.utils.LocalDateAdapter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Actor extends BaseEntity {

    private LocalDate birthDate;

    private String birthCountry;

    private List<Персонаж> characters = new LinkedList<>();

    public Actor() {
        super();
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

    public void addCharacter(Персонаж character) {
        characters.add(character);
    }

    @XmlElementWrapper(name = "characters")
    @XmlElement(name = "character")
    @XmlIDREF
    public List<Персонаж> getCharacters() {
        return characters;
    }
}
