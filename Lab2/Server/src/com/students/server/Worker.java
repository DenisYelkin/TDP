/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.server;

import com.students.commands.ServerCommand;
import com.students.entity.AbstractEntity;
import com.students.entity.EntityType;
import com.students.model.Model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pushi_000
 */
public class Worker implements Runnable {
    
    private final Socket socket;
    private final Model model;
    
    public Worker(Socket socket, Model model) {
        this.socket = socket;
        this.model = model;
    }

    @Override
    public void run() {
        try (ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream())) {
            ServerCommand command = (ServerCommand) reader.readObject();
            EntityType type;
            AbstractEntity entity;
            switch (command) {
                case ADD:
                    entity = (AbstractEntity) reader.readObject();
                    type = model.addEntity(entity);
                    Server.publish(socket, type);
                    break;
                case REMOVE:
                    type = (EntityType) reader.readObject();
                    String id = (String) reader.readObject();
                    model.removeEntityById(type, id);
                    Server.publish(socket, type);
                    break;
                case START_EDITING:
                    entity = (AbstractEntity) reader.readObject();
                    Server.lockEntity(entity);
                    break;
                case FINISH_EDITING:
                    entity = (AbstractEntity) reader.readObject();
                    Server.unlockEntity(entity);
                    break;
                case APPLY_EDITING:
                    entity = (AbstractEntity) reader.readObject();
                    type = model.editEntity(entity);
                    Server.publish(socket, type);
                    break;
                case GET_ENTITIES:
                    type = (EntityType) reader.readObject();
                    writer.writeObject(model.getEntities(type));
                    break;
                case GET_BY_ID:
                    break;
                case SAVE:
                    break;
                case LOAD:
                    break;
                default:
                    throw new IllegalArgumentException("Команда не опознана");
            }
        } catch (Exception ex) {
            System.out.println("Что-то пошло не так: " + ex.getMessage());
        }
        Server.removeUser(socket);
    }
    
    
}
