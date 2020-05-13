package de.jo0001.gloomhaven.battleGoalCards.core;

import de.jo0001.gloomhaven.battleGoalCards.other.DataHolder;
import de.jo0001.gloomhaven.battleGoalCards.other.DataManager;
import javafx.application.Application;

import java.io.*;

public class AppLauncher {

    public static void main(String[] args) throws IOException {
        if (args.length < 1&& false) {
            String currentDir = System.getProperty("user.dir");
            String jar = new java.io.File(AppLauncher.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName().replaceAll("%20", " ");
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("cmd.exe /c cd " + currentDir + " && java -jar \"" + jar + "\" -withcmd");
            System.out.println("Restarting");
            System.exit(-2);
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("-host") && args[2].equalsIgnoreCase("-port")) {
            DataHolder data = new DataHolder(args[1], Integer.parseInt(args[3]));
            DataManager.saveData(data);
        }
        Application.launch(Main.class);
    }
}
