package com.students.controller;

import com.google.common.collect.Maps;
import com.students.commands.ClientCommand;
import com.students.commands.ServerCommand;
import com.students.entity.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 10500;

    private final Socket socket;
    private final Map<String, AbstractEntity> moviesCache = Maps.newHashMap();
    private final Map<String, AbstractEntity> actorsCache = Maps.newHashMap();
    private final Map<String, AbstractEntity> charactersCache = Maps.newHashMap();
    private final Map<String, AbstractEntity> directorsCache = Maps.newHashMap();
    private final ExecutorService pool;
    private DataListener dataListener;
    private final MessageListener messageListener;

    public Controller(MessageListener messageListener) throws IOException {
        this.messageListener = messageListener;
        socket = new Socket(HOST, PORT);
        pool = Executors.newSingleThreadExecutor();
        pool.submit(() -> receive());
    }

    private abstract class Instruction {

        protected abstract void perform(ObjectOutputStream oos) throws IOException;

        public void execute() throws IOException {
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                perform(oos);
                oos.flush();
            }
        }
    }

    private void instructionForEntity(AbstractEntity entity, ServerCommand command) throws IOException {
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
        while (true) {
            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                ClientCommand command = (ClientCommand) ois.readObject();
                if (command == ClientCommand.RECEIVE_DATA) {
                    List<? extends AbstractEntity> entities = (List<? extends AbstractEntity>) ois.readObject();
                    if (entities != null && !entities.isEmpty()) {
                        EntityType type = EntityType.fromEntity(entities.get(0));
                        Map<String, AbstractEntity> cache = getCacheByType(type);
                        entities.stream().forEach((entity) -> {
                            cache.put(entity.getId(), entity);
                        });
                        dataListener.onDataReceive(type, new LinkedList<>(cache.values()));
                    }
                } else if (command == ClientCommand.RECEIVE_MESSAGE) {
                    EntityType type = (EntityType) ois.readObject();
                    messageListener.onMessageReceive(type);
                }
            } catch (IOException | ClassNotFoundException ex) {

            }
        }
    }

    public void setDataListener(DataListener listener) {
        dataListener = listener;
    }

    public void requestEntities(EntityType type) throws IOException {
        Map<String, AbstractEntity> cache = getCacheByType(type);
        if (cache.isEmpty()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                oos.writeObject(ServerCommand.REQUEST_ENTITIES);
                oos.writeObject(type);
                oos.flush();
            }
        } else {
            dataListener.onDataReceive(type, new LinkedList<>(cache.values()));
        }
    }

    public void refreshData() {
        moviesCache.clear();
        actorsCache.clear();
        directorsCache.clear();
        charactersCache.clear();
    }
}
