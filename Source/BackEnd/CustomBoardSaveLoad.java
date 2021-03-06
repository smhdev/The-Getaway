package BackEnd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to save and load iniBoard class and use it for the board editor
 *
 * @author Mikhail Okrugov
 */

public class CustomBoardSaveLoad {

    /**
     * Reads required data in the following path
     *
     * @param path where file will be read
     * @return Custom board instance with all required data
     */
    public static CustomBoard readIniBoard(String path) {
        CustomBoard result = null;
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
        result = new CustomBoard(xSize, ySize, playerPoses, fixedTiles, silkBagContent);

        in.close();
        return result;
    }

    /**
     * Gets player positions using scanner
     *
     * @param in scanner in the file to read
     * @return array list of every player spawn point
     */
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

    /**
     * Gets silk bag content using scanner
     *
     * @param in scanner in the file to read data
     * @return map with every amount of the tiles in the silkbag
     */
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

    /**
     * Gets matrix with fixed tiles on the board
     *
     * @param in    scanner in the file to read data
     * @param xSize x size of the board
     * @param ySize y size of the board
     * @return array list with all the tiles within the board
     */
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
        if (in.hasNextInt()) {
            numOfUnfixedTiles = in.nextInt();
            for (int i = 0; i < numOfUnfixedTiles; i++) {
                tileToAdd = getFloorTile(in);
                tileToAdd.setFixedBool(false);
                result.add(tileToAdd);
            }
        }

        return result;
    }

    /**
     * Gets tile from the file
     *
     * @param in scanner in the file to read data
     * @return floor tile we will add into the array
     */
    private static FloorTile getFloorTile(Scanner in) {
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

    /**
     * Writes data to file
     *
     * @param path        where the file belongs
     * @param boardToSave custom board which will be saved using the function
     */
    public static void writeIniBoard(String path, CustomBoard boardToSave) {
        CustomBoard result = null;
        File file = null;
        FileWriter writer = null;

        try {
            file = new File(path);
            writer = new FileWriter(file);

            // Write all the required data

            // Write board size
            writer.write(boardToSave.getXSize() + " " + boardToSave.getYSize() + "\r\n");

            // And the rest of the info
            writePlayerPos(writer, boardToSave);
            writeSilkBagContent(writer, boardToSave);
            writeTiles(writer, boardToSave);


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes players into the file in the following path
     *
     * @param writer writer in the file where the board will be saved
     * @param board  board with required data
     * @throws IOException exception if anything happen with the file
     */
    private static void writePlayerPos(FileWriter writer, CustomBoard board) throws IOException {
        Coordinate coor;

        for (int i = 0; i < FileReader.MAX_NUM_OF_PLAYERS; i++) {
            coor = board.getPlayerSpawnPoint(i);
            writer.write(coor.toString() + " Player" + i + "\r\n");
        }
    }

    /**
     * Writes silk bag content into the file
     *
     * @param writer writer within the file
     * @param board  with all the required data to save
     * @throws IOException exception if anything happen with the file
     */
    private static void writeSilkBagContent(FileWriter writer, CustomBoard board) throws IOException {
        int numOfElems;

        for (TileType tileType : TileType.values()) {
            numOfElems = board.getNumOfTileTypes(tileType);
            writer.write(numOfElems + " " + tileType.toString() + "\r\n");
        }
    }

    /**
     * Writes tiles into the file
     *
     * @param writer within the file where the board willbe savd
     * @param board  it contains all the required data
     * @throws IOException exception if anything happen with the file
     */
    private static void writeTiles(FileWriter writer, CustomBoard board) throws IOException {
        ArrayList<FloorTile> fixedTiles = new ArrayList<>();
        ArrayList<FloorTile> nonFixedTiles = new ArrayList<>();

        for (FloorTile floorTile : board.getTileArray()) {
            if (floorTile.isFixed()) {
                fixedTiles.add(floorTile);
            } else {
                nonFixedTiles.add(floorTile);
            }
        }
        writeTileArray(writer, fixedTiles);
        writeTileArray(writer, nonFixedTiles);

    }

    /**
     * Writes tile arrays into the file with their size at the beginning
     *
     * @param writer within the file where the board willbe savd
     * @param tiles  an array list with all the tiles to save
     * @throws IOException exception if anything happen with the file
     */
    private static void writeTileArray(FileWriter writer, ArrayList<FloorTile> tiles) throws IOException {
        TileType tileType;
        Coordinate tilePosition;
        Rotation tileRotation;
        int tileRotationInt = 0;
        String output;

        if (tiles.size() != 0) {
            writer.write(tiles.size() + "\r\n");
        }

        for (FloorTile floorTile : tiles) {
            tileType = floorTile.getType();
            tilePosition = floorTile.getLocation();
            tileRotation = floorTile.getRotation();

            // Transform value rotation into integer
            for (int i = 0; i < Rotation.values().length; i++) {
                if (tileRotation == Rotation.values()[i]) {
                    tileRotationInt = i;
                }
            }
            output = String.format("%s %d %d %d\r\n", tileType.toString(), tilePosition.getX(), tilePosition.getY(), tileRotationInt);
            writer.write(output);
        }
    }
}
