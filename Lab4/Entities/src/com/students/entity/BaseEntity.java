package com.students.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlID;

public class BaseEntity implements Serializable {

    private String id;

    private String name;

    public BaseEntity() {
        id = UUID.randomUUID().toString();
    }

    public BaseEntity(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlID
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
        if (obj == null || !(obj instanceof BaseEntity)) {
            return false;
        }
        return id.equals(((BaseEntity) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static <T extends BaseEntity> Map<String, T> toMap(Collection<T> entities) {
        return entities.stream().collect(Collectors.toMap(BaseEntity::getId, Function.identity()));
    }
}
