/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.controller;

import com.students.entity.EntityType;

/**
 *
 * @author pushi_000
 */
public abstract class MessageListener {
    public abstract void onMessageReceive(EntityType type);
}
