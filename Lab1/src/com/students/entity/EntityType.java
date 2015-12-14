/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.entity;

/**
 *
 * @author pushi_000
 */
public enum EntityType {
    ACTOR("Actors"),
    CHARACTER("Characters"),
    DIRECTOR("Directors"),
    MOVIE("Movies");
    
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
        return null;
    }
}
