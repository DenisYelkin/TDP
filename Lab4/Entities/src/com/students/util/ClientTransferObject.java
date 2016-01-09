/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.util;

import com.students.commands.ServerCommand;
import com.students.entity.Actor;
import com.students.entity.BaseEntity;
import com.students.entity.Director;
import com.students.entity.EntityType;
import com.students.entity.Movie;
import com.students.entity.Персонаж;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author pushi_000
 */
@XmlRootElement
@XmlSeeAlso({Actor.class, Персонаж.class, Director.class, Movie.class})
public class ClientTransferObject<T extends BaseEntity> {

    public static final String PATH_TO_SCHEMA = "../Entities/src/com/students/util/resources/clientTransferObject.xsd";
    
    @XmlElement(required = true)
    private ServerCommand command;
    
    private T entity;

    @XmlElement(required = true)
    private EntityType entityType;

    public ClientTransferObject() {
    }

    public ClientTransferObject(ServerCommand command, T entity, EntityType entityType) {
        this.command = command;
        this.entity = entity;
        this.entityType = entityType;
    }

    public ServerCommand getCommand() {
        return command;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }       
    
    @XmlElement(required = true)
    public T getEntity() {
        return entity;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
