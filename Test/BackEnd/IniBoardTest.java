package BackEnd;

import org.junit.jupiter.api.Test;

import java.io.File;

public class IniBoardTest {

    @Test
    void checkIniBoard() {
        IniBoard board = new IniBoard();
    }

    @Test
    void checkLoadSystem() {
        String path = "./CustomGameBoards/CustomBoard.txt";
        IniBoard board = IniBoardSaveLoad.readIniBoard(path);
    }

    @Test
    void checkSaveSystem() {
        String pathF = "./CustomGameBoards/CustomBoard.txt";
        IniBoard board = IniBoardSaveLoad.readIniBoard(pathF);
        String path = "./CustomGameBoards/CustomBoard2.txt";
        IniBoardSaveLoad.writeIniBoard(path, board);

        /*File file = new File(path);
        file.delete();*/
    }
}
