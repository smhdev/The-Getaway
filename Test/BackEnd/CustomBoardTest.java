package BackEnd;

import org.junit.jupiter.api.Test;

public class CustomBoardTest {

    @Test
    void checkIniBoard() {
        CustomBoard board = new CustomBoard();
    }

    @Test
    void checkLoadSystem() {
        String path = "./CustomGameBoards/CustomBoard.txt";
        CustomBoard board = CustomBoardSaveLoad.readIniBoard(path);
    }

    @Test
    void checkSaveSystem() {
        String pathF = "./CustomGameBoards/CustomBoard.txt";
        CustomBoard board = CustomBoardSaveLoad.readIniBoard(pathF);
        String path = "./CustomGameBoards/CustomBoard2.txt";
        CustomBoardSaveLoad.writeIniBoard(path, board);

        /*File file = new File(path);
        file.delete();*/
    }
}
