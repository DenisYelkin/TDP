/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pushi_000
 */
@XmlEnum
public enum EntityType implements Serializable {

    ACTOR("Actors"),
    CHARACTER("Characters"),
    DIRECTOR("Directors"),
    MOVIE("Movies"),
    FAKE("Fake");
    @XmlTransient
    private String name;

    private EntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EntityType of(String name) {
        if (name != null) {
            for (EntityType type : EntityType.values()) {
                if (type.getName().equalsIgnoreCase(name)) {
                    return type;
                }
            }
        }
        return FAKE;
    }

    public static EntityType fromEntity(BaseEntity entity) {
        if (entity instanceof Movie) {
            return MOVIE;
        } else if (entity instanceof Actor) {
            return ACTOR;
        } else if (entity instanceof Director) {
            return DIRECTOR;
        } else if (entity instanceof Персонаж) {
            return CHARACTER;
        }
        return FAKE;
    }
    
    public static <T extends BaseEntity> EntityType fromEntityClass(Class<T> clazz) {
        if (clazz.isAssignableFrom(Movie.class)) {
            return MOVIE;
        } else if (clazz.isAssignableFrom(Actor.class)) {
            return ACTOR;
        } else if (clazz.isAssignableFrom(Director.class)) {
            return DIRECTOR;
        } else if (clazz.isAssignableFrom(Персонаж.class)) {
            return CHARACTER;
        }
        return FAKE;
    }

    public Class<? extends BaseEntity> getEntityClass() {
        switch (this) {
            case ACTOR:
                return Actor.class;
            case CHARACTER:
                return Персонаж.class;
            case DIRECTOR:
                return Director.class;
            case MOVIE:
                return Movie.class;
            default:
                return null;
        }
    }
}
