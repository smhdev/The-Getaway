package BackEnd;

import java.io.FileWriter;
import java.util.HashMap;

/**
 * Class which represents not generated board for the game
 *
 * @author s0s10
 */

public class IniBoard {
    private int xSize, ySize; // Size of the board
    private Coordinate[] playerPos; // Position for the players
    private FloorTile[][] fixedTiles; // Tiles on the board
    private HashMap<TileType, Integer> silkBagArray; // Number of elements at the silkbag

    public IniBoard() {
        playerPos = new Coordinate[FileReader.MAX_NUM_OF_PLAYERS];// By default, might be changed
        silkBagArray = new HashMap<>();
        for (TileType tileType : TileType.values()) {
            silkBagArray.put(tileType, 0);
        }
    }

    public IniBoard(int xSize, int ySize, Coordinate[] playerPos, FloorTile[][] fixedTiles, HashMap<TileType, Integer> silkBagArray) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.playerPos = playerPos;
        this.fixedTiles = fixedTiles;
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

    public FloorTile getTileAt(int i, int j) {
        return fixedTiles[i][j];
    }

    public FloorTile[][] getFixedTiles() {
        return fixedTiles;
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
