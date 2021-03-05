package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Used to control the options for level editor
 *
 * @version 1.0
 * @since 1.0
 * @author Liam B
 */
public class LevelEditorMenuController extends StateLoad{

    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
    private boolean levelEditorOption = true; // True for new level or False for existing level

    @FXML
    private Button backButton;
    @FXML
    private RadioButton newLevel;
    @FXML
    private RadioButton existingLevel;
    @FXML
    private FlowPane existingOption;
    @FXML
    private ChoiceBox<String> selectGameBoard;
    @FXML
    private TextField nameIn;
    @FXML
    private TextField widthIn;
    @FXML
    private TextField heightIn;


    /**
     * Sets up objects in the class
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup levelEditorOption = new ToggleGroup();
        newLevel.setToggleGroup(levelEditorOption);
        existingLevel.setToggleGroup(levelEditorOption);

        existingOption.setDisable(true);

        if (selectGameBoard.getValue() == null) {
            String[] gameBoards;
            File gameBoardLocation = new File("Gameboards");
            gameBoards = gameBoardLocation.list();
            if (gameBoards != null) {
                for (String gameBoard : gameBoards) {
                    gameBoard = gameBoard.substring(0, gameBoard.length() - 4);
                    selectGameBoard.getItems().add(gameBoard);
                }
            }
            selectGameBoard.getSelectionModel().selectFirst();
        }

        emptyTextBoxes();
    }

    /**
     * Returns to main menu
     */
    public void onBackButton() {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("MenuScreen", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * Takes you to the level editor if requirements are met.
     */
    public void onStartButton() {

        boolean inputError = false; //True if error

        if (!correctBoardName(nameIn.getText())) {
            nameIn.setText("");
            nameIn.setStyle("-fx-prompt-text-fill:red;");
            inputError = true;
        }
        if (!correctBoardSize(widthIn.getText())) {
            widthIn.setText("");
            widthIn.setStyle("-fx-prompt-text-fill:red;");
            inputError = true;
        }
        if (!correctBoardSize(heightIn.getText())) {
            heightIn.setText("");
            heightIn.setStyle("-fx-prompt-text-fill:red;");
            inputError = true;
        }
        if (inputError) {
            return;
        }

        //Take you to level editor
        if (newLevel.isSelected()) {
            // User chose to create a new level
            getInitData().put("Custom Board Is New", "true");
        } else {
            // User chose to modify existing level
            getInitData().put("Custom Board Is New", "false");
        }
        getInitData().put("Custom Board Name", nameIn.getText());
        getInitData().put("Custom Board Width", widthIn.getText());
        getInitData().put("Custom Board Height", heightIn.getText());

        WindowLoader wl = new WindowLoader(backButton);
        wl.load("LevelEditor", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));

    }

    /**
     * Fills text boxes with pre-existing board data
     */
    public void boardSelect() {
        nameIn.setText(selectGameBoard.getValue());

        File loadFile = new File("Gameboards/" + selectGameBoard.getValue() + ".txt");
        Scanner in = null;
        try {
            in = new Scanner(loadFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int width = in.nextInt();
        int height = in.nextInt();

        in.close();

        widthIn.setText(String.valueOf(width));
        heightIn.setText(String.valueOf(height));
    }

    /**
     * Checks the board size in is a set size
     * @param sizeIn The board input size
     * @return True if size is valid, else False
     */
    private boolean correctBoardSize(String sizeIn) {

        final int MAX_IN = 100;
        final int MIN_IN = 3;
        int numIn = 0;

        try {
            numIn = Integer.parseInt(sizeIn);
        } catch (NumberFormatException e) {
            System.out.println("NOT a int input");
        }

        return numIn >= MIN_IN && numIn <= MAX_IN;
    }

    /**
     * Checks the board name is unique
     * @param nameIn The name of the new board level
     * @return True if the name is unique, else False
     */
    private boolean correctBoardName(String nameIn) throws NullPointerException{
        String[] gameBoards;
        File gameBoardLocation = new File("Gameboards");
        gameBoards = gameBoardLocation.list();

        // If String is empty
        if (nameIn.equalsIgnoreCase("")) {
            return false;
        }

        //If true its a existing board so it already exists
        if (!levelEditorOption) {
            return true;
        }

        for (String board : gameBoards) {
            board = board.substring(0, board.length() - 4);

            if (nameIn.equalsIgnoreCase(board)){
                return false;
            }
        }

        return true;
    }

    /**
     * Toggles
     */
    public void onLevelEditorToggle(){
        levelEditorOption = !levelEditorOption;
        existingOption.setDisable(levelEditorOption);

        if (levelEditorOption) {
            emptyTextBoxes();

        }else {
            boardSelect();
        }
    }

    /**
     * Empty all text box on level editor
     */
    private void emptyTextBoxes() {
        nameIn.setText("");
        widthIn.setText("");
        heightIn.setText("");
    }


}
