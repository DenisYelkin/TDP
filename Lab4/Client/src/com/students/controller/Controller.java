package com.students.controller;

import com.google.common.collect.Maps;
import com.students.commands.ClientCommand;
import com.students.commands.ServerCommand;
import com.students.entity.*;
import com.students.util.ClientTransferObject;
import com.students.util.ServerTransferObject;
import com.students.util.XMLUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

public class Controller {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 10500;

    private final Socket socket;
    private final Map<String, BaseEntity> moviesCache = Maps.newConcurrentMap();
    private final Map<String, BaseEntity> actorsCache = Maps.newConcurrentMap();
    private final Map<String, BaseEntity> charactersCache = Maps.newConcurrentMap();
    private final Map<String, BaseEntity> directorsCache = Maps.newConcurrentMap();
    private final ExecutorService pool;
    private ListOfEntitiesListener listOfEntitiesListener;
    private final EntityListener entityListener;
    private final MessageListener messageListener;

    public Controller(MessageListener messageListener, EntityListener entityListener) throws IOException {
        this.messageListener = messageListener;
        this.entityListener = entityListener;
        socket = new Socket(HOST, PORT);
        pool = Executors.newSingleThreadExecutor();
        pool.submit(() -> receive());
    }

    public void dispose() throws IOException {
        pool.shutdownNow();
        socket.close();
    }

    public Controller(MessageListener messageListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private abstract class Instruction {

        protected abstract void perform(OutputStream oos) throws IOException, JAXBException;

        public void execute() throws IOException, JAXBException {
            OutputStream os = socket.getOutputStream();
            perform(os);
            os.flush();
        }

    }

    private void instructionForEntity(BaseEntity entity, ServerCommand command) throws IOException, JAXBException {
        new Instruction() {

            @Override
            protected void perform(OutputStream os) throws IOException, JAXBException {
                XMLUtils.write(os, new ClientTransferObject(command, entity, EntityType.fromEntity(entity)));
                XMLUtils.write(System.out, new ClientTransferObject(command, entity, EntityType.fromEntity(entity)));
            }
        }.execute();
    }

    public void addEntity(BaseEntity entity) throws IOException, JAXBException {
        instructionForEntity(entity, ServerCommand.ADD);
        getCacheByType(EntityType.fromEntity(entity)).put(entity.getId(), entity);
    }

    public void editEntity(BaseEntity entity) throws IOException, JAXBException {
        instructionForEntity(entity, ServerCommand.APPLY_EDITING);
        getCacheByType(EntityType.fromEntity(entity)).put(entity.getId(), entity);
    }

    public void startEditing(BaseEntity entity) throws IOException, JAXBException {
        instructionForEntity(entity, ServerCommand.START_EDITING);
    }

    public void finishEditing(BaseEntity entity) throws IOException, JAXBException {
        instructionForEntity(entity, ServerCommand.FINISH_EDITING);
    }

    public void removeEntity(EntityType type, String id) throws IOException, JAXBException {
        new Instruction() {

            @Override
            protected void perform(OutputStream os) throws IOException, JAXBException {
                XMLUtils.write(os, new ClientTransferObject(ServerCommand.REMOVE, new BaseEntity(id), type));
            }
        }.execute();
        getCacheByType(type).remove(id);
    }

    public void requestEntityById(EntityType type, String id) throws IOException, JAXBException {
        new Instruction() {

            @Override
            protected void perform(OutputStream os) throws IOException, JAXBException {
                XMLUtils.write(os, new ClientTransferObject(ServerCommand.REQUEST_ENTITY_BY_ID, new BaseEntity(id), type));
            }
        }.execute();
    }

    public void requestEntities(EntityType type) throws IOException, JAXBException {
        Map<String, BaseEntity> cache = getCacheByType(type);
        if (cache.isEmpty()) {
            OutputStream os = socket.getOutputStream();
            XMLUtils.write(os, new ClientTransferObject(ServerCommand.REQUEST_ENTITIES, null, type));
            XMLUtils.write(System.out, new ClientTransferObject(ServerCommand.REQUEST_ENTITIES, null, type));
//            os.flush();
        } else {
            listOfEntitiesListener.onDataReceive(type, new LinkedList<>(cache.values()));
        }
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

    public void receive() {
        while (!socket.isClosed()) {
            try {
                InputStream is = socket.getInputStream();
                ServerTransferObject object = XMLUtils.read(ServerTransferObject.class, is, ServerTransferObject.PATH_TO_SCHEMA);
                ClientCommand command = object.getCommand();
                EntityType type = object.getEntityType();
                System.out.println(String.format("Command %s received from server", command.name()));
                switch (command) {
                    case RECEIVE_LIST_OF_ENTITIES:
                        List<? extends BaseEntity> entities = object.getEntities();
                        if (entities != null) {
                            if (entities.isEmpty()) {
                                listOfEntitiesListener.onDataReceive(EntityType.FAKE, new LinkedList<>());
                            } else {
                                Map<String, BaseEntity> cache = getCacheByType(type);
                                entities.stream().forEach((entity) -> {
                                    cache.put(entity.getId(), entity);
                                });
                                listOfEntitiesListener.onDataReceive(type, new LinkedList<>(cache.values()));
                            }
                        }
                        break;
                    case RECEIVE_MESSAGE:
                        messageListener.onMessageReceive(type);
                        break;
                    case RECEIVE_ENTITY:
                        List singleEntityList = object.getEntities();
                        if (singleEntityList != null && !singleEntityList.isEmpty()) {
                            BaseEntity entity = (BaseEntity) object.getEntities().get(0);
                            entityListener.onEntityReceive(type, entity);
                        }
                        break;
                    case RECEIVE_ENTITY_LOCKED:
                        entityListener.onEntityReceive(EntityType.FAKE, null);
                        break;
                }
            } catch (SocketException e) {
            } catch (IOException | SAXException | JAXBException ex) {
                System.out.println("Что-то пошло не так");
                ex.printStackTrace();
            }
        }
    }

    public void setDataListener(ListOfEntitiesListener listener) {
        listOfEntitiesListener = listener;
    }

    public void refreshData() {
        moviesCache.clear();
        actorsCache.clear();
        directorsCache.clear();
        charactersCache.clear();
    }
}
