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
    private HashMap<TileType, Integer> silkBagArray; // Number of elements at the silkbag

    public IniBoard() {
        playerPos = new Coordinate[FileReader.MAX_NUM_OF_PLAYERS];// By default, might be changed
        silkBagArray = new HashMap<>();
        for (TileType tileType : TileType.values()) {
            silkBagArray.put(tileType, 0);
        }
    }

    public IniBoard(int xSize, int ySize, Coordinate[] playerPos, ArrayList<FloorTile> tiles, HashMap<TileType, Integer> silkBagArray) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.playerPos = playerPos;
        this.tiles = tiles;
        this.silkBagArray = silkBagArray;
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

    public ArrayList<FloorTile> getTileAt() {
        return tiles;
    }

    public int getNumOfTileTypes(TileType tileType) {
        return silkBagArray.get(tileType);
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }
}
