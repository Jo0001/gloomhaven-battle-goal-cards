package de.jo0001.gloomhaven.battleGoalCards.other;

import java.io.*;

public class DataManager {
    public static DataHolder readData() {
        DataHolder dh = null;
        try {
            FileInputStream fi = new FileInputStream(new File("gh-save.ser"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            dh = (DataHolder) oi.readObject();
            oi.close();
            fi.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return dh;
    }

    public static void saveData(DataHolder dh) {
        try {
            FileOutputStream f = new FileOutputStream(new File("gh-save.ser"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(dh);
            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
