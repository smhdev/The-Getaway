package BackEnd;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class to save and load iniBoard class and use it for the board editor
 *
 * @author s0s10
 */

public class IniBoardSaveLoad {

    // Reads required data
    public static IniBoard readIniBoard(String path){
        IniBoard result = new IniBoard();
        File file = null;
        Scanner in = null;

        try{
            file = new File(path);
            in = new Scanner(file);
            in.useDelimiter("\n\r");
        }catch (IOException e){
            e.printStackTrace();
        }

        // Data parsing son
        Coordinate[] playerPoses = getPlayerPos(in);

        in.close();
        return result;
    }

    private static Coordinate[] getPlayerPos(Scanner in){
        Coordinate[] result = null;

        int playerNum = in.nextInt();
        System.out.println("Player number : " + playerNum);

        int xPos, yPos;
        Coordinate playerPos;

        for (int i = 0; i < playerNum; i++){
            xPos = in.nextInt();
            yPos = in.nextInt();
            System.out.println("Player position : " + xPos + "," + yPos);
            playerPos = new Coordinate(xPos,yPos);
        }

        return result;
    }

    // Writes data to file
    public static void writeIniBoard(String path){

    }
}
