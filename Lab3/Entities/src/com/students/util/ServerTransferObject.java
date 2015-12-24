/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.util;

import com.students.commands.ClientCommand;
import com.students.entity.Actor;
import com.students.entity.BaseEntity;
import com.students.entity.Персонаж;
import com.students.entity.Director;
import com.students.entity.EntityType;
import com.students.entity.Movie;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author pushi_000
 */
@XmlRootElement
@XmlSeeAlso({Actor.class, Персонаж.class, Director.class, Movie.class})
public class ServerTransferObject<T extends BaseEntity> {

    public static final String PATH_TO_SCHEMA = "../Entities/src/com/students/util/resources/serverTransferObject.xsd";
    
    @XmlElement(required = true)
    private ClientCommand command;
    
    private List<T> entities;
    
    @XmlElement(required = true)
    private EntityType entityType;

    public ServerTransferObject() {
    }

    public ServerTransferObject(ClientCommand command, List<T> entities, EntityType entityType) {
        this.command = command;
        this.entities = entities;
        this.entityType = entityType;
    }

    public ClientCommand getCommand() {
        return command;
    }

    @XmlElementWrapper(name = "entities")
    @XmlElement(name = "entity", required = true)
    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    
    public EntityType getEntityType() {
        return entityType;
    }
}
