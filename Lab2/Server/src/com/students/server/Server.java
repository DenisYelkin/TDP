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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author pushi_000
 */
public class Server {

    private static final int PORT = 10500;

    private static final ExecutorService pool;
    private static final Model model;
    private static final Set<Socket> users;
    private static final Set<String> lockedEntities; 
    
    static {
        pool = Executors.newCachedThreadPool();
        model = new Model();
        users = Sets.newConcurrentHashSet();
        lockedEntities = Sets.newConcurrentHashSet();
    }

    private static Runnable serveClient(ServerSocket serverSocket) throws IOException {
        return () -> {
            while (true) {
                try {
                    Socket user = serverSocket.accept();
                    users.add(user);
                    pool.execute(new Worker(user, model));
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                ServerSocket serverSocket = new ServerSocket(PORT)) {
            pool.execute(serveClient(serverSocket));
            while (!reader.readLine().toLowerCase().equals("stop")) {
            }
            pool.shutdownNow();
        } catch (IOException ex) {
            System.out.println("Что то пошло не так: " + ex.getMessage());
        }
    }

    
    /*TODO:
    check locked entities before editing
    reseive messages on client
    commands: getById
    save/load - remove from client and add into server (start/shutdown)
    cache???
    refresh
    use ClientCommand (getEntities - wrong) Денис все поймет
    */
}
