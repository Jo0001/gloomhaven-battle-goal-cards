package de.jo0001.gloomhaven.battleGoalCards.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputListenerReload implements Runnable {
    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private List<Socket> clients = new ArrayList<>();

    public InputListenerReload() {
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
        System.out.println("Listener Started ");
    }

    public void stop() {
        running.set(false);
        System.out.println("Listener Stopped");
    }

    public void run() {
        running.set(true);
        while (running.get()) {
            for (int i = 0; i < clients.size(); i++) {
                try {
                    DataInputStream inputStream = new DataInputStream(clients.get(i).getInputStream());
                    if (inputStream.available() > 0) {
                        System.out.println("Found something from" + clients.get(i).getLocalAddress());
                        if (inputStream.readUTF().equalsIgnoreCase("getnumber")) {
                            System.out.println("Found getnumber");
                            for (Socket tmp : clients) {
                                DataOutputStream out = new DataOutputStream(tmp.getOutputStream());
                                out.writeUTF(Server.getNumber() + "," + Server.getNumber());
                                System.out.println("Client disconnected: " + tmp.getLocalAddress());
                            }
                            clear();
                        }
                    }
                } catch (IOException e) {
                    clients.remove(clients.get(i));
                    e.printStackTrace();
                }
            }
        }
    }

    public void addClient(Socket s) {
        this.stop();
        clients.add(s);
        this.start();
    }

    private void clear() {
        this.stop();
        clients.clear();
        Server.firstClient = true;
    }
}
