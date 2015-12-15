/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.entity;

import java.io.Serializable;

/**
 *
 * @author pushi_000
 */
public enum EntityType implements Serializable {
    ACTOR("Actors"),
    CHARACTER("Characters"),
    DIRECTOR("Directors"),
    MOVIE("Movies"),
    FAKE("Fake");
    
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
    
    public static EntityType fromEntity(AbstractEntity entity) {
        if (entity instanceof Movie) {
            return MOVIE;
        } else if (entity instanceof Actor) {
            return ACTOR;
        } else if (entity instanceof Director) {
            return DIRECTOR;
        } else if (entity instanceof Character) {
            return CHARACTER;
        }
        return FAKE;
    }
}
