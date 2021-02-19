package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which represents not generated board for the game
 *
 * @author s0s10
 */

public class IniBoard {
    private int xSize, ySize; // Size of the board
    private Coordinate[] playerPos; // Position for the players
    //private FloorTile[][] tiles; // Tiles on the board
    private ArrayList<FloorTile> tiles; // Tiles on the board
    private HashMap<TileType, Integer> silkBagMap; // Number of elements at the silkbag

    public IniBoard() {
        playerPos = new Coordinate[FileReader.MAX_NUM_OF_PLAYERS];// By default, might be changed
        silkBagMap = new HashMap<>();
        for (TileType tileType : TileType.values()) {
            silkBagMap.put(tileType, 0);
        }
    }

    public IniBoard(int xSize, int ySize, Coordinate[] playerPos, ArrayList<FloorTile> tiles, HashMap<TileType, Integer> silkBagMap) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.playerPos = playerPos;
        this.tiles = tiles;
        this.silkBagMap = silkBagMap;
    }

    /*
        Getters and setters
    */

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    // Returns tile on the requested position if such exist and false otherwise
    public FloorTile getTileAt(int i, int j) {
        for (FloorTile tile : tiles){
           if (tile.getLocation().getX() == i && tile.getLocation().getY() == j){
               return tile;
           }
        }
        return null;
    }

    public int getNumOfElems(TileType tileType){
        return silkBagMap.get(tileType);
    }

    public ArrayList<FloorTile> getTileArray() {
        return tiles;
    }

    public int getNumOfTileTypes(TileType tileType) {
        return silkBagMap.get(tileType);
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    public Coordinate getPlayerSpawnPoint(int playerNum){
        return playerPos[playerNum];
    }
}
