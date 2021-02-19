package BackEnd;

import org.junit.jupiter.api.Test;

public class IniBoardTest {

    @Test
    void checkIniBoard() {
        IniBoard board = new IniBoard();
    }

    @Test
    void checkLoadSystem() {
        String path = "./Gameboards/Gameboard1.txt";
        IniBoard board = IniBoardSaveLoad.readIniBoard(path);
    }
}
