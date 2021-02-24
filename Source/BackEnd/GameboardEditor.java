package BackEnd;

import java.io.File;
import java.util.ArrayList;

/**
 * Controls custom game board editor by providing required functionality
 *
 * @author Mikhail Okrugov
 */

/*
    Should be possible to:
 */

public class GameboardEditor {
    private static final String DEFAULT_NAME = "CustomBoard";
    private static final String DEFAULT_MAP_PATH = "./CustomGameBoards";

    // Board data with it's name to save
    private CustomBoard board;
    private String fileName;

    /*
        Constructors

        (Not sure which constructors do we need so will make every variation)
     */

    public GameboardEditor() {
        board = new CustomBoard();
    }

    public GameboardEditor(CustomBoard board) {
        this.board = board;
    }

    public GameboardEditor(String fileName) {
        board = new CustomBoard();
        this.fileName = fileName;
    }

    public GameboardEditor(CustomBoard board, String fileName) {
        this.board = board;
        this.fileName = fileName;
    }

    /*
        Getters/Setters

        (Some of them might be useful)
     */

    public CustomBoard getBoard() {
        return board;
    }

    public void setBoard(CustomBoard board) {
        this.board = board;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    /*
        Operations with files

        (Such as save, load or delete, check if exists)
        (Should they be static or better to make them connected to the object?)
     */

    // Uploads selected file as CustomGameBoard object
    public static CustomBoard loadFile(String path) {
        CustomBoard board = CustomBoardSaveLoad.readIniBoard(path);
        return board;
    }

    // Save chosen file at the following path
    public static void saveFile(String path, CustomBoard boardToSave) {
        if (!checkIfFileExist(path)) {
            CustomBoardSaveLoad.writeIniBoard(path, boardToSave);
        } else {
            System.out.println("Such file already exists");
        }
    }

    // Function to delete custom level with a following path
    public static void deleteFile(String path) {
        if (checkIfFileExist(path)) {
            File file = new File(path);
            file.delete();
        } else {
            System.out.println("Can't delete such file because if already  exists");
        }

    }

    // Function to check if such file exist in the directory with the following path
    public static boolean checkIfFileExist(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /*
        File naming, default name/size
     */

    public static int getDefaultBoardXSize() {
        return CustomBoard.DEFAULT_X_SIZE;
    }

    public static int getDefaultBoardYSize() {
        return CustomBoard.DEFAULT_Y_SIZE;
    }

    public static String getNextDefaultName() {
        //private static final String DEFAULT_NAME = "CustomBoard";
        //private static final String DEFAULT_MAP_PATH = "./CustomGameBoards";

        boolean isFound = false;
        String result = null;
        String pathToCheck = null;
        int i = -1;

        // Check every name (starts with defaultName0.txt)
        while (!isFound) {
            i++;
            pathToCheck = String.format("%s/%s%d.txt", DEFAULT_MAP_PATH, DEFAULT_NAME, i);
            isFound = !checkIfFileExist(pathToCheck);

            //System.out.println("Checking " + pathToCheck);
            //System.out.println(isFound);
        }

        result = String.format("%s%d", DEFAULT_NAME, i);

        return result;
    }

    /*
        Main editing functions

        (Such as put, remove tile, make it fixed/unfixed, set default players positions, Check number of the goal tiles,
        Change location of the tile, rotate tile, check if tile already stands on the location, set content of the silkbag)
     */

    // Returns true if tile is on the position and false otherwise (do we need this function?)
    public boolean checkIfTileLayOnThePosition(Coordinate position) {
        if (board.getTileAt(position.getX(), position.getY()) != null) {
            return true;
        } else {
            return false;
        }
    }

    // Sets position for the player with selected index
    public void setPlayerPosition(int playerIndex, Coordinate position) {
        board.setPlayerSpawnPoint(playerIndex, position);
    }

    // Returns array list with all goal tiles
    private ArrayList<FloorTile> getAllGoalTiles() {
        ArrayList<FloorTile> result = new ArrayList<>();

        for (FloorTile floorTile : board.getTileArray()) {
            if (floorTile.getType() == TileType.GOAL) {
                result.add(floorTile);
            }
        }

        return result;
    }

    // Returns number of goal tiles on the board
    public int checkGoalTypeNum() {
        return getAllGoalTiles().size();
    }

    // Sets selected tile type amount
    public void setSilkBagContent(TileType type, int amount) {
        board.setSilkBagMapElement(type, amount);
    }

    // Puts tile on the selected location (doesn't check if such location is free)
    public void putTile(FloorTile tileToAdd) {
        /*if (!checkIfTileLayOnThePosition(position)) {
            board.getTileArray().add(tileToAdd);
        } else {
            System.out.println("Can't put a tile, tile already stands on the position");
        }*/
        board.getTileArray().add(tileToAdd);
    }

    // Removes tile with the following position
    public void removeTileOnPosition(Coordinate position) {
        FloorTile tileToRemove;
        if (checkIfTileLayOnThePosition(position)) {
            tileToRemove = board.getTileAt(position.getX(), position.getY());
            board.getTileArray().remove(tileToRemove);
        }
    }
}

