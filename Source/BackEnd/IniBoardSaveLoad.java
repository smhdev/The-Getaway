package BackEnd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to save and load iniBoard class and use it for the board editor
 *
 * @author s0s10
 */

public class IniBoardSaveLoad {

    // Reads required data
    public static IniBoard readIniBoard(String path) {
        IniBoard result = null;
        File file = null;
        Scanner in = null;

        try {
            file = new File(path);
            in = new Scanner(file);
            //in.useDelimiter("\n\r");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Data parsing son

        // Board size
        int xSize = in.nextInt();
        int ySize = in.nextInt();
        System.out.println("Board size : " + xSize + " " + ySize);

        // Player locations
        Coordinate[] playerPoses = getPlayerPos(in);

        // Silk bag content
        HashMap<TileType, Integer> silkBagContent = getSilkBagContent(in);

        // Tile positions
        ArrayList<FloorTile> fixedTiles = getTiles(in, xSize, ySize);

        //Generate such IniBoard
        result = new IniBoard(xSize, ySize, playerPoses, fixedTiles, silkBagContent);

        in.close();
        return result;
    }

    // Gets player positions using scanner
    private static Coordinate[] getPlayerPos(Scanner in) {
        Coordinate[] result = new Coordinate[FileReader.MAX_NUM_OF_PLAYERS];

        int xPos, yPos;
        Coordinate playerPos;

        for (int i = 0; i < FileReader.MAX_NUM_OF_PLAYERS; i++) {
            xPos = in.nextInt();
            yPos = in.nextInt();
            in.next();//Skip player name
            System.out.println("Player position : " + xPos + "," + yPos);

            playerPos = new Coordinate(xPos, yPos);
            result[i] = playerPos;
        }

        return result;
    }

    // Gets silk bag content using scanner
    private static HashMap<TileType, Integer> getSilkBagContent(Scanner in) {
        HashMap<TileType, Integer> result = new HashMap<>();

        // Parse every amount of tiles at the silk bag
        int numOfElem;
        for (TileType tileType : TileType.values()) {
            numOfElem = in.nextInt();
            in.next(); // Skip tile name
            result.put(tileType, numOfElem);
            System.out.println("Number of " + tileType + " is : " + numOfElem);
        }

        return result;
    }

    // Gets matrix with fixed tiles on the board
    private static ArrayList<FloorTile> getTiles(Scanner in, int xSize, int ySize) {
        ArrayList<FloorTile> result = new ArrayList<FloorTile>();
        int numOfTiles = in.nextInt();

        System.out.println("Number of tiles to add : " + numOfTiles);

        // Parse every fixed tile and add it to the matrix
        FloorTile tileToAdd;

        System.out.println("Fixed tiles");
        for (int i = 0; i < numOfTiles; i++) {
            tileToAdd = getFloorTile(in);
            result.add(tileToAdd);
        }

        // Parse every fixed tile and add it to the matrix
        int numOfUnfixedTiles;
        tileToAdd = null;

        System.out.println("Unfixed tiles");
        if (in.hasNextInt()){
            numOfUnfixedTiles = in.nextInt();
            for (int i = 0; i < numOfUnfixedTiles; i++){
                tileToAdd = getFloorTile(in);
                tileToAdd.setFixedBool(false);
                result.add(tileToAdd);
            }
        }

        return result;
    }

    // Gets tile from the file
    private static FloorTile getFloorTile(Scanner in){
        String tileTypeString;
        TileType tileType;
        int xPos, yPos, rotationState;
        Coordinate location;
        Rotation rotation;
        FloorTile result = null;

        tileTypeString = in.next().toUpperCase();
        tileType = TileType.valueOf(tileTypeString);

        xPos = in.nextInt();
        yPos = in.nextInt();
        location = new Coordinate(xPos, yPos);
        rotationState = in.nextInt();
        rotation = Rotation.values()[rotationState];

        result = new FloorTile(tileType);
        result.setLocation(location);
        result.setRotation(rotation);
        result.setFixedBool(true);

        String debugMess = String.format("Tile %s with position [%d,%d] and rotation %s added", tileTypeString, xPos, yPos, rotation);
        System.out.println(debugMess);

        return result;
    }

    // Writes data to file
    public static void writeIniBoard(String path) {

    }
}
