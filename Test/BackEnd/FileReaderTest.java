package BackEnd;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
	@Test
	public void basic() {
		Pair<Gameboard, Player[]> output = null;

		try {
			output = FileReader.customGameSetup("CustomBoard.txt");
		} catch (FileNotFoundException e) {
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}


		Gameboard gameboard = output.getKey();
		assertEquals(9, gameboard.getWidth());
		assertEquals(9, gameboard.getHeight());

		assertEquals("Corner",gameboard.
		assertEquals("Straight",gameboard.tileAt(new Coordinate(2,2)).toString());
		assertEquals("T_Shape",gameboard.tileAt(new Coordinate(3,3)).toString());
	}
}