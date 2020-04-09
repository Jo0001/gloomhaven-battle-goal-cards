package de.jo0001.gloomhaven.battleGoalCards.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class InputListener extends Thread {
    private List<Socket> clients = new ArrayList<>();

    public InputListener() {
        this.setName("InputListener-" + this.getName());
        System.out.println(this.getName());
    }

    public InputListener(List<Socket> clients) {
        this.setName("InputListener-" + this.getName());
        System.out.println(this.getName());
        this.clients = clients;
    }

    public void run() {
        check();
    }

    private void check() {
        while (true) {
            for (int i = 0; i < clients.size(); i++) {
                try {
                    DataInputStream inputStream = new DataInputStream(clients.get(i).getInputStream());
                    if (inputStream.available() > 0) {
                        System.out.println("Found something from" + clients.get(i).getLocalAddress());
                        if (inputStream.readUTF().equalsIgnoreCase("getnumber")) {
                            for (Socket tmp : clients) {
                                DataOutputStream out = new DataOutputStream(tmp.getOutputStream());
                                out.writeUTF(Server.getNumber() + "," + Server.getNumber());
                                System.out.println("Client disconnected: " + tmp.getLocalAddress());
                            }
                            //this.stop();
                            clients.clear();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addClient(Socket socket) {
        clients.add(socket);
        System.out.println("Successful added client");
        InputListener inputListener = new InputListener(clients);
        inputListener.start();
        this.stop();
    }
}
