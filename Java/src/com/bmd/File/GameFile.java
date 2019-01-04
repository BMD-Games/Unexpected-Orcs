package com.bmd.File;

import com.bmd.App.Main;
import com.bmd.GUI.ScrollElement;
import com.bmd.Items.Inventory;
import com.bmd.Player.Player;

import java.io.*;
import java.util.HashMap;

public class GameFile {
    public static void readStats(PrintWriter printer, HashMap<Integer, Integer> killsMap) {
        if(printer == null) return;
        for (Integer tier : killsMap.keySet()) {
            printer.print(tier + "," + killsMap.get(tier) + ";");
        }
        printer.println();
    }


    public static void saveGame() {
        if (Main.loadedPlayerName != null && Main.loadedPlayerName != "") {
            saveStats(Main.loadedPlayerName);
            //saveInventory(loadedPlayerName);
        }
    }

    public static void saveInventory(String savename) {
        try {
            FileOutputStream invSaveFile = new FileOutputStream("/saves/" + savename + "/inventory.txt");
            ObjectOutputStream inv = new ObjectOutputStream(invSaveFile);
            inv.writeObject(Main.engine.player.inv);
            inv.close();
            invSaveFile.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Inventory loadInventory(String savename) {
        Inventory inv = new Inventory();

        try {
            FileInputStream invSaveFile = new FileInputStream("/saves/" + savename + "/inventory.txt");
            ObjectInputStream in = new ObjectInputStream(invSaveFile);
            System.out.println("yeet");
            inv =  (Inventory) in.readObject();
            System.out.println("peen");
            in.close();
            invSaveFile.close();
        }
        catch(Exception e) {
            System.out.println("load: " + e);
        }
        System.out.println(inv);
        return inv;
    }

    public static void saveStats(String savename) {
        // set up file reader
        PrintWriter printer = null;

        // attack
        try {
            printer = new PrintWriter("/saves/" + savename + "/" + savename + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        readStats(printer, Main.engine.player.stats.attackKills);
        readStats(printer, Main.engine.player.stats.defenceKills);
        readStats(printer, Main.engine.player.stats.vitalityKills);
        readStats(printer, Main.engine.player.stats.wisdomKills);
        readStats(printer, Main.engine.player.stats.healthKills);
        readStats(printer, Main.engine.player.stats.manaKills);
        readStats(printer, Main.engine.player.stats.speedKills);

        printer.flush();
        printer.close();
    }

    public static Player readStats(String savename) throws IOException {

        Player playerToReturn = new Player(Main.engine.currentLevel.start.x + 0.5f, Main.engine.currentLevel.start.y + 0.5f);
        BufferedReader reader = null;
        try { // read the file
            System.out.println("yeet");
            reader = new BufferedReader(new FileReader("/saves/" + savename + "/" + savename + ".txt"));
            System.out.println("double yeet");
        }
        catch (IOException ioException) {
            System.out.println(ioException);
        }

        if (reader == null) {
            return playerToReturn;
        }

        playerToReturn.stats.attackKills = makeHashmap(reader);
        playerToReturn.stats.defenceKills = makeHashmap(reader);
        playerToReturn.stats.vitalityKills = makeHashmap(reader);
        playerToReturn.stats.wisdomKills = makeHashmap(reader);
        playerToReturn.stats.healthKills = makeHashmap(reader);
        playerToReturn.stats.manaKills = makeHashmap(reader);
        playerToReturn.stats.speedKills = makeHashmap(reader);
        playerToReturn.stats.calcAllStats();

        //playerToReturn.inv = loadInventory(savename);

        return playerToReturn;
    }

    public static ScrollElement[] loadSaves() {

        java.io.File folder = new java.io.File("/saves/");
        String[] listOfFiles = folder.list(new FilenameFilter() {
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
       });
        ScrollElement[] scrollElements = new ScrollElement[listOfFiles.length];
        Main.loadedPlayers = new Player[listOfFiles.length];

        for (int i = 0; i < listOfFiles.length; i++) {
            try {
                System.out.println(listOfFiles[i]);
                Main.loadedPlayers[i] = readStats(listOfFiles[i]);
                scrollElements[i] = new ScrollElement(listOfFiles[i], Main.loadedPlayers[i].stats.toString(), 200);
            }
            catch (Exception ioe) {
                System.out.println(ioe);
            }
        }

        return scrollElements;
    }

    public static HashMap<Integer, Integer> makeHashmap(BufferedReader reader) {

        HashMap <Integer, Integer> statsMap = new HashMap<Integer, Integer>();
        String[] splitLine = new String[0];
        try {
            String line = reader.readLine();
            if (line != null && line.indexOf(";") != -1) {
                splitLine = line.split(";");
            }
        }
        catch (IOException ioe) {
        }

        for (String pair : splitLine) {
            int i = pair.indexOf(',');
            if(i == -1) continue;
            Integer tier = Integer.parseInt(pair.substring(0, i));
            Integer killCount = Integer.parseInt(pair.substring(i + 1));
            statsMap.put(tier, killCount);
        }
        return statsMap;
    }
}
