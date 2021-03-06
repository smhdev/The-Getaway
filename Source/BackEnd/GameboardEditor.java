package BackEnd;

import java.io.File;
import java.util.ArrayList;

/**
 * Controls custom game board editor by providing required functionality
 *
 * @author Mikhail Okrugov
 */

public class GameboardEditor {
    // Default file name and folder to save
    private static final String DEFAULT_NAME = "CustomBoard";
    private static final String DEFAULT_MAP_PATH = "./CustomGameBoards";

    // Board data with it's name to save and file name
    private CustomBoard board;
    private String fileName;

    /**
     * Default constructor with selecting board and default name
     *
     * @param board board with all required data
     */
    public GameboardEditor(CustomBoard board) {
        fileName = getNextDefaultName();
        this.board = board;
    }

    /*
    public GameboardEditor(String fileName) {
        board = new CustomBoard();
        this.fileName = fileName;
    }*/

    /**
     * Constructor with board and file name as parameter
     *
     * @param board
     * @param fileName
     */
    public GameboardEditor(CustomBoard board, String fileName) {
        this.board = board;
        this.fileName = fileName;
    }

    /*
        Getters/Setters

        (Some of them might be useful)
     */

    /**
     * Returns board
     *
     * @return board with all data to use
     */
    public CustomBoard getBoard() {
        return board;
    }

    /**
     * Setter for the board dataset parameter
     *
     * @param board board to set
     */
    public void setBoard(CustomBoard board) {
        this.board = board;
    }

    /**
     * Returns name of the file with which the board will be saved
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Settter for the file name
     *
     * @param fileName new name of the board file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    /*
        Operations with files

        (Such as save, load or delete, check if exists)
        (Should they be static or better to make them connected to the object?)
     */

    /**
     * Uploads selected file as CustomGameBoard object
     *
     * @param path path where file will be uploaded
     * @return CustomBoard which will be uploaded from the file
     */
    public static CustomBoard loadFile(String path) {
        CustomBoard board = CustomBoardSaveLoad.readIniBoard(path);
        return board;
    }

    /**
     * Save chosen file at the following path (also includes word custom at the beginning
     * s
     *
     * @return true if saved successfully and false otherwise
     */
    public boolean saveFile() {
        // Check if the name contains word custom
        String lowPath = fileName.toLowerCase();
        String[] fileNameArray = fileName.split("/");
        String fileName = fileNameArray[fileNameArray.length - 1];

        // Debug
        System.out.println("Required path is: " + lowPath);
        System.out.println("File name: " + fileName);

        if (fileName.length() >= 6){
            if (fileName.substring(6).equals("custom")) {
                fileNameArray[fileNameArray.length - 1] = "Custom" + fileName;
            }
        }else{
            fileNameArray[fileNameArray.length - 1] = "Custom" + fileName;
        }

        // Generate new file name containing word custom at the beginning
        String resultString = new String();
        for (int i = 0; i < fileNameArray.length; i++) {
            resultString += fileNameArray[i];
        }

        // Check if contain at least 1 goal tile and all player positions
        // Will do it today
        if (!containGoalTiles() || !checkIFAllPlayersExist()){
            return false;
        }

            // Is it required?
            //if (!checkIfFileExist(path)) {
            System.out.println("Saved at the path +" + resultString);
        CustomBoardSaveLoad.writeIniBoard(resultString, board);
        return true;
        /*} else {
            System.out.println("Such file already exists");
        }*/
    }

    /**
     * Function to delete custom level with a following path
     *
     * @param path where the file is located
     */
    public static void deleteFile(String path) {
        if (checkIfFileExist(path)) {
            File file = new File(path);
            file.delete();
        } else {
            System.out.println("Can't delete such file because if already  exists");
        }

    }

    /**
     * Function to check if such file exist in the directory with the following path
     *
     * @param path of the file where it belongs
     * @return true if exists and false otherwise
     */
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

    /**
     * Returns current x board size
     *
     * @return x size of the board
     */
    public static int getDefaultBoardXSize() {
        return CustomBoard.DEFAULT_X_SIZE;
    }

    /**
     * Returns current y board size
     *
     * @return y size of the board
     */
    public static int getDefaultBoardYSize() {
        return CustomBoard.DEFAULT_Y_SIZE;
    }

    /**
     * Get next default name with which file will be saved
     *
     * @return next possible name to save
     */
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

    /**
     * Check if tile lays on selected position
     *
     * @param position to check
     * @return Returns true if tile is on the position and false otherwise
     */
    public boolean checkIfTileLayOnThePosition(Coordinate position) {
        if (board.getTileAt(position.getX(), position.getY()) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets position for the player with selected index
     *
     * @param playerIndex player index in array
     * @param position    position to set player position
     * @return true if set is successful and false otherwise
     */
    public boolean setPlayerPosition(int playerIndex, Coordinate position) {
        ArrayList<FloorTile> tiles = getAllGoalTiles();
        for (FloorTile tile : tiles) {
            if (tile.getLocation().equals(position)) {
                return false;
            }
        }
        board.setPlayerSpawnPoint(playerIndex, position);
        return true;
    }

    /**
     * Function to get all the goal tiles within the board
     *
     * @return array list with all goal tiles
     */
    private ArrayList<FloorTile> getAllGoalTiles() {
        ArrayList<FloorTile> result = new ArrayList<>();

        for (FloorTile floorTile : board.getTileArray()) {
            if (floorTile.getType() == TileType.GOAL) {
                result.add(floorTile);
            }
        }

        return result;
    }

    /**
     * Function which checks number of the goal tiles
     *
     * @return number of goal tiles on the board
     */
    public int checkGoalTypeNum() {
        return getAllGoalTiles().size();
    }

    /**
     * Sets selected tile type amount
     *
     * @param type   type of the time in the silkbag
     * @param amount number of selected element type
     */
    public void setSilkBagContent(TileType type, int amount) {
        board.setSilkBagMapElement(type, amount);
    }

    /**
     * Puts tile on the selected location (doesn't check if such location is free)
     *
     * @param tileToAdd tile which will be added to the board array list
     * @return true if successfully put and false otherwise
     */
    public boolean putTile(FloorTile tileToAdd) {
        /*if (!checkIfTileLayOnThePosition(position)) {
            board.getTileArray().add(tileToAdd);
        } else {
            System.out.println("Can't put a tile, tile already stands on the position");
        }*/

        // Goal tile checks
        if (tileToAdd.getType() == TileType.GOAL) {
            // Check the other goal tiles on the board
            /*if (containGoalTiles()) {
                System.out.println("Gameboard already contain fixed tile so ignore putTile");
                return false;
            }*/

            // Check if player located on the
            if (checkIfPlayerOnTheTile(tileToAdd)) {
                System.out.println("Player stands on the position");
                return false;
            }

            // Set the goal tile fixed
            System.out.println("It's a goal tile so set it fixed");
            tileToAdd.setFixed();
        }

        board.getTileArray().add(tileToAdd);
        return true;
    }

    /**
     * Removes tile with the following position
     *
     * @param position position of the tile where it is expected to be
     */
    public void removeTileOnPosition(Coordinate position) {
        FloorTile tileToRemove;
        if (checkIfTileLayOnThePosition(position)) {
            tileToRemove = board.getTileAt(position.getX(), position.getY());
            board.getTileArray().remove(tileToRemove);
        }
    }

    /**
     * Checks if the array list contains goal tile
     *
     * @return true if board contain goal tile in the array
     */
    private boolean containGoalTiles() {
        for (FloorTile tile : board.getTileArray()) {
            if (tile.getType() == TileType.GOAL) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any player stands on the tile position
     *
     * @param tile tile on which player will be checked
     * @return true if player stands on the tile and false otherwise
     */
    private boolean checkIfPlayerOnTheTile(FloorTile tile) {
        for (Coordinate coordinate : board.getPlayerPos()) {
            if (coordinate != null) {
                if (tile.getLocation().equals(coordinate)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if every players position is set on the board
     *
     * @return true if all players position is set and false otherwise
     */
    private boolean checkIFAllPlayersExist() {
        for (Coordinate coordinate : board.getPlayerPos()) {
            if (coordinate == null) {
                return false;
            }
        }
        return true;
    }
}

