package de.jo0001.gloomhaven.battleGoalCards.network;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private static final int[] ids = {458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481};
    private static final List<Integer> usedIds = new ArrayList<>();
    private static final List<Socket> clients = new ArrayList<>();

    public Server(int port) throws BindException {
        try {
            serverSocket = new ServerSocket(port);
        } catch (BindException e) {
            throw new BindException();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void run() {
        runServer();
    }

    private void runServer() {
        Socket client;
        while (true) {
            try {
                System.out.println("Waiting for clients on port " + serverSocket.getLocalPort());
                client = serverSocket.accept();
                clients.add(client);
                ServerThread serverThread = new ServerThread(client);
                serverThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static int getNumber(Random rand) {
        int id;
        do {
            id = ids[rand.nextInt(ids.length)];
        } while (usedIds.contains(id));
        usedIds.add(id);
        return id;
    }

    public static void notifyClients() {
        System.out.println("Sending ids to clients...");
        Random rand = new Random();
        List<Socket> deadClients = new ArrayList<>();
        for (Socket tmp : clients) {
            try {
                DataOutputStream out = new DataOutputStream(tmp.getOutputStream());
                out.writeUTF(Server.getNumber(rand) + "," + Server.getNumber(rand));
                out.flush();
            } catch (IOException e) {
                deadClients.add(tmp);
            }
        }
        usedIds.clear();
        clients.removeAll(deadClients);
    }

    //Just for Testing
    public static void main(String[] args) throws BindException {
        Server s = new Server(58889);
        s.runServer();
    }

}

class ServerThread extends Thread {
    private final DataInputStream inputStream;
    private final Socket s;

    public ServerThread(Socket s) throws IOException {
        this.s = s;
        inputStream = new DataInputStream(s.getInputStream());
        System.out.println("Starting new ServerThread " + this.getName());
    }

    public void run() {
        try {
            String in;
            while (true) {
                in = inputStream.readUTF();
                System.out.println("Found something from" + s.getLocalAddress());
                if (in.equalsIgnoreCase("getnumber")) {
                    System.out.println("Found 'getnumber'");
                    Server.notifyClients();
                }
            }
        } catch (IOException e) {
            System.out.println("IO error in server thread");
            try {
                if (s != null) {
                    s.close();
                    System.out.println("Socket Closed");
                }
            } catch (IOException ignored) {
            }
        }
    }
}