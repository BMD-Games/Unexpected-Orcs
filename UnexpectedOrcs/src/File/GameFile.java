package File;


import Entities.Player;
import GUI.Scroll.PlayerDisplayElement;
import GUI.Scroll.ScrollElement;
import Items.Inventory;
import Stats.PlayerStats;
import Utility.Util;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import static Utility.Constants.*;

public class GameFile {

    //saves the players inv and stats to their folder
    public static void saveGame() {
        if (!guestMode && loadedPlayerName != null && loadedPlayerName != "") {
            checkSavesFolderExists();
            checkSaveFolderExists(loadedPlayerName);
            //saveStats2(loadedPlayerName); //-->DEPRECATED
            saveStats(loadedPlayerName);
            saveInventory(loadedPlayerName);
        }
    }

    //Constructs a player object from the inv and stats stored in its file
    //if none exist (ie missing files) it will be reset to default values
    public static Player loadPlayer(String savename) {
        Player player = new Player();
        player.stats = loadStats(savename);
        player.inv = loadInventory(savename);
        return player;
    }

    //saves the current players inventory using serialization
    public static void saveInventory(String savename) {
        try {
            FileOutputStream invSaveFile = new FileOutputStream(game.sketchPath() + "/saves/" + savename + "/inventory.txt");
            ObjectOutputStream inv = new ObjectOutputStream(invSaveFile);
            inv.writeObject(engine.player.inv);
            inv.close();
            invSaveFile.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //saves the current players stats using serialization
    public static void saveStats(String savename) {
        try {
            FileOutputStream statsSaveFile = new FileOutputStream(game.sketchPath() + "/saves/" + savename + "/stats.txt");
            ObjectOutputStream inv = new ObjectOutputStream(statsSaveFile);
            inv.writeObject(engine.player.stats);
            inv.close();
            statsSaveFile.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //loads the current players inventory using serialization
    public static Inventory loadInventory(String savename) {
        Inventory inv = new Inventory();

        try {
            FileInputStream invSaveFile = new FileInputStream(game.sketchPath() + "/saves/" + savename + "/inventory.txt");
            ObjectInputStream in = new ObjectInputStream(invSaveFile);
            inv =  (Inventory) in.readObject();
            in.close();
            invSaveFile.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return inv;
    }

    //loads the current players stats using serialization
    public static PlayerStats loadStats(String savename) {
        PlayerStats stats = new PlayerStats();
        try {
            FileInputStream invSaveFile = new FileInputStream(game.sketchPath() + "/saves/" + savename + "/stats.txt");
            ObjectInputStream in = new ObjectInputStream(invSaveFile);
            stats =  (PlayerStats) in.readObject();
            in.close();
            invSaveFile.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    //ensure saves file is present, if not -> create it
    public static File checkSavesFolderExists() {
        String path = game.sketchPath() + "/saves/";
        File f = new File(path);

        if(!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    public static void checkSaveFolderExists(String savename) {
        String path = game.sketchPath() + "/saves/" + savename;
        File f = new File(path);

        if(!f.exists()) {
            f.mkdirs();
        }
    }

    //loads all saves and generates all loading screen scroll elements
    public static ScrollElement[] loadAllSaves() {

        File folder = checkSavesFolderExists();

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
                //game.println(listOfFiles[i]);
                loadedPlayers[i] = loadPlayer(listOfFiles[i]);//readStats(listOfFiles[i]);
                scrollElements[i] = new PlayerDisplayElement(listOfFiles[i], loadedPlayers[i].stats, loadedPlayers[i].inv);
            }
            catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }

        return scrollElements;
    }

    //deletes all files stored under the savename
    public static void deleteSave(String savename) {
        game.println("delete file: " + savename);
        Util.deleteFile(new File(game.sketchPath() + "/saves/" + savename));
    }

    //returns the number of player files currently saved
    public static int numSaves() {
        return checkSavesFolderExists().list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        }).length;
    }

    public static String[] allFilesInDirectory(String path) {
        File file = new File(game.sketchPath() + path);

        String[] allFiles = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isFile();
            }
        });

        return (allFiles == null) ? new String[0] : allFiles;
    }


    //--------OLD CODE--------//


    //DEPRECATED
    //recontructs the kills hashmap from txt files
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

    //DEPRECATED
    //reads stats using old txt file method
    public static Player readStats(String savename) throws IOException {

        Player playerToReturn = new Player(engine.currentLevel.start.x + 0.5f, engine.currentLevel.start.y + 0.5f);
        BufferedReader reader = null;
        try { // read the file
            reader = new BufferedReader(new FileReader(game.sketchPath() + "/saves/" + savename + "/" + savename + ".txt"));
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
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

    //DEPRECATED
    //saves stats using old txt file method
    public static void saveStats2(String savename) {
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

    //DEPRECATED
    //saves player kills hashmap to a txt file
    public static void readStats(PrintWriter printer, HashMap<Integer, Integer> killsMap) {

        for (Integer tier : killsMap.keySet()) {
            printer.print(tier + "," + killsMap.get(tier) + ";");
        }
        printer.println();
    }
}
