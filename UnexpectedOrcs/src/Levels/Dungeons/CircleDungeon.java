package Levels.Dungeons;

import Levels.Generator;
import Levels.Level;
import Levels.Room;
import Sprites.TileSet;

import static Utility.Constants.*;

public class CircleDungeon extends Level {

    /***
     Creates a dungeon and appends the tiles to the Level it's been given.

     level     - The level being generated for
     maxRooms  - Number of rooms the dungeon will have
     spread    - Angle variation of the rooms from existing rooms
     minRadius - minimum distance between rooms
     maxRadius - maximum distance between rooms
     spawnRoom - Preset for the spawn room
     bossRoom  - Preset for the boss romm
     rooms     - Presets for all other rooms
     ***/

    public CircleDungeon() {
        super(60, 45, "Circle", TileSet.cellarTileSet());
        Generator.generateConnectedDungeon(this, 20, game.PI/4, 5, 10, Room.diamondSpawn(), Room.circleBoss(), new Room[]{Room.circle5(), Room.circle7(), Room.circle11()});
    }
}