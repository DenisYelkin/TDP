/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.commands;

import java.io.Serializable;

/**
 *
 * @author pushi_000
 */
public enum ClientCommand implements Serializable {
    RECEIVE_MESSAGE,
    RECEIVE_LIST_OF_ENTITIES,
    RECEIVE_ENTITY,
    RECEIVE_ENTITY_LOCKED;
}
