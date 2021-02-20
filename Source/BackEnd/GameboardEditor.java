package BackEnd;

import java.io.File;

/**
 *  Controls custom game board editor by providing required functionality
 *
 * @author s0s10
 */

/*
    Should be possible to:
 */
    // Create new file
    // Delete existing
    // Modify existing
    // Name it as well

    // Add/remove tiles from the board
    // Fix/unfix them
    // Rotate them
    // Change their position
    // Control max num of the goal tiles

    // Set players position
    // Set number of every element on the board

public class GameboardEditor {
    private static final String DEFAULT_NAME = "CustomBoard";

    private CustomBoard board;
    private String fileName;

    // Default constructor, creates default version of the board
    public GameboardEditor(){
        board = new CustomBoard();
    }

    /*
        Operations with files
     */

    // Function to delete custom level with a following path
    public static void deleteFile(String path){
        File file = new File(path);
        file.delete();
    }

    // Finds next possible default name
    private static String findDefaultName(){
        String result = null;

        

        return result;
    }

}
