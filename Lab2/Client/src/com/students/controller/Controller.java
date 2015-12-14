package com.students.controller;

import com.students.commands.ServerCommand;
import com.students.entity.*;
import com.students.entity.Character;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.List;

public class Controller {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 10500;

    private final Socket socket;

    public Controller() throws IOException {
        socket = new Socket(HOST, PORT);
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
    }

    public void editEntity(AbstractEntity entity) throws IOException {
        instructionForEntity(entity, ServerCommand.APPLY_EDITING);
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
    }
    
    public List<? extends AbstractEntity> getEntities(EntityType type) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            oos.writeObject(ServerCommand.GET_ENTITIES);
            oos.writeObject(type);
            oos.flush();
            return (List<? extends AbstractEntity>) ois.readObject();
        }
    }

    public void saveData(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(model);
            oos.flush();
        }
    }

    public void loadData(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            model = (Model) ois.readObject();
        }
    }
}
