/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.commands;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlEnum;

/**
 *
 * @author pushi_000
 */

@XmlEnum
public enum ServerCommand implements Serializable {
    ADD,
    REMOVE,
    START_EDITING,
    APPLY_EDITING,
    FINISH_EDITING,
    REQUEST_ENTITIES,
    REQUEST_ENTITY_BY_ID;
}
