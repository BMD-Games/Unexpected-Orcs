package File;


import Entities.Player;
import GUI.ScrollElement;
import Items.Inventory;

import java.io.*;
import java.util.HashMap;

import static Utility.Constants.*;

public class GameFile {
    
    public static void readStats(PrintWriter printer, HashMap<Integer, Integer> killsMap) {

        for (Integer tier : killsMap.keySet()) {
            printer.print(tier + "," + killsMap.get(tier) + ";");
        }
        printer.println();
    }


    public static void saveGame() {
        if (loadedPlayerName != null && loadedPlayerName != "") {
            saveStats(loadedPlayerName);
            saveInventory(loadedPlayerName);
        }
    }

    public static void saveInventory(String savename) {
        try {
            FileOutputStream invSaveFile = new FileOutputStream(game.sketchPath() + "/saves/" + savename + "/inventory.txt");
            ObjectOutputStream inv = new ObjectOutputStream(invSaveFile);
            inv.writeObject(engine.player.inv);
            inv.close();
            invSaveFile.close();
        }
        catch(Exception e) {
            game.println(e);
        }
    }

    public static Inventory loadInventory(String savename) {
        Inventory inv = new Inventory();

        try {
            FileInputStream invSaveFile = new FileInputStream(game.sketchPath() + "/saves/" + savename + "/inventory.txt");
            ObjectInputStream in = new ObjectInputStream(invSaveFile);
            game.println("yeet");
            inv =  (Inventory) in.readObject();
            game.println("peen");
            in.close();
            invSaveFile.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        game.println(inv);
        return inv;
    }

    public static void saveStats(String savename) {
        // set up file reader
        PrintWriter printer;

        // attack
        printer =  game.createWriter("/saves/" + savename + "/" + savename + ".txt");

        readStats(printer, engine.player.stats.attackKills);
        readStats(printer, engine.player.stats.defenceKills);
        readStats(printer, engine.player.stats.vitalityKills);
        readStats(printer, engine.player.stats.wisdomKills);
        readStats(printer, engine.player.stats.healthKills);
        readStats(printer, engine.player.stats.manaKills);
        readStats(printer, engine.player.stats.speedKills);

        printer.flush();
        printer.close();
    }

    public static Player readStats(String savename) throws IOException {

        Player playerToReturn = new Player(engine.currentLevel.start.x + 0.5f, engine.currentLevel.start.y + 0.5f);
        BufferedReader reader = null;
        try { // read the file
            reader = new BufferedReader(new FileReader(game.sketchPath() + "/saves/" + savename + "/" + savename + ".txt"));
        }
        catch (IOException ioException) {
            game.println("wank", ioException);
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

        playerToReturn.inv = loadInventory(savename);

        return playerToReturn;
    }

    public static ScrollElement[] loadSaves() {
        String path = game.sketchPath() + "/saves/";
        File f = new File(path);

        if(!f.exists()) {
            f.mkdir();
        }

        java.io.File folder = new java.io.File(game.sketchPath() + "/saves/");
        String[] listOfFiles = folder.list(new FilenameFilter() {
           @Override
            public boolean accept(File current, String name) {
               return new File(current, name).isDirectory();
           }
        });
        if(listOfFiles == null) listOfFiles = new String[0];
        ScrollElement[] scrollElements = new ScrollElement[listOfFiles.length];
        loadedPlayers = new Player[listOfFiles.length];

        for (int i = 0; i < listOfFiles.length; i++) {
            try {
                game.println(listOfFiles[i]);
                loadedPlayers[i] = readStats(listOfFiles[i]);
                scrollElements[i] = new ScrollElement(listOfFiles[i], loadedPlayers[i].stats.toString(), 200);
            }
            catch (Exception ioe) {
                game.println("twat", ioe);
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
