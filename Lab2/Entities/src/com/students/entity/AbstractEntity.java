package com.students.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractEntity implements Serializable {

    private String id;
    
    private String name;

    public AbstractEntity() {
        id = UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AbstractEntity))
            return false;
        return id.equals(((AbstractEntity)obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
}
