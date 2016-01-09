package com.students.entity;

import com.students.entity.utils.LocalDateAdapter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Actor extends BaseEntity {

    private LocalDate birthDate;

    private String birthCountry;

    private List<String> идентификаторыПерсонажей = new LinkedList<>();

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

    public void addCharacter(String идентификаторПерсонажа) {
        идентификаторыПерсонажей.add(идентификаторПерсонажа);
    }

    @XmlElementWrapper(name = "characters")
    @XmlElement(name = "character")
    public List<String> getCharacters() {
        return идентификаторыПерсонажей;
    }
    
    public void setCharacters(List<String> идентификаторыПерсонажей) {
        this.идентификаторыПерсонажей = идентификаторыПерсонажей;
    }
}
