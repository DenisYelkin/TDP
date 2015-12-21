/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.server;

import com.students.commands.ClientCommand;
import com.students.commands.ServerCommand;
import com.students.entity.BaseEntity;
import com.students.entity.EntityType;
import com.students.model.Model;
import com.students.util.ClientTransferObject;
import com.students.util.ServerTransferObject;
import com.students.util.XMLUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

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
                InputStream reader = socket.getInputStream();
                OutputStream writer;
                ClientTransferObject transferObject = XMLUtils.read(ClientTransferObject.class, reader, null);
                ServerCommand command = transferObject.getCommand();
                EntityType type = transferObject.getEntityType();
                BaseEntity entity = transferObject.getEntity();
                System.out.println(String.format("Command %s received from %d", command.name(), currentNum));
                synchronized (socket) {
                    switch (command) {
                        case ADD:
                            model.addEntity(entity);
                            Server.publish(socket, type);
                            break;
                        case REMOVE:
                            model.removeEntityById(type, entity.getId());
                            Server.publish(socket, type);
                            break;
                        case START_EDITING:
                            Server.lockEntity(entity);
                            break;
                        case FINISH_EDITING:
                            Server.unlockEntity(entity);
                            break;
                        case APPLY_EDITING:
                            type = model.editEntity(entity);
                            Server.publish(socket, type);
                            break;
                        case REQUEST_ENTITIES:
                            writer = socket.getOutputStream();
                            XMLUtils.write(writer, new ServerTransferObject(ClientCommand.RECEIVE_LIST_OF_ENTITIES, model.getEntities(type), type));
                            writer.flush();
                            break;
                        case REQUEST_ENTITY_BY_ID:
                            writer = socket.getOutputStream();
                            if (Server.isEntityLocked(entity.getId())) {                                
                                XMLUtils.write(writer, new ServerTransferObject(ClientCommand.RECEIVE_ENTITY_LOCKED, null, null));
                            } else {
                                List singletonList = Collections.singletonList(model.getEntityById(type, entity.getId()));
                                XMLUtils.write(writer, new ServerTransferObject(ClientCommand.RECEIVE_ENTITY, singletonList, type));
                            }
                            writer.flush();
                            break;
                        default:
                            throw new IllegalArgumentException("Команда не опознана");
                    }
                }
                // writer.flush();

            }
        } catch (IOException | IllegalArgumentException | JAXBException | SAXException ex) {
            try {
                // client is gone
                socket.close();
            } catch (IOException ex1) {
                System.out.println("Что то пошло не так: " + ex.getMessage());
            }
            ex.printStackTrace();
        }
        Server.removeUser(socket);
        System.out.println(String.format("Клиент %s ушел ", currentNum));
    }
}
