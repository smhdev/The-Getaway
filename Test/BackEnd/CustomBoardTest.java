package BackEnd;

import org.junit.jupiter.api.Test;

import java.io.File;

public class CustomBoardTest {

    /*@Test
    void checkIniBoard() {
        CustomBoard board = new CustomBoard();
    }

    @Test
    void checkLoadSystem() {
        String path = "./CustomGameBoards/CustomBoard0.txt";
        CustomBoard board = CustomBoardSaveLoad.readIniBoard(path);
    }

    @Test
    void checkSaveSystem() {
        String pathF = "./CustomGameBoards/CustomBoard1.txt";
        CustomBoard board = CustomBoardSaveLoad.readIniBoard(pathF);
        String path = "./CustomGameBoards/CustomBoard2.txt";
        CustomBoardSaveLoad.writeIniBoard(path, board);

        // Finding next available file name
        String result = GameboardEditor.getNextDefaultName();
        System.out.println(result);

        File file = new File(path);
        file.delete();
    }*/

    @Test
    void checkGameboardEditor() {
        // Read file
        String path = "./CustomGameBoards/CustomBoard1.txt";
        CustomBoard board = GameboardEditor.loadFile(path);

        // Generates new GameboardEditor object with a newt default name
        System.out.println();

        String newName = GameboardEditor.getNextDefaultName();
        System.out.println("Next default name is : " + newName);
        GameboardEditor gameboardEditor = new GameboardEditor(board, newName);

        // Save and delete methods
        String pathToCheck = "./CustomGameBoards/" + gameboardEditor.getFileName() + ".txt";
        GameboardEditor.saveFile(pathToCheck, gameboardEditor.getBoard());
        GameboardEditor.deleteFile(pathToCheck);

        // Check uploaded board
        System.out.println();
        System.out.println(gameboardEditor.getBoard().toString());

        // Some interaction with such board
        System.out.println("Is laying before removing: " + gameboardEditor.checkIfTileLayOnThePosition(new Coordinate(2, 2)));
        System.out.println("Is laying before adding: " + gameboardEditor.checkIfTileLayOnThePosition(new Coordinate(3, 2)));

        FloorTile tileToAdd = new FloorTile(TileType.CORNER);
        tileToAdd.setFixedBool(true);
        tileToAdd.setRotation(Rotation.DOWN);
        tileToAdd.setLocation(new Coordinate(3, 2));
        gameboardEditor.putTile(tileToAdd);

        System.out.println("Is laying after adding: " + gameboardEditor.checkIfTileLayOnThePosition(new Coordinate(3, 2)));

        gameboardEditor.setSilkBagContent(TileType.FIRE, 5);// 0 before
        gameboardEditor.removeTileOnPosition(new Coordinate(2, 2));
        System.out.println("Is laying after removing: " + gameboardEditor.checkIfTileLayOnThePosition(new Coordinate(2, 2)));

        System.out.println("GOAL tiles number: " + gameboardEditor.checkGoalTypeNum());

        gameboardEditor.setPlayerPosition(0, new Coordinate(1, 1));

        System.out.println();
        System.out.println(gameboardEditor.getBoard().toString());

    }
}
