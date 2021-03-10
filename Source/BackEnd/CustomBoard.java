package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A custom, user-generated board.
 * @author Mikhail Okrugov
 */

public class CustomBoard {
    public static final int DEFAULT_X_SIZE = 10; // To those values will be set size of the board by default
    public static final int DEFAULT_Y_SIZE = 10;

    private int xSize, ySize; // Size of the board
    private Coordinate[] playerPos; // Position for the players
    //private FloorTile[][] tiles; // Tiles on the board
    private ArrayList<FloorTile> tiles; // Tiles on the board
    private HashMap<TileType, Integer> silkBagMap; // Number of elements at the silkbag

    /**
     * Creates a custom board with default size, tile count and player positions.
     */
    public CustomBoard() {
        xSize = DEFAULT_X_SIZE;
        ySize = DEFAULT_Y_SIZE;
        tiles = new ArrayList<>();
        playerPos = new Coordinate[FileReader.MAX_NUM_OF_PLAYERS];
        for (int i = 0; i < FileReader.MAX_NUM_OF_PLAYERS; i++) {
            playerPos[i] = null;
        }
        silkBagMap = new HashMap<>();
        for (TileType tileType : TileType.values()) {
            silkBagMap.put(tileType, 0);
        }
    }

    /**
     * Creates a custom board with given parameters.
     * @param xSize      width of the board
     * @param ySize      height of the board
     * @param playerPos  positions of the players
     * @param tiles      tiles which will be in the board
     * @param silkBagMap quantity of each tile type in the silk bag
     */
    public CustomBoard(int xSize, int ySize, Coordinate[] playerPos, ArrayList<FloorTile> tiles, HashMap<TileType, Integer> silkBagMap) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.playerPos = playerPos;
        this.tiles = tiles;
        this.silkBagMap = silkBagMap;
    }

    /*
        Getters and setters
    */

    /**
     * Gets the length of the board.
     * @return the length of the board
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * Gets the height of the board.
     * @return the height of the board
     */
    public int getYSize() {
        return ySize;
    }

    /**
     * Gets the tile in the following position
     *
     * @param i x position of the tile
     * @param j y position of the tile
     * @return tile on the requested position if such exist and null otherwise
     */
    public FloorTile getTileAt(int i, int j) {
        for (FloorTile tile : tiles) {
            if (tile.getLocation().getX() == i && tile.getLocation().getY() == j) {
                return tile;
            }
        }
        return null;
    }

    /**
     * Get array with all tiles
     *
     * @return Tile array
     */
    public ArrayList<FloorTile> getTileArray() {
        return tiles;
    }

    /**
     * Get number of tiles in the silk bag map
     *
     * @param tileType type to get the number for
     * @return number of the elements
     */
    public int getNumOfTileTypes(TileType tileType) {
        return silkBagMap.get(tileType);
    }

    /**
     * Set the x size of the board
     *
     * @param xSize new x size
     */
    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    /**
     * Set the y size of the board
     *
     * @param ySize new y size
     */
    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    /**
     * Gets the location of the spawn point of the given player
     *
     * @param playerNum zero-based index of player
     * @return The spawn point of the given player
     */
    public Coordinate getPlayerSpawnPoint(int playerNum) {
        return playerPos[playerNum];
    }

    /**
     * Set position for the selected player's spawn
     *
     * @param playerNum zero-based index of the player
     * @param position  the new spawn position
     */
    public void setPlayerSpawnPoint(int playerNum, Coordinate position) {
        if (checkPosition(position)) {
            playerPos[playerNum] = position;
        } else {
            System.out.println("Such position isn't possible");
        }
    }

    /**
     * Returns all the info about custom board content
     *
     * @return the board data as a String
     */
    public String toString() {
        String result = new String();

        //Size, players positions, tiles on the board, content of the silkbag
        result += String.format("Size:%d,%d\r\n", getXSize(), getYSize());

        for (int i = 0; i < playerPos.length; i++) {
            result += String.format("Player%d pos: %d,%d\n", i, playerPos[i].getX(), playerPos[i].getY());
        }

        for (FloorTile tile : tiles) {
            result += tile.getLocation().toString();
            result += " " + tile.getType().toString();
            result += " " + tile.isFixed();
            result += " " + tile.getRotation().toString() + "\n";
        }

        for (TileType type : TileType.values()) {
            result += String.format("%s:%d\r\n", type.toString(), silkBagMap.get(type));
        }

        return result;
    }

    /**
     * Checks if such coordinate is valid
     *
     * @param position the position to check
     * @return is such position is valid or not
     */
    public static boolean checkPosition(Coordinate position) {
        boolean yCheck = position.getY() > -1 && position.getY() < DEFAULT_Y_SIZE;
        boolean xCheck = position.getX() > -1 && position.getX() < DEFAULT_X_SIZE;

        return xCheck && yCheck;
    }

    /**
     * Gets the number of tiles of a given type in the silk bag
     * @param tileType The tile type to get the count for
     * @return The number of tiles of that type
     */
    public int getSilkBagMapElement(TileType tileType) { return silkBagMap.get(tileType); }

    /**
     * Changes the number of the elements at the silkbag
     *
     * @param tileType type of the tile to change the value
     * @param amount   new amount of such tile elements
     */
    public void setSilkBagMapElement(TileType tileType, int amount) {
        silkBagMap.replace(tileType, amount);
    }

    /**
     * Get every players positions
     *
     * @return array list with player positions
     */
    public Coordinate[] getPlayerPos() {
        return playerPos;
    }
}
