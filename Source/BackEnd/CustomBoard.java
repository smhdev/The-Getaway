package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which represents not generated board for the game
 *
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

    public CustomBoard() {
        xSize = DEFAULT_X_SIZE;
        ySize = DEFAULT_Y_SIZE;
        tiles = new ArrayList<>();
        playerPos = new Coordinate[FileReader.MAX_NUM_OF_PLAYERS];
        silkBagMap = new HashMap<>();
        for (TileType tileType : TileType.values()) {
            silkBagMap.put(tileType, 0);
        }
    }

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

    public void setPlayerSpawnPoint(int playerNum, Coordinate position){
        if (checkPosition(position)){
            playerPos[playerNum] = position;
        }else {
            System.out.println("Such position isn't possible");
        }
    }

    // Checks if such coordinate is valid
    public static boolean checkPosition(Coordinate position){
        boolean yCheck = position.getY() > -1 && position.getY() < DEFAULT_Y_SIZE;
        boolean xCheck = position.getX() > -1 && position.getX() < DEFAULT_X_SIZE;

        return xCheck && yCheck;
    }

    // Changes the number of the elements at the silkbag
    public void setSilkBagMapElement(TileType tileType, int amount){
        silkBagMap.replace(tileType, amount);
    }
}
