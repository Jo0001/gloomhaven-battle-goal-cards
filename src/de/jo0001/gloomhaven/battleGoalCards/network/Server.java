package de.jo0001.gloomhaven.battleGoalCards.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server extends Thread {
    private ServerSocket serverSocket;
    public static boolean firstClient = true;
    private static int[] ids = {458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481};
    private static List<Integer> usedIds = new ArrayList<>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        runServer();
    }

    private void runServer() {
        InputListenerReload inputListenerReload = new InputListenerReload();
        while (true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort());
                Socket client = serverSocket.accept();
                if (firstClient) {
                    System.out.println("Starting Listener for first client");
                    inputListenerReload.start();
                    firstClient = false;
                }
                inputListenerReload.addClient(client);
                System.out.println("Client connected: " + client.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static int getNumber() {
        Random rand = new Random();
        int id;
        do {
            id = ids[rand.nextInt(ids.length)];
        } while (usedIds.contains(id));
        usedIds.add(id);
        return id;
    }

    //Just for Testing
    public static void main(String[] args) throws IOException {
        Server s = new Server(58889);
        s.runServer();
    }

}