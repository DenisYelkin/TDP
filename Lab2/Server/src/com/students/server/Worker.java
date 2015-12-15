/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.server;

import com.students.commands.ClientCommand;
import com.students.commands.ServerCommand;
import com.students.entity.AbstractEntity;
import com.students.entity.EntityType;
import com.students.model.Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author pushi_000
 */
public class Worker implements Runnable {

    private final Socket socket;
    private final Model model;
    private final int currentNum;

    public Worker(Socket socket, Model model) {
        this.socket = socket;
        this.model = model;
        currentNum = Server.counter;
        Server.counter++;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream writer;
                ServerCommand command = (ServerCommand) reader.readObject();
                EntityType type;
                AbstractEntity entity;
                String id;
                System.out.println(String.format("Command %s received from %d", command.name(), currentNum));
                synchronized (socket) {
                    switch (command) {
                        case ADD:
                            entity = (AbstractEntity) reader.readObject();
                            type = model.addEntity(entity);
                            Server.publish(socket, type);
                            break;
                        case REMOVE:
                            type = (EntityType) reader.readObject();
                            id = (String) reader.readObject();
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
                        case REQUEST_ENTITIES:
                            type = (EntityType) reader.readObject();
                            writer = new ObjectOutputStream(socket.getOutputStream());
                            writer.writeObject(ClientCommand.RECEIVE_LIST_OF_ENTITIES);
                            List temp = model.getEntities(type);
                            writer.writeObject(temp);
                            writer.flush();
                            break;
                        case REQUEST_ENTITY_BY_ID:
                            type = (EntityType) reader.readObject();
                            id = (String) reader.readObject();
                            writer = new ObjectOutputStream(socket.getOutputStream());
                            if (Server.isEntityLocked(id)) {
                                writer.writeObject(ClientCommand.RECEIVE_ENTITY_LOCKED);
                            } else {
                                writer.writeObject(ClientCommand.RECEIVE_ENTITY);
                                writer.writeObject(model.getEntityById(type, id));
                            }
                            writer.flush();
                            break;
                        default:
                            throw new IllegalArgumentException("Команда не опознана");
                    }
                }
                // writer.flush();

            }
        } catch (IOException | ClassNotFoundException | IllegalArgumentException ex) {
            try {
                // client is gone
                socket.close();
            } catch (IOException ex1) {
                System.out.println("Что то пошло не так: " + ex.getMessage());
            }
        }
        Server.removeUser(socket);
        System.out.println(String.format("Клиент %s ушел ", socket.getLocalAddress()));
    }

}
