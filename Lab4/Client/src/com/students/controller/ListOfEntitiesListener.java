/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.controller;

import com.students.entity.BaseEntity;
import com.students.entity.EntityType;
import java.util.List;

/**
 *
 * @author pushi_000
 */
public abstract class ListOfEntitiesListener {
    public abstract void onDataReceive(EntityType type, List<? extends BaseEntity> entities);
}
