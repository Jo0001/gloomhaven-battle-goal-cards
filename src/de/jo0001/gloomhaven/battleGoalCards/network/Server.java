package de.jo0001.gloomhaven.battleGoalCards.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private static int[] ids = {458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481};
    private static List<Integer> usedIds = new ArrayList<>();
    private List<Socket> clients = new ArrayList<>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            //   serverSocket.setSoTimeout(100000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        laufen();
    }

    private void laufen() {
        InputListener inputListener = new InputListener();
        inputListener.start();
        while (true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort());
                Socket client = serverSocket.accept();
                inputListener.addClient(client);
                System.out.println("Client connected: " + client.getInetAddress());
                clients.add(client);
                System.out.println("Current number of clients: " + clients.size());

                DataInputStream inputStream = new DataInputStream(client.getInputStream());
               System.out.println("DataInputStream ready");
                //System.out.println(inputStream.available());

                //String input = inputStream.readUTF();
                // System.out.println("Something is there :)");
              /*  String input = "null";
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                if (input.equalsIgnoreCase("ping")) {
                    output.writeUTF("pong");
                    disconnect(client);
                } else if (input.equalsIgnoreCase("getclients")) {
                    output.writeUTF(clients.size() + "");
                    disconnect(client);
                } else if (input.equalsIgnoreCase("getnumber")) {
                    for (Socket tmp : clients) {
                        DataOutputStream out = new DataOutputStream(tmp.getOutputStream());
                        out.writeUTF(getNumber() + "," + getNumber());
                        System.out.println("Client disconnected: " + tmp.getLocalAddress());
                    }
                    clients.clear();
                    usedIds.clear();
                }*/

                //client.close();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server(58889);
        s.laufen();
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

    private void disconnect(Socket s) {
        System.out.println("Client disconnected: " + s.getLocalAddress());
        clients.remove(s);
    }


}
