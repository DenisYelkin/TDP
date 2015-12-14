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
public enum ServerCommand implements Serializable {
    ADD,
    REMOVE,
    START_EDITING,
    APPLY_EDITING,
    FINISH_EDITING,
    GET_ENTITIES,
    GET_BY_ID,
    SAVE,
    LOAD;
}
