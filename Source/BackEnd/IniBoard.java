package BackEnd;

import java.util.HashMap;

/**
 * Class which represents not generated board for the game
 *
 * @author s0s10
 */

public class IniBoard {
    private int xSize,ySize; // Size of the board
    private int playerNum; // Number of players on the board
    private Coordinate[] playerPos; // Position for the players
    private FloorTile[][] fixedTiles; // Tiles on the board
    private HashMap<TileType, Integer> silkBagArray; // Number of elements at the silkbag

    public IniBoard(){
        playerNum = 4;
        silkBagArray = new HashMap<>();
        playerPos = new Coordinate[playerNum];// By default, might be changed
        for (TileType tileType : TileType.values()) {
            silkBagArray.put(tileType, 0);
        }
        //silkBag = new int[TileType.values().length];
    }

    /*
        Getters and setters
    */

    public int getXSize(){
        return xSize;
    }

    public int getYSize(){
        return ySize;
    }

    public FloorTile getTileAt(int i, int j){
        return fixedTiles[i][j];
    }

    public FloorTile[][] getFixedTiles(){
        return  fixedTiles;
    }

    public int getNumOfTileTypes(TileType tileType){
        return silkBagArray.get(tileType);
    }

    public void setXSize(int xSize){
        this.xSize = xSize;
    }

    public void setYSize(int ySize){
        this.ySize = ySize;
    }

    public void setPlayerNum(int playerNum){
        this.playerNum = playerNum;
    }
}
