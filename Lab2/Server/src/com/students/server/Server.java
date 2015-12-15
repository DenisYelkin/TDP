/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.server;

import com.google.common.collect.Sets;
import com.students.commands.ClientCommand;
import com.students.entity.AbstractEntity;
import com.students.entity.EntityType;
import com.students.model.Model;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pushi_000
 */
public class Server {

    private static final int PORT = 10500;
    private static final String MODEL_FILE_STORAGE_NAME = "model.data";

    private static final ExecutorService pool;
    private static final Model model;
    private static final Set<Socket> users;
    private static final Set<String> lockedEntities;

    static {
        pool = Executors.newCachedThreadPool();
        Model tempModel;
        try {
            tempModel = Model.loadData(new File(MODEL_FILE_STORAGE_NAME));
        } catch (IOException | ClassNotFoundException ex) {
            tempModel = new Model();
            System.out.println("Что то пошло не так: " + ex.getMessage());
        }
        model = tempModel;
        users = Sets.newConcurrentHashSet();
        lockedEntities = Sets.newConcurrentHashSet();
    }

    private static Runnable serveClient(ServerSocket serverSocket) throws IOException {
        return () -> {
            while (true) {
                try {
                    Socket user = serverSocket.accept();
                    users.add(user);
                    pool.submit(new Worker(user, model));
                } catch (IOException ex) {
                    System.out.println("Что то пошло не так: " + ex.getMessage());
                }
            }
        };
    }

    public static void removeUser(Socket socket) {
        users.remove(socket);
    }

    public static void publish(Socket from, EntityType type) {
        users.stream().forEach((user) -> {
            if (user.equals(from)) {
                return;
            }
            try (ObjectOutputStream writer = new ObjectOutputStream(user.getOutputStream())) {
                writer.writeObject(ClientCommand.RECEIVE_MESSAGE);
                writer.writeObject(type);
            } catch (IOException e) {
                System.out.println("Не удалось кому-то доставить сообщение: " + type.getName());
            }
        });
    }

    public static void lockEntity(AbstractEntity entity) {
        lockedEntities.add(entity.getId());
    }

    public static void unlockEntity(AbstractEntity entity) {
        lockedEntities.remove(entity.getId());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        SaveDataTimerTask timerTask = new SaveDataTimerTask();
        timer.schedule(timerTask, 20 * 1000, 30 * 1000);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                ServerSocket serverSocket = new ServerSocket(PORT)) {
            pool.submit(serveClient(serverSocket));
            while (!reader.readLine().toLowerCase().equals("stop")) {
            }
            pool.shutdownNow();
            timer.cancel();
            timerTask.run();
        } catch (IOException ex) {
            System.out.println("Что то пошло не так: " + ex.getMessage());
        }
    }

    private static class SaveDataTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                model.saveData(new File(MODEL_FILE_STORAGE_NAME));
            } catch (IOException ex) {
                System.out.println("Не удалось сохранить данные: " + ex.getMessage());
            }
        }
    }

    /*TODO:
     check locked entities before editing
     commands: getById
    synchronized
     */
}
