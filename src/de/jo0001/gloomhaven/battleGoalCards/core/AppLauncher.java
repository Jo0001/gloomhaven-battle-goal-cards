package de.jo0001.gloomhaven.battleGoalCards.core;

import de.jo0001.gloomhaven.battleGoalCards.network.Server;
import de.jo0001.gloomhaven.battleGoalCards.other.DataHolder;
import de.jo0001.gloomhaven.battleGoalCards.other.DataManager;

import java.net.BindException;

public class AppLauncher {

    public static void main(String[] args) {
        if (args.length == 3 && args[0].equalsIgnoreCase("-startserver") && args[1].equalsIgnoreCase("-port")) {
            try {
                Server server = new Server(Integer.parseInt(args[2]));
                server.start();
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid port");
                e.printStackTrace();
            } catch (BindException e) {
                System.err.println("Can't bind to port. Already in use?");
                e.printStackTrace();
            }
            return;
        } else if (args.length == 4 && args[0].equalsIgnoreCase("-host") && args[2].equalsIgnoreCase("-port")) {
            DataHolder data = new DataHolder(args[1], Integer.parseInt(args[3]));
            DataManager.saveData(data);
        }
        Main.main(args);
    }
}
