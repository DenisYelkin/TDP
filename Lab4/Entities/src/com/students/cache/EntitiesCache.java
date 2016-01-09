/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.cache;

import com.google.common.collect.Maps;
import com.students.entity.BaseEntity;
import com.students.entity.EntityType;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author pushi_000
 */
public class EntitiesCache {

    private final Map<String, BaseEntity> moviesCache = Maps.newConcurrentMap();
    private final Map<String, BaseEntity> actorsCache = Maps.newConcurrentMap();
    private final Map<String, BaseEntity> charactersCache = Maps.newConcurrentMap();
    private final Map<String, BaseEntity> directorsCache = Maps.newConcurrentMap();

    public EntitiesCache() {
    }

    public <T extends BaseEntity> void put(T entity) {
        if (entity != null) {
            getCacheByType(EntityType.fromEntity(entity)).put(entity.getId(), entity);
        }
    }

    public <T extends BaseEntity> void putAll(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        T first = entities.stream().findFirst().get();
        getCacheByType(EntityType.fromEntity(first)).putAll(BaseEntity.toMap(entities));
    }

    public <T extends BaseEntity> BaseEntity remove(T entity) {
        return getCacheByType(EntityType.fromEntity(entity)).remove(entity.getId());
    }

    public BaseEntity remove(EntityType type, String id) {
        return getCacheByType(type).remove(id);
    }

    public boolean hasValuesForType(EntityType type) {
        return !getCacheByType(type).isEmpty();
    }

    public Collection<BaseEntity> getValuesForType(EntityType type) {
        return getCacheByType(type).values();
    }

    public boolean hasValue(EntityType type, String id) {
        return getCacheByType(type).containsKey(id);
    }

    public BaseEntity getValueForId(EntityType type, String id) {
        if (hasValue(type, id)) {
            return getCacheByType(type).get(id);
        }
        return null;
    }

    private Map<String, BaseEntity> getCacheByType(EntityType type) {
        switch (type) {
            case MOVIE:
                return moviesCache;
            case ACTOR:
                return actorsCache;
            case CHARACTER:
                return charactersCache;
            case DIRECTOR:
                return directorsCache;
            default:
                return null;
        }
    }

    public void clear() {
        moviesCache.clear();
        actorsCache.clear();
        directorsCache.clear();
        charactersCache.clear();
    }

    public void clearCacheByType(EntityType type) {
        getCacheByType(type).clear();
    }
}
