package com.students.controller;

import com.google.common.collect.Maps;
import com.students.commands.ClientCommand;
import com.students.commands.ServerCommand;
import com.students.entity.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 10500;

    private final Socket socket;
    private final Map<String, AbstractEntity> moviesCache = Maps.newConcurrentMap();
    private final Map<String, AbstractEntity> actorsCache = Maps.newConcurrentMap();
    private final Map<String, AbstractEntity> charactersCache = Maps.newConcurrentMap();
    private final Map<String, AbstractEntity> directorsCache = Maps.newConcurrentMap();
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

        protected abstract void perform(ObjectOutputStream oos) throws IOException;

        public void execute() throws IOException {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            perform(oos);
            oos.flush();
        }

    }

    private void instructionForEntity(AbstractEntity entity, ServerCommand command) throws IOException {
        System.out.println(String.format("instructonForEntity entity.class = %s; command = %s", entity.getClass(), command.name()));
        new Instruction() {

            @Override
            protected void perform(ObjectOutputStream oos) throws IOException {
                oos.writeObject(command);
                oos.writeObject(entity);
            }
        }.execute();
    }

    public void addEntity(AbstractEntity entity) throws IOException {
        instructionForEntity(entity, ServerCommand.ADD);
        getCacheByType(EntityType.fromEntity(entity)).put(entity.getId(), entity);
    }

    public void editEntity(AbstractEntity entity) throws IOException {
        instructionForEntity(entity, ServerCommand.APPLY_EDITING);
        getCacheByType(EntityType.fromEntity(entity)).put(entity.getId(), entity);
    }

    public void startEditing(AbstractEntity entity) throws IOException {
        instructionForEntity(entity, ServerCommand.START_EDITING);
    }

    public void finishEditing(AbstractEntity entity) throws IOException {
        instructionForEntity(entity, ServerCommand.FINISH_EDITING);
    }

    public void removeEntity(EntityType type, String id) throws IOException {
        new Instruction() {

            @Override
            protected void perform(ObjectOutputStream oos) throws IOException {
                oos.writeObject(ServerCommand.REMOVE);
                oos.writeObject(type);
                oos.writeObject(id);
            }
        }.execute();
        getCacheByType(type).remove(id);
    }

    public void requestEntityById(EntityType type, String id) throws IOException {
        new Instruction() {

            @Override
            protected void perform(ObjectOutputStream oos) throws IOException {
                oos.writeObject(ServerCommand.REQUEST_ENTITY_BY_ID);
                oos.writeObject(type);
                oos.writeObject(id);
            }
        }.execute();
    }

    private Map<String, AbstractEntity> getCacheByType(EntityType type) {
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
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ClientCommand command = (ClientCommand) ois.readObject();
                EntityType type;
                System.out.println(String.format("Command %s received from server", command.name()));
                switch (command) {
                    case RECEIVE_LIST_OF_ENTITIES:
                        List<? extends AbstractEntity> entities = (List<? extends AbstractEntity>) ois.readObject();
                        if (entities != null) {
                            if (entities.isEmpty()) {
                                listOfEntitiesListener.onDataReceive(EntityType.FAKE, new LinkedList<>());
                            } else {
                                type = EntityType.fromEntity(entities.get(0));
                                Map<String, AbstractEntity> cache = getCacheByType(type);
                                entities.stream().forEach((entity) -> {
                                    cache.put(entity.getId(), entity);
                                });
                                listOfEntitiesListener.onDataReceive(type, new LinkedList<>(cache.values()));
                            }
                        }
                        break;
                    case RECEIVE_MESSAGE:
                        type = (EntityType) ois.readObject();
                        messageListener.onMessageReceive(type);
                        break;
                    case RECEIVE_ENTITY:
                        AbstractEntity entity = (AbstractEntity) ois.readObject();
                        type = EntityType.fromEntity(entity);
                        entityListener.onEntityReceive(type, entity);
                        break;
                    case RECEIVE_ENTITY_LOCKED:
                        entityListener.onEntityReceive(EntityType.FAKE, null);
                        break;
                }
            } catch (SocketException e) {

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Что-то пошло не так");
                ex.printStackTrace();
            }
        }
    }

    public void setDataListener(ListOfEntitiesListener listener) {
        listOfEntitiesListener = listener;
    }

    public void requestEntities(EntityType type) throws IOException {
        Map<String, AbstractEntity> cache = getCacheByType(type);
        if (cache.isEmpty()) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(ServerCommand.REQUEST_ENTITIES);
            oos.writeObject(type);
            oos.flush();
        } else {
            listOfEntitiesListener.onDataReceive(type, new LinkedList<>(cache.values()));
        }
    }

    public void refreshData() {
        moviesCache.clear();
        actorsCache.clear();
        directorsCache.clear();
        charactersCache.clear();
    }
}
